package com.example.bankingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bankingapp.entity.User;
import com.example.bankingapp.repository.UserRepository;

@Service
public class UserService {

@Autowired
UserRepository repo;

public void registerUser(User user)
{
user.setBalance(0);
repo.save(user);
}

public User login(String email)
{
return repo.findByEmail(email);
}

public void updateUser(User user)
{
repo.save(user);
}
public User findByEmail(String email)
{
return repo.findByEmail(email);
}

public User findByEmail1(String receiverEmail) {
	// TODO Auto-generated method stub
	return repo.findByEmail(receiverEmail);
}

}