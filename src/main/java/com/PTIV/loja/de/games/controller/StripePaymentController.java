package com.PTIV.loja.de.games.controller;


import com.PTIV.loja.de.games.dto.CreatePaymentResponse;
import com.PTIV.loja.de.games.model.CartItem;
import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.OrderHistory;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.repository.OrderHistoryRepository;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.ShoppingCartService;
import com.PTIV.loja.de.games.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StripePaymentController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;


    //make the paymentIntent

    //delete the items in the cart
    private void moveCartItemsToOrderHistory(List<CartItem> cartItemList){

        //for each cart item set the fields of the orderHistory and write to persistent storage
        //also delete the cart item
        for (CartItem cartItem: cartItemList) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setCustomerId(cartItem.getCustomerProfile().getId());
            orderHistory.setQuantity(cartItem.getQuantity());
            orderHistory.setProductId(cartItem.getProduct().getId());
            orderHistory.setSubtotal(cartItem.getQuantity()*(cartItem.getProduct().getPrice()));
            orderHistory.setDateCreated("today");
            System.out.println("cart subtotal" + orderHistory.getSubtotal());
            orderHistoryRepository.save(orderHistory);
            shoppingCartService.deleteItems(cartItem);
        }

    }
}