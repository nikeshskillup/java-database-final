package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Store ID cannot be null")
    private Long storeId;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private String comment;

    // Constructors
    public Review() {
    }

    public Review(Long customerId, Long productId, Long storeId, Integer rating) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
    }

    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this(customerId, productId, storeId, rating);
        this.comment = comment;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", storeId=" + storeId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}