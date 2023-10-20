package com.PTIV.loja.de.games.service;


import com.PTIV.loja.de.games.model.CartItem;
import com.PTIV.loja.de.games.model.CustomerProfile;
import com.PTIV.loja.de.games.model.Product;
import com.PTIV.loja.de.games.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public Integer addProduct(Integer quantity, Integer productId, CustomerProfile customerProfile) {

        //var declarations
        Integer updatedQuantity = quantity;
        Product product = new Product(productId);

        //get the cartitems of a customer for a specific product
        CartItem cartItem = cartItemRepository.findByCustomerProfileAndProduct(customerProfile,
                product);
        // if there is no cartitems of a customer for a specific product
        if(cartItem != null){
            //just add the quantity
            updatedQuantity = cartItem.getQuantity() + quantity;
        }else {
            //make a new cartitems object and set the customer profile and product to attributes
            cartItem = new CartItem();
            cartItem.setCustomerProfile(customerProfile);
            cartItem.setProduct(product);
        }

        //set the quantity
        cartItem.setQuantity(updatedQuantity);
        //write to presistent storage
        cartItemRepository.save(cartItem);

        return updatedQuantity;
    }

    public List<CartItem> listCartItems(CustomerProfile customerProfile){
        return cartItemRepository.findByCustomerProfile(customerProfile);
    }

    public Integer cartCount(List<CartItem> cartItemList) {
        int totalquantity = 0;
        for (CartItem cartItem: cartItemList) {
            totalquantity += cartItem.getQuantity();
        }
        return totalquantity;
    }

    public float totalCost(List<CartItem> cartItemList){
        float total = 0;
        for (CartItem cartItem: cartItemList) {
            float subtotal = cartItem.getQuantity()*cartItem.getProduct().getPrice();
            total += subtotal;
        }
        return total;
    }

    public void deleteItems(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }
}

