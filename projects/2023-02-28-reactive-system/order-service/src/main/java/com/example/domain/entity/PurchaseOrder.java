package com.example.domain.entity;

import com.example.domain.dto.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private int id;
    private String productId;
    private int userId;
    private int amount;
    private OrderStatus status;
}
