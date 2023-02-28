package com.example.controller;

import com.example.domain.dto.PurchaseOrderRequestDto;
import com.example.domain.dto.PurchaseOrderResponseDto;
import com.example.service.OrderFulfillmentService;
import com.example.service.OrderQueryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;

    public PurchaseOrderController(OrderFulfillmentService orderFulfillmentService, OrderQueryService orderQueryService) {
        this.orderFulfillmentService = orderFulfillmentService;
        this.orderQueryService = orderQueryService;
    }

    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> purchaseOrderRequestDtoMono) {
        return orderFulfillmentService.processOrder(purchaseOrderRequestDtoMono);
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId) {
        return orderQueryService.getProductsByUserId(userId);
    }
}
