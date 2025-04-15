package com.project.code.Model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.Positive;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderDetails order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Positive(message = "Price must be positive")
    private Double price;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(OrderDetails order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Business logic method
    public Double getSubtotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}