package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Payment;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(User user, double amount) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(amount);
        payment.setPaid(false);
        return paymentRepository.save(payment);
    }

    @Override
    public void processPayment(Long paymentId) {
        // Lógica de processamento do pagamento (pode ser simulado aqui)
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            payment.setPaid(true);
            paymentRepository.save(payment);
        }
    }
}
