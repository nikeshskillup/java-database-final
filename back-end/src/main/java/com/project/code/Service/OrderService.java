package com.project.code.Service;

import com.project.code.Dto.PlaceOrderRequestDTO;
import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Handle Customer
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer(
                            placeOrderRequest.getCustomerName(),
                            placeOrderRequest.getCustomerEmail(),
                            placeOrderRequest.getCustomerPhone()
                    );
                    return customerRepository.save(newCustomer);
                });

        // 2. Retrieve Store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + placeOrderRequest.getStoreId()));

        // 3. Create OrderDetails
        OrderDetails order = new OrderDetails();
        order.setCustomer(customer);
        order.setStore(store);
        order.setTotalPrice(placeOrderRequest.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order = orderDetailsRepository.save(order);

        // 4. Process Order Items
        for (PlaceOrderRequestDTO.OrderItemDTO itemDTO : placeOrderRequest.getItems()) {
            // Get Product
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDTO.getProductId()));

            // Update Inventory
            Inventory inventory = inventoryRepository.findByProductIdAndStoreId(
                            itemDTO.getProductId(), placeOrderRequest.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + itemDTO.getProductId()));

            if (inventory.getQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            inventory.setQuantity(inventory.getQuantity() - itemDTO.getQuantity());
            inventoryRepository.save(inventory);

            // Create Order Item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);
        }
    }

    // Additional useful methods
    public List<OrderDetails> getOrdersByCustomerEmail(String email) {
        return orderDetailsRepository.findByCustomerEmail(email);
    }

    public List<OrderDetails> getOrdersByStore(Long storeId) {
        return orderDetailsRepository.findByStoreId(storeId);
    }
}