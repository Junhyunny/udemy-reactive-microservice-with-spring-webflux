package com.example.domain.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {

    private int userId;
    private String productId;
}
