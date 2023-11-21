package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.OrderHistory;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.OrderHistoryService;
import com.PTIV.loja.de.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    public OrderHistoryController() {
    }

    @GetMapping("/orderHistory")
    public String showOrderHistory(Model model) {
        // Obtém o CustomerProfile do usuário logado (você precisará implementar essa lógica)

        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        if(user.getCustomerProfile() == null){
            return "profileAdd";
        }else {
            customerProfile = user.getCustomerProfile();
        }

        // Busca todos os pedidos do cliente
        List<OrderHistory> orderHistoryList = orderHistoryService.findAllByCustomerProfile(customerProfile);

        // Adiciona os pedidos ao modelo para exibição na página
        model.addAttribute("orderHistoryList", orderHistoryList);

        return "orderHistory";
    }
}
