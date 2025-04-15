package com.project.code.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Store name cannot be blank")
    @NotNull(message = "Store name cannot be null")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @NotNull(message = "Address cannot be null")
    private String address;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference("inventory-store")
    private List<Inventory> inventories;

    // Constructors
    public Store() {
    }

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    // toString() method (optional but useful for debugging)
    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}