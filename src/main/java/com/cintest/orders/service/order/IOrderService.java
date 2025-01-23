package com.cintest.orders.service.order;

import com.cintest.orders.dto.order.OrderRequest;
import com.cintest.orders.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();
    Order getOrderById(int orderId);
    Order createOrder(OrderRequest orderRequest);
    Order updateOrder(int orderId, OrderRequest orderRequest);
}
