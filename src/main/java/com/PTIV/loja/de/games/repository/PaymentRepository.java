package com.PTIV.loja.de.games.repository;

import com.PTIV.loja.de.games.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
