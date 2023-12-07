package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.Quantity;
import com.PTIV.loja.de.games.model.Role;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class HomeController {

//    @Autowired
//    private QuantidadeServiço quantidadeServiço;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    //a página inicial do aplicativo da web
    @GetMapping({"/", "/home"})
    public String viewIndex(Model model){
//-----Fragmento de código para obter o número do carrinho---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;

            List<Role> userRoles = user.getRoles();
            for (Role role: userRoles) {
                if(role.getId() == 1){
                    return "adminHome";
                }
            }
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------
        return "index";
    }

    //renderizar a página da loja
    @GetMapping("/shop")
    public String viewShop(Model model){
        //-----Fragmento de código para obter o número do carrinho---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "shop";
    }

    //Filtrar por categoria
    @GetMapping("/shop/category/{id}")
    public String viewByCategories(@PathVariable int id, Model model){

        //-----Fragmento de código para obter o número do carrinho---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "shop";
    }

    //cliente visualizar página do produto
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model){
        //-----Fragmento de código para obter o número do carrinho---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------

        model.addAttribute("quantityObj", new Quantity());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }

    @GetMapping("/shop/viewproductAdmin/{id}")
    public String viewProductAdmin(@PathVariable int id, Model model){
        //-----Fragmento de código para obter o número do carrinho---------
        //-----------------------------------------------

        model.addAttribute("quantityObj", new Quantity());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProductAdmin";
    }
}

