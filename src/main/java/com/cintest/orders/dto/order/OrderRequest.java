package com.cintest.orders.dto.order;

import com.cintest.orders.model.Order;
import com.cintest.orders.model.Product;
import lombok.Data;

@Data
public class OrderRequest {
    private int units;
    private int bonus;
    private int promo;
    private String productId;

    public Order toEntity(Product product) {
        Order order = new Order();
        order.setUnits(units);
        order.setBonus(bonus);
        order.setPromo(promo);
        order.setProduct(product);
        return order;
    }
}