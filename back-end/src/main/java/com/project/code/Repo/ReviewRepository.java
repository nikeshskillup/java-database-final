package com.project.code.Repo;

import com.project.code.Model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    /**
     * Finds all reviews for a specific product at a specific store
     * @param storeId The ID of the store
     * @param productId The ID of the product
     * @return List of reviews matching the criteria
     */
    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);

    // Additional useful query methods
    List<Review> findByProductId(Long productId);
    
    List<Review> findByStoreId(Long storeId);
    
    List<Review> findByCustomerId(Long customerId);
    
    List<Review> findByRatingBetween(int minRating, int maxRating);
    
    /**
     * Finds reviews for a product with minimum rating
     * @param productId The product ID
     * @param minRating The minimum rating (inclusive)
     * @return List of matching reviews
     */
    List<Review> findByProductIdAndRatingGreaterThanEqual(Long productId, int minRating);
}