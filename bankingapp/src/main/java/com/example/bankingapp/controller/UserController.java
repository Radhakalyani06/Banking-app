package com.example.bankingapp.controller;

import com.example.bankingapp.entity.Transaction;
import com.example.bankingapp.entity.User;
import com.example.bankingapp.service.UserService;
import com.example.bankingapp.service.TransactionService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

@Autowired
private UserService userService;

@Autowired
private TransactionService transactionService;



/* =====================================================
                    LOGIN PAGE
===================================================== */

@GetMapping("/login")
public String loginPage()
{
    return "login";
}



/* =====================================================
                    REGISTER PAGE
===================================================== */

@GetMapping("/register")
public String registerPage(Model model)
{
    model.addAttribute("user", new User());
    return "register";
}



/* =====================================================
                    REGISTER USER
===================================================== */

@PostMapping("/register")
public String registerUser(@ModelAttribute User user)
{
    // Default balance
    user.setBalance(0);

    // Save user
    userService.registerUser(user);

    return "redirect:/";
}



/* =====================================================
                    LOGIN USER
===================================================== */
/* =====================================================
LOGIN USER
===================================================== */

@PostMapping("/login")
public String login(@RequestParam String email,
@RequestParam String password,
HttpSession session,
Model model)
{

User user = userService.findByEmail(email);

// Email check
if(user == null)
{
model.addAttribute("msg","Email not found");
return "login";
}

// Password check
if(!user.getPassword().equals(password))
{
model.addAttribute("msg","Invalid password");
return "login";
}

// Login success
session.setAttribute("user", user);
return "redirect:/dashboard";

}


/* =====================================================
                    USER DASHBOARD
===================================================== */

@GetMapping("/dashboard")
public String dashboard(HttpSession session, Model model)
{

    User user = (User) session.getAttribute("user");

    if(user == null)
    {
        return "redirect:/";
    }

    model.addAttribute("user", user);

    return "dashboard";
}



/* =====================================================
                    DEPOSIT PAGE
===================================================== */

@GetMapping("/depositPage")
public String depositPage()
{
    return "deposit";
}



/* =====================================================
                    DEPOSIT MONEY
===================================================== */

@PostMapping("/deposit")
public String deposit(@RequestParam double amount,
                      @RequestParam String pin,
                      HttpSession session,
                      Model model)
{

    User user = (User) session.getAttribute("user");

    if(user == null)
    {
        return "redirect:/login";
    }

    // PIN validation
    if(!user.getPin().equals(pin))
    {
        model.addAttribute("error","Invalid PIN");
        return "deposit";
    }

    if(amount > 0)
    {
        user.setBalance(user.getBalance() + amount);

        userService.updateUser(user);

        transactionService.saveTransaction(user.getId(),"DEPOSIT",amount);

        session.setAttribute("user", user);
    }

    return "redirect:/dashboard";
}



/* =====================================================
                    WITHDRAW PAGE
===================================================== */

@GetMapping("/withdrawPage")
public String withdrawPage()
{
    return "withdraw";
}



/* =====================================================
                    WITHDRAW MONEY
===================================================== */

@PostMapping("/withdraw")
public String withdraw(@RequestParam double amount,
                       @RequestParam String pin,
                       HttpSession session,
                       Model model)
{

    User user = (User) session.getAttribute("user");

    if(user == null)
    {
        return "redirect:/login";
    }

    // PIN validation
    if(!user.getPin().equals(pin))
    {
        model.addAttribute("error","Invalid PIN");
        return "withdraw";
    }

    // Balance check
    if(user.getBalance() < amount)
    {
        model.addAttribute("error","Insufficient Balance");
        return "withdraw";
    }

    user.setBalance(user.getBalance() - amount);

    userService.updateUser(user);

    transactionService.saveTransaction(user.getId(),"WITHDRAW",amount);

    session.setAttribute("user", user);

    return "redirect:/dashboard";
}



/* =====================================================
                    TRANSFER PAGE
===================================================== */

@GetMapping("/transferPage")
public String transferPage()
{
    return "transfer";
}



/* =====================================================
                    TRANSFER MONEY
===================================================== */

@PostMapping("/transfer")
public String transfer(@RequestParam String receiverEmail,
                       @RequestParam double amount,
                       @RequestParam String pin,
                       HttpSession session,
                       Model model)
{

    User sender = (User) session.getAttribute("user");

    if(sender == null)
    {
        return "redirect:/login";
    }

    // PIN validation
    if(!sender.getPin().equals(pin))
    {
        model.addAttribute("error","Invalid PIN");
        return "transfer";
    }

    User receiver = userService.findByEmail(receiverEmail);

    if(receiver == null)
    {
        model.addAttribute("error","Receiver Not Found");
        return "transfer";
    }

    if(sender.getBalance() < amount)
    {
        model.addAttribute("error","Insufficient Balance");
        return "transfer";
    }

    // Deduct sender balance
    sender.setBalance(sender.getBalance() - amount);

    // Add receiver balance
    receiver.setBalance(receiver.getBalance() + amount);

    userService.updateUser(sender);
    userService.updateUser(receiver);

    transactionService.saveTransaction(sender.getId(),"TRANSFER SENT",amount);

    transactionService.saveTransaction(receiver.getId(),"TRANSFER RECEIVED",amount);

    session.setAttribute("user", sender);

    return "redirect:/dashboard";
}



/* =====================================================
                    VIEW TRANSACTIONS
===================================================== */

@GetMapping("/transactions")
public String transactions(HttpSession session, Model model)
{

    User user = (User) session.getAttribute("user");

    List<Transaction> list = transactionService.getTransactionsByUser(user.getId());

    model.addAttribute("transactions", list);

    return "transactions";
}



/* =====================================================
                    LOGOUT
===================================================== */

@GetMapping("/logout")
public String logout(HttpSession session)
{

    session.invalidate();

    return "redirect:/";
}

}