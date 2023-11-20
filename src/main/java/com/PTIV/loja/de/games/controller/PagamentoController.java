package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.model.Payment;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.PaymentService;
import com.PTIV.loja.de.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagamentoController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/create-payment")
    public String createPayment(@RequestParam double amount, Model model) {
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        Payment payment = paymentService.createPayment(user, amount);
        model.addAttribute("payment", payment);
        return "paymentConfirmation";
    }

    @PostMapping("/process-payment")
    public String processPayment(@RequestParam Long paymentId) {
        paymentService.processPayment(paymentId);
        return "redirect:/payment-success";
    }

}
