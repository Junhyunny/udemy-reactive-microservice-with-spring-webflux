package com.example.domain.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDto {

    private int orderId;
    private int userId;
    private String productId;
    private int amount;
    private OrderStatus status;

}
