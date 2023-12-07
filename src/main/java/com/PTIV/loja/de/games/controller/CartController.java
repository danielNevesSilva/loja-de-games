package com.PTIV.loja.de.games.controller;


import com.PTIV.loja.de.games.model.CartItem;
import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.Quantity;
import com.PTIV.loja.de.games.model.User;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;
import com.PTIV.loja.de.games.service.ShoppingCartService;
import com.PTIV.loja.de.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;
import java.util.List;


@Controller
public class CartController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;



    public CartController() {
    }



    //Carregue a página de pagamento do Stripe Payment
    //    //Adicionar ao carrinho
    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id,
                            @ModelAttribute("quantityObj") Quantity quantityObj,
                            Model model,
                            @ModelAttribute("value") Integer value)  {

        //recuperamos qual usuário é
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        //se o usuário não tiver perfil de cliente
        if(user.getCustomerProfile() == null){
            //redirect to profile
            return "profile";
        }else {
            //definir este cliente para seu perfil de cliente
            customerProfile = user.getCustomerProfile();
        }

        //obtenha o valor da quantidade do valor do atributo amountObj
        model.addAttribute("value", value);

        //adicione o produto ao banco de dados
        shoppingCartService.addProduct(value,id,customerProfile);

        //quando adicionado redireciona para loja
        return "redirect:/shop";
    }

    //Ver o carrinho
    @GetMapping("/cart")
    public String viewCart(Model model){

        //Obtenha as informações do perfil do cliente
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        if(user.getCustomerProfile() == null){
            return "profile";
        }else {
            customerProfile = user.getCustomerProfile();
        }

        //obtenha os itens do carrinho para o perfil do cliente
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        Integer totalQuantity = shoppingCartService.cartCount(cartItemList);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //Adicionar à visualização
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("cartCount", totalQuantity);

        double totalCostWithoutShipping = shoppingCartService.totalCost(cartItemList);

        // Adiciona o custo fixo de frete
        double shippingCost = 10.00;
        double totalCostWithShipping = totalCostWithoutShipping + shippingCost;


        model.addAttribute("total", decimalFormat.format(totalCostWithShipping));

        return "cart";
    }

    //Remove item do carrinho
    @GetMapping("/cart/removeItem/{index}")
    public String removeCartItem(@PathVariable int index){
        shoppingCartService.deleteItems(new CartItem(index));
        return "redirect:/cart";
    }

    //Pagina checkout
    @GetMapping("/checkout")
    public String checkout(Model model){
        //-------obtenha os detalhes do perfil do cliente -------------------------
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        if(user.getCustomerProfile() == null){
            return "profileAdd";
        }else {
            customerProfile = user.getCustomerProfile();
        }

        //-------Exibir na visualização Detalhes do usuário -------------------------
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());

        //Detalhes dos itens do carrinho.
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        Integer totalQuantity = shoppingCartService.cartCount(cartItemList);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //---------Exibir para a visualização ----------------------------------
        model.addAttribute("customerProfile", customerProfile);
        model.addAttribute("cartCount", totalQuantity);
        model.addAttribute("cartItemList", cartItemList);

        double totalCostWithoutShipping = shoppingCartService.totalCost(cartItemList);

        // Adiciona o custo fixo de frete
        double shippingCost = 10.00;
        double totalCostWithShipping = totalCostWithoutShipping + shippingCost;

        model.addAttribute("total", decimalFormat.format(totalCostWithShipping));


        return "checkout";
    }

}
