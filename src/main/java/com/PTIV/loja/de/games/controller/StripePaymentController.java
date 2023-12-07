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


    //faça o pagamentoIntent
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent() throws StripeException {

        //obter os detalhes do usuário
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //obter o perfil do cliente
        CustomerProfile customerProfile = user.getCustomerProfile();
        //obtenha os cartItems desse customerProfile
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        //obtenha o custo total
        long totalCost = (long)shoppingCartService.totalCost(cartItemList);

        //definir os parâmetros PaymentIntent com o objeto construtor,
        // a moeda euro e o valor total oferecido
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("BR")
                .setAmount(totalCost*100L)
                .build();
        // Crie um PaymentIntent com o valor do pedido e a moeda
        PaymentIntent intent = PaymentIntent.create(createParams);
        //excluir itens do carrinho
        return new CreatePaymentResponse(intent.getClientSecret());
    }

    //exclua os itens do carrinho


    }
