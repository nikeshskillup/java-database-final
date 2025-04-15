package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
    Optional<Inventory> findByProductIdAndStoreId(Long productId, Long storeId);

    List<Inventory> findByStore_Id(Long storeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.product.id = :productId")
    void deleteByProductId(Long productId);

    // Additional useful methods
    boolean existsByProductIdAndStoreId(Long productId, Long storeId);

    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId")
    List<Inventory> findByProductId(Long productId);
}