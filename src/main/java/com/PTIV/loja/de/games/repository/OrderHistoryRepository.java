package com.PTIV.loja.de.games.repository;

import com.PTIV.loja.de.games.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
}
