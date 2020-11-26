package com.practice.kafkaApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "transfer_history", schema = "public")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Setter
    @Column(name = "tx_uid")
    @JsonProperty("txUid")
    private String tx_uid;

    @Column(name = "debit_acct_no")
    @JsonProperty("debitAcct")
    private int debit_account_no;

    @Column(name = "ic_no")
    @JsonProperty("debitIC")
    private String debit_ic_no;

    @Column(name = "credit_acct_no")
    @JsonProperty("creditAcct")
    private int credit_account_no;

    @Column(name = "bene_ic_no")
    @JsonProperty("creditIC")
    private String credit_ic_no;

    @Column(name = "amount")
    private double amount;

    @Setter
    @Column(name = "status")
    private String status;

    @Setter
    @Column(name = "reason")
    private String reason;

    @Override
    public String toString(){
        return "TxUid: " + this.getTx_uid()
                + ", debit acct: " + this.getDebit_account_no()
                + ", debit IC: " + this.getDebit_ic_no()
                + ", credit acct: " + this.getCredit_account_no()
                + ", credit IC: " + this.getCredit_ic_no()
                + ", amount: " + this.getAmount();
    }

}
