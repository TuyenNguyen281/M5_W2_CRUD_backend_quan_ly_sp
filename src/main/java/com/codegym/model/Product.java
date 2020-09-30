package com.codegym.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotEmpty
    private String productName;
    @NotEmpty
    private String description;

    public Product() {
    }

    public Product(Long productId, @NotEmpty String productName, @NotEmpty String description) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
