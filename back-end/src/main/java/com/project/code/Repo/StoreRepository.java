package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * Finds a store by its ID
     * @param id The store ID
     * @return Optional containing the store if found
     */
    Optional<Store> findById(Long id);

    /**
     * Finds stores whose name contains the given substring (case-insensitive)
     * @param pname The substring to search for in store names
     * @return List of matching stores
     */
    @Query("SELECT s FROM Store s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Store> findBySubName(String pname);

    // Additional useful methods
    boolean existsByName(String name);

    @Query("SELECT s FROM Store s WHERE s.address LIKE %:addressPart%")
    List<Store> findByAddressContaining(String addressPart);

    List<Store> findByNameContainingIgnoreCase(String name);

    @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.inventories")
    List<Store> findAllWithInventories();
}