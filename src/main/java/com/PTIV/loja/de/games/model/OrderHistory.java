package com.PTIV.loja.de.games.model;


import jakarta.persistence.*;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "product_id")
    private int productId;
    private int quantity;
    private double subtotal;



    public OrderHistory() {
    }

    public OrderHistory(int customerId, int productId, int quantity, float subtotal, boolean paid) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;

    }

    public OrderHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }


    @Override
    public String toString() {
        return "OrderHistory{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
