package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Payment;
import com.PTIV.loja.de.games.model.User;


public interface PaymentService {
    Payment createPayment(User user, double amount);
    void processPayment(Long paymentId);
}

