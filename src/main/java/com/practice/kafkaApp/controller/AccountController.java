package com.practice.kafkaApp.controller;

import com.practice.kafkaApp.model.Account;
import com.practice.kafkaApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @PostMapping
    public Account addAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(){
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping(value = "/{acct_number}")
    public ResponseEntity<Account> getAccount(@PathVariable(value = "acct_number") int acct_id){
        Account account = accountRepository.findById(acct_id).orElseThrow(
                ()->new ResourceNotFoundException("Account Not Found" + acct_id)
        );

        return ResponseEntity.ok().body(account);
    }

    @PutMapping(value = "/{acct_number}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "acct_number") int acct_id, @RequestBody Account accountDetail){
        Account account = accountRepository.findById(acct_id).orElseThrow(
                ()->new ResourceNotFoundException("Account Not Found" + acct_id)
        );
        account.setIc_number(accountDetail.getIc_number());

        return ResponseEntity.ok(this.accountRepository.save(account));
    }

    @DeleteMapping(value = "/{acct_number}")
    public ResponseEntity<Void> deleteAccount(@PathVariable(value = "acct_number") int acct_id){
        Account account = accountRepository.findById(acct_id).orElseThrow(
                ()->new ResourceNotFoundException("Account Not Found" + acct_id)
        );

        accountRepository.delete(account);
        return ResponseEntity.ok().build();
    }

    public boolean verifyAccount(int acct_number, String ic_number){
        return accountRepository.verifyAccount(acct_number, ic_number);
    }

}
