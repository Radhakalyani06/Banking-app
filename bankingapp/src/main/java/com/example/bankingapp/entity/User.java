package com.example.bankingapp.entity;

import jakarta.persistence.*;

@Entity
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String email;
private String password;
private String pin;

private double balance;

public User(){}

public Long getId(){ return id; }

public String getName(){ return name; }
public void setName(String name){ this.name=name; }

public String getEmail(){ return email; }
public void setEmail(String email){ this.email=email; }

public String getPassword(){ return password; }
public void setPassword(String password){ this.password=password; }

public String getPin(){ return pin; }
public void setPin(String pin){ this.pin=pin; }

public double getBalance(){ return balance; }
public void setBalance(double balance){ this.balance=balance; }

}