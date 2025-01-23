package com.cintest.orders.service.order;


import com.cintest.orders.dto.order.ProductRequest;
import com.cintest.orders.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
}
