package com.example.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankingapp.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

User findByEmail(String email);

}