package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceClass {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ServiceClass(ProductRepository productRepository, 
                      InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Validates if an inventory record exists for a product-store combination
     * @param inventory The inventory to validate
     * @return false if inventory exists, true otherwise
     */
    public boolean validateInventory(Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryRepository
            .findByProductIdAndStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
            );
        return existingInventory.isEmpty();
    }

    /**
     * Validates if a product with the same name already exists
     * @param product The product to validate
     * @return false if product with same name exists, true otherwise
     */
    public boolean validateProduct(Product product) {
        Optional<Product> existingProduct = productRepository
            .findByName(product.getName());
        return existingProduct.isEmpty();
    }

    /**
     * Validates if a product exists by ID
     * @param id The product ID to validate
     * @return true if product exists, false otherwise
     */
    public boolean validateProductId(long id) {
        return productRepository.existsById(id);
    }

    /**
     * Retrieves inventory record for a product-store combination
     * @param inventory The inventory to search for
     * @return The found inventory record
     * @throws RuntimeException if inventory not found
     */
    public Inventory getInventoryId(Inventory inventory) {
        return inventoryRepository
            .findByProductIdAndStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
            )
            .orElseThrow(() -> new RuntimeException(
                "Inventory not found for product: " + inventory.getProduct().getId() + 
                " and store: " + inventory.getStore().getId()
            ));
    }

    // Additional useful methods
    public boolean productExistsByNameAndId(String name, Long id) {
        return productRepository.existsByNameAndIdNot(name, id);
    }

    public boolean hasSufficientStock(Long productId, Long storeId, int quantity) {
        return inventoryRepository.findByProductIdAndStoreId(productId, storeId)
            .map(inv -> inv.getQuantity() >= quantity)
            .orElse(false);
    }
}