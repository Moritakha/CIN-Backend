package com.cintest.orders.service.order;

import com.cintest.orders.dto.order.OrderRequest;
import com.cintest.orders.model.Order;
import com.cintest.orders.model.Product;
import com.cintest.orders.repository.OrderRepository;
import com.cintest.orders.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Integer productId = Integer.parseInt(orderRequest.getProductId());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Order order = orderRequest.toEntity(product);
        this.setProduct(order, productId);
        order.setTotalPrice(this.getTotalPrice(order));
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(int orderId, OrderRequest orderRequest) {
        Order order = this.getOrderById(orderId);
        this.validate(order, orderRequest);

        Integer productId = Integer.parseInt(orderRequest.getProductId());
        if (!productId.equals(order.getProduct().getId())) {
            this.setProduct(order, productId);
        }

        order.setUnits(orderRequest.getUnits());
        order.setBonus(orderRequest.getBonus());
        order.setPromo(orderRequest.getPromo());
        order.setTotalPrice(this.getTotalPrice(order));
        return orderRepository.save(order);
    }

    public void validate(Order order, OrderRequest orderRequest) {
        if (orderRequest.getUnits() > order.getUnits()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't increase the number of units");
        }
        if (orderRequest.getBonus() > order.getBonus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't increase the bonus");
        }
        if (orderRequest.getPromo() > order.getPromo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't increase the promo");
        }
    }

    public int getTotalPrice(Order order) {
        return order.getProduct().getPrice() * (order.getUnits() + order.getBonus() + order.getPromo());
    }

    public void setProduct(Order order, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        order.setProduct(product);
    }
}