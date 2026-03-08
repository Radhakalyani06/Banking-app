package com.example.bankingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingapp.entity.Transaction;
import com.example.bankingapp.entity.User;
import com.example.bankingapp.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

@Autowired
TransactionRepository repo;

public void saveTransaction(Long userId,String type,double amount)
{
Transaction t=new Transaction();

t.setUserId(userId);
t.setType(type);
t.setAmount(amount);
t.setDate(LocalDateTime.now());

repo.save(t);
}
public List<Transaction> getTransactionsByUser(Long userId) {

return repo.findByUserId(userId);

}
}
