package com.practice.kafkaApp.repository;

import com.practice.kafkaApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM transfer_history WHERE ic_no = ?1", nativeQuery = true)
    List<Transaction> listTransactionsByIcNo(String ic_number);

    @Query(value = "SELECT status FROM transfer_history WHERE tx_uid = ?1", nativeQuery = true)
    String checkTransferStatusForTXUID(String txuid);

}
