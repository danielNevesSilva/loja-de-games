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
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
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
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent() throws StripeException {

        //get the user details
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //get the customerProfile
        CustomerProfile customerProfile = user.getCustomerProfile();
        //get the cartItems of that customerProfile
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        //get the total cost
        long totalCost = (long)shoppingCartService.totalCost(cartItemList);

        //set the PaymentIntent parameters with builder object,
        // the currency euro and the total amount tendered
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("BR")
                .setAmount(totalCost*100L)
                .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent intent = PaymentIntent.create(createParams);
        //delete cartItems
        //create the
        return new CreatePaymentResponse(intent.getClientSecret());
    }

    //delete the items in the cart


    }
