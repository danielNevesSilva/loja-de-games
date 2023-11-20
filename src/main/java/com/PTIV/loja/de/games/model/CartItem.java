package com.PTIV.loja.de.games.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id")
    private CustomerProfile customerProfile;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @Column(name = "paid")
    private Boolean paid;

    public CartItem() {
    }

    public CartItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", customerProfile=" + customerProfile.getEmail() +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ",  paid"+
                '}';
    }
}