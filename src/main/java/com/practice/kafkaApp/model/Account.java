package com.practice.kafkaApp.model;


import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="account", schema="public")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_number")
    private int acct_number;
    @Column(name = "ic_number")
    private String ic_number;
}
