package com.project.code.Repo;

import com.project.code.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Finds order items by order ID
     * @param orderId The order ID
     * @return List of order items
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * Finds order items by product ID
     * @param productId The product ID
     * @return List of order items
     */
    List<OrderItem> findByProductId(Long productId);

    /**
     * Finds order items with product details
     * @param orderId The order ID
     * @return List of order items with product data
     */
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.product WHERE oi.order.id = :orderId")
    List<OrderItem> findByOrderIdWithProducts(Long orderId);

    /**
     * Calculates total quantity sold by product
     * @param productId The product ID
     * @return Total quantity sold
     */
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.id = :productId")
    Integer getTotalQuantitySoldByProduct(Long productId);
}