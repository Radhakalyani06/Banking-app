package com.example.bankingapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private Long userId;

private String type;

private double amount;

private LocalDateTime date;

public Transaction(){}

public Long getId(){ return id; }

public Long getUserId(){ return userId; }
public void setUserId(Long userId){ this.userId=userId; }

public String getType(){ return type; }
public void setType(String type){ this.type=type; }

public double getAmount(){ return amount; }
public void setAmount(double amount){ this.amount=amount; }

public LocalDateTime getDate(){ return date; }
public void setDate(LocalDateTime date){ this.date=date; }

}
