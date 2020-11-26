package com.practice.kafkaApp.controller;

import com.practice.kafkaApp.model.Transaction;
import com.practice.kafkaApp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class TransactionController {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionController.class);

    private final KafkaTemplate<String, Object> template;
    private final String transferTopic;
    private final String verificationResultTopic;

    public TransactionController(final KafkaTemplate<String, Object> template,
                                 @Value("${tpd.verification-result-topic}") final String verificationResultTopic,
                                 @Value("${tpd.transfer-topic}") final String transferTopic){
        this.template = template;
        this.verificationResultTopic = verificationResultTopic;
        this.transferTopic = transferTopic;
    }

    @Autowired
    AccountController accountController;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping(value = "/listAllTransferRequestByIcNo/{ic}")
    public ResponseEntity<List<Transaction>> listTransactionsByIcNo(@PathVariable(value = "ic") String ic){
        return ResponseEntity.ok(transactionRepository.listTransactionsByIcNo(ic));
    }

    @GetMapping(value = "/checkTransferStatusForTXUID/{txuid}")
    public String checkTransferStatusForTXUID(@PathVariable(value = "txuid") String txuid){
        return transactionRepository.checkTransferStatusForTXUID(txuid);
    }

    @PostMapping(value = "/transfer")
    public Transaction transfer(@RequestBody Transaction transactionDetail){
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        transactionDetail.setTx_uid("TX" + date);
        transactionDetail.setStatus("PROCESSING");

        logger.info(transactionDetail.toString());
        this.template.send(transferTopic, transactionDetail);
        logger.info("Sending transaction detail: " + transactionDetail.getTx_uid() + ", TOPIC: " + transferTopic);

        return transactionDetail;
    }

    @KafkaListener(topics = "transfer-topic", containerFactory = "kafkaListenerContainerFactory")
    public void transferListener(Transaction transactionDetail){
        logger.info("====================================");
        logger.info("Receiving transaction detail: " + transactionDetail.getTx_uid());
        logger.info(transactionDetail.toString());

        try {
            transactionRepository.save(transactionDetail);

            transactionDetail.setReason(validateTransactionAcct(transactionDetail));
            this.template.send(verificationResultTopic, transactionDetail);
            logger.info("Sending transaction detail2: " + transactionDetail.getTx_uid());
        }catch(Exception e){
            logger.info("ERROR @transferListener: " + e.toString());
        }
    }

    @KafkaListener(topics = "acct-verification-result-topic", containerFactory = "kafkaListenerContainerFactory")
    public void verifyResultListener(Transaction transactionDetail){
        logger.info("Receiving verified transaction detail: " + transactionDetail.getTx_uid());

        if(transactionDetail.getReason().equals("Valid"))transactionDetail.setStatus("SUCCEED");
        else transactionDetail.setStatus("FAILED");

        try{
            transactionRepository.save(transactionDetail);
        }catch(Exception e){
            logger.info("ERROR @verifyResultListener: " + e.toString());
        }

    }

    public String validateTransactionAcct(Transaction transactionDetail){

        boolean isValidDebitAcct = accountController.verifyAccount(transactionDetail.getDebit_account_no(), transactionDetail.getDebit_ic_no());
        boolean isValidCreditAcct = accountController.verifyAccount(transactionDetail.getCredit_account_no(), transactionDetail.getCredit_ic_no());

        if(!isValidCreditAcct && !isValidDebitAcct)
            return "BothDebitNCreditAcctNotFound";
        else if(!isValidCreditAcct)
            return "CreditAccountNotFound";
        else if(!isValidDebitAcct)
            return "DebitAccountNotFound";
        else
            return "Valid";
    }

}
