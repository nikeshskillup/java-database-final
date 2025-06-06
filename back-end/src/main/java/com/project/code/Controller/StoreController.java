package com.project.code.Controller;

import com.project.code.Dto.PlaceOrderRequestDTO;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Map<String, String> response = new HashMap<>();
        try {
            Store savedStore = storeRepository.save(store);
            response.put("message", "Store created successfully with ID: " + savedStore.getId());
        } catch (Exception e) {
            response.put("message", "Error creating store: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {
        return storeRepository.existsById(storeId);
    }

    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO orderRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(orderRequest);
            response.put("message", "Order placed successfully");
        } catch (Exception e) {
            response.put("Error", "Failed to place order: " + e.getMessage());
        }
        return response;
    }

    // Additional useful endpoint
    @GetMapping
    public Map<String, Object> getAllStores() {
        Map<String, Object> response = new HashMap<>();
        response.put("stores", storeRepository.findAll());
        return response;
    }
}