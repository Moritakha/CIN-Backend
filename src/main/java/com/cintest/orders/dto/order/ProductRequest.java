package com.cintest.orders.dto.order;

import com.cintest.orders.model.Product;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private int price;

    public Product toEntity() {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
