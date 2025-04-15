package com.project.code.Repo;

import com.project.code.Model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    /**
     * Finds orders by customer ID
     * @param customerId The customer ID
     * @return List of orders for the customer
     */
    List<OrderDetails> findByCustomerId(Long customerId);

    /**
     * Finds orders by store ID
     * @param storeId The store ID
     * @return List of orders for the store
     */
    List<OrderDetails> findByStoreId(Long storeId);

    /**
     * Finds orders by customer email
     * @param email The customer email
     * @return List of orders for the customer
     */
    @Query("SELECT o FROM OrderDetails o WHERE o.customer.email = :email")
    List<OrderDetails> findByCustomerEmail(String email);

    /**
     * Finds orders within a date range
     * @param start Start date
     * @param end End date
     * @return List of orders between dates
     */
    @Query("SELECT o FROM OrderDetails o WHERE o.orderDate BETWEEN :start AND :end")
    List<OrderDetails> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}