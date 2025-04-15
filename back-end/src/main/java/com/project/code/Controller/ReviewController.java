package com.project.code.Controller;

import com.project.code.Model.Review;
import com.project.code.Model.Customer;
import com.project.code.Repo.ReviewRepository;
import com.project.code.Repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> formattedReviews = new ArrayList<>();

        // Get all reviews for the product in the store
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);

        // Process each review
        for (Review review : reviews) {
            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("comment", review.getComment());
            reviewData.put("rating", review.getRating());
            reviewData.put("reviewDate", review.getReviewDate());

            // Get customer name
            String customerName = "Unknown";
            if (review.getCustomerId() != null) {
                Optional<Customer> customer = customerRepository.findById(review.getCustomerId());
                if (customer.isPresent()) {
                    customerName = customer.get().getName();
                }
            }
            reviewData.put("customerName", customerName);

            formattedReviews.add(reviewData);
        }

        response.put("reviews", formattedReviews);
        return response;
    }

    // Additional useful endpoint
    @GetMapping("/product/{productId}")
    public Map<String, Object> getProductReviews(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewRepository.findByProductId(productId);
        response.put("reviews", reviews);
        return response;
    }
}