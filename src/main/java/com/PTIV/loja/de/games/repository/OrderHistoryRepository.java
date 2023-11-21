package com.PTIV.loja.de.games.repository;

import com.PTIV.loja.de.games.model.CartItem;
import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.OrderHistory;
import com.PTIV.loja.de.games.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
        OrderHistory findByCustomerProfileAndProduct(CustomerProfile customerProfile, Product product);
        List<OrderHistory> findAllByCustomerProfile(CustomerProfile customerProfile);


}


