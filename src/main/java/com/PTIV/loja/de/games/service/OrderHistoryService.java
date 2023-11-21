package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.OrderHistory;
import com.PTIV.loja.de.games.model.Product;
import com.PTIV.loja.de.games.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public OrderHistory findByCustomerAndProduct(CustomerProfile customerProfile, Product product) {
        return orderHistoryRepository.findByCustomerProfileAndProduct(customerProfile, product);
    }

    public List<OrderHistory> findAllByCustomerProfile(CustomerProfile customerProfile) {
        return orderHistoryRepository.findAllByCustomerProfile(customerProfile);
    }

}
