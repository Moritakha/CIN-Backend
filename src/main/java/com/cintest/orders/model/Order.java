package com.cintest.orders.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int units;
    private int bonus;
    private int promo;
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_number", nullable = false)
    private Product product;
}
