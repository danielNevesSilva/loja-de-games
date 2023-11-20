package com.PTIV.loja.de.games.controller;


import com.PTIV.loja.de.games.model.*;
import com.PTIV.loja.de.games.repository.OrderHistoryRepository;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.PaymentService;
import com.PTIV.loja.de.games.service.ShoppingCartService;
import com.PTIV.loja.de.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class PagamentoController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;


    @GetMapping("/payment")
    public String home(Model model){
        //get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //get the customer Profile
        CustomerProfile customerProfile = user.getCustomerProfile();
        //get the cartItems of the customer Profile
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        model.addAttribute("email", customUserDetailsService.returnUsername());
        model.addAttribute("total", decimalFormat.format(shoppingCartService.totalCost(cartItemList)));


        return "payment";
    }

    @PostMapping("/create-payment")
    public String createPayment(@RequestParam double amount, RedirectAttributes attributes) {
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        // Lógica para mover itens do carrinho para o histórico de pedidos
        Payment payment = paymentService.createPayment(user, amount);


        return "index";
    }

    @PostMapping("/process-payment")
    public String processPayment(@RequestParam Long paymentId, Model model) {
        paymentService.processPayment(paymentId);


        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile = user.getCustomerProfile();
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);

        model.addAttribute("cartItems", cartItemList);

        moveCartItemsToOrderHistory(cartItemList, customerProfile);

        return "index";
    }

    private void moveCartItemsToOrderHistory(List<CartItem> cartItemList, CustomerProfile customerProfile) {
        for (CartItem cartItem : cartItemList) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setCustomerId(customerProfile.getId());
            orderHistory.setQuantity(cartItem.getQuantity());
            orderHistory.setProductId(cartItem.getProduct().getId());
            orderHistory.setSubtotal(cartItem.getQuantity() * cartItem.getProduct().getPrice());

            // Salva no histórico de pedidos
            orderHistoryRepository.save(orderHistory);

            // Remove do carrinho
            shoppingCartService.deleteItems(cartItem);
        }
    }
}