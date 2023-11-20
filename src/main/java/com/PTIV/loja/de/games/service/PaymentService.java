package com.PTIV.loja.de.games.service;


import com.PTIV.loja.de.games.model.CartItem;
import com.PTIV.loja.de.games.model.Payment;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.CartItemRepository;
import com.PTIV.loja.de.games.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Payment createPayment(User user, double amount) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(amount);
        return paymentRepository.save(payment);
    }


    public void processPayment(Long paymentId) {
        CartItem cartItem = cartItemRepository.findById(paymentId);
        if (cartItem != null) {
            cartItem.setPaid(true);
            cartItemRepository.save(cartItem);
        }
    }
}