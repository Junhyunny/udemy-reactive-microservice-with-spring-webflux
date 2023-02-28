package com.example.util;

import com.example.domain.dto.PurchaseOrderResponseDto;
import com.example.domain.dto.RequestContext;
import com.example.domain.dto.TransactionRequestDto;
import com.example.domain.dto.TransactionStatus;
import com.example.domain.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

import static com.example.domain.dto.OrderStatus.COMPLETED;
import static com.example.domain.dto.OrderStatus.FAILED;
import static com.example.domain.dto.TransactionStatus.APPROVED;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());
        TransactionStatus transactionStatus = requestContext.getTransactionResponseDto().getStatus();
        purchaseOrder.setStatus(APPROVED.equals(transactionStatus) ? COMPLETED : FAILED);
        return purchaseOrder;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }
}
