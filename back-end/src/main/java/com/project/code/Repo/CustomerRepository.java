package com.project.code.Repo;

import com.project.code.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their email address
     * @param email The email address to search for
     * @return The customer with the matching email, or null if not found
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Finds a customer by their ID
     * @param id The customer ID to search for
     * @return The customer with the matching ID, or null if not found
     */
    Optional<Customer> findById(Long id);

    /**
     * Finds customers by name (case-insensitive)
     * @param name The name to search for
     * @return List of customers with matching names
     */
    List<Customer> findByNameContainingIgnoreCase(String name);

    /**
     * Checks if a customer with the given email exists
     * @param email The email to check
     * @return true if a customer with this email exists, false otherwise
     */
    boolean existsByEmail(String email);
}