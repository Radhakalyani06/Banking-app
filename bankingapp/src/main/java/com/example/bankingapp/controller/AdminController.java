package com.example.bankingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bankingapp.entity.User;
import com.example.bankingapp.entity.Transaction;
import com.example.bankingapp.repository.UserRepository;
import com.example.bankingapp.repository.TransactionRepository;

@Controller
public class AdminController {

    // Inject UserRepository
    @Autowired
    private UserRepository userRepository;

    // Inject TransactionRepository
    @Autowired
    private TransactionRepository transactionRepository;


    /**
     * Admin Login Page
     * URL : /adminLogin
     */
    @GetMapping("/adminLogin")
    public String adminLoginPage() {

        return "adminLogin";
    }


    /**
     * Admin Login Check
     */
    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password) {

        // Hardcoded admin credentials
        if(email.equals("admin@gmail.com") && password.equals("admin123")) {

            // Redirect to dashboard
            return "redirect:/adminDashboard";

        } else {

            // Login failed → return login page
            return "adminLogin";
        }
    }


    /**
     * Admin Dashboard
     * Shows users, transactions, deposit & withdraw info
     */
    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model) {

        // Total Users
        long totalUsers = userRepository.count();

        // Total Transactions
        long totalTransactions = transactionRepository.count();

        // Get all users
        List<User> users = userRepository.findAll();

        // Get all transactions
        List<Transaction> transactions = transactionRepository.findAll();

        // Send values to HTML
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("users", users);
        model.addAttribute("transactions", transactions);

        return "adminDashboard";
    }

}