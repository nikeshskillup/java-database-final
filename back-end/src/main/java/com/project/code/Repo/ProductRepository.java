package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic CRUD operations inherited from JpaRepository
    
    // Standard query methods
    List<Product> findByCategory(String category);
    
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    Optional<Product> findBySku(String sku);
    
    Optional<Product> findByName(String name);
    
    // Custom query methods
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Product> findProductBySubName(String pname);
    
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId")
    List<Product> findProductsByStoreId(Long storeId);
    
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    List<Product> findByCategoryAndStoreId(Long storeId, String category);
    
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Product> findByNameLike(Long storeId, String pname);
    
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.product.category = :category")
    List<Product> findByNameAndCategory(Long storeId, String pname, String category);
    
    @Query("SELECT i.product FROM Inventory i WHERE i.product.category = :category AND i.store.id = :storeId")
    List<Product> findProductByCategory(String category, Long storeId);
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND p.category = :category")
    List<Product> findProductBySubNameAndCategory(String pname, String category);
    
    // Additional useful methods
    boolean existsBySku(String sku);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findAllCategories();
}