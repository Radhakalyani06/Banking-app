package com.example.bankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankingapp.entity.Transaction;



public interface TransactionRepository extends JpaRepository<Transaction, Long>{

List<Transaction> findByUserId(Long userId);

}