package com.practice.kafkaApp.repository;

import com.practice.kafkaApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT count(acct_number) > 0 FROM Account WHERE acct_number = ?1 and ic_number = ?2")
    boolean verifyAccount(int acct_number, String ic_number);
}
