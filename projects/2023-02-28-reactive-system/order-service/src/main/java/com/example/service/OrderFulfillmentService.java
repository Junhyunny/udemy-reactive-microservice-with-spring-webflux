package com.example.service;

import com.example.client.ProductClient;
import com.example.client.UserClient;
import com.example.domain.dto.PurchaseOrderRequestDto;
import com.example.domain.dto.PurchaseOrderResponseDto;
import com.example.domain.dto.RequestContext;
import com.example.repository.PurchaseOrderRepository;
import com.example.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;

    public OrderFulfillmentService(PurchaseOrderRepository purchaseOrderRepository,
                                   ProductClient productClient,
                                   UserClient userClient) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
    }

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(purchaseOrderRepository::save) // blocking layer
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic())
                ;
    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return productClient.getProductById(
                        requestContext.getPurchaseOrderRequestDto().getProductId()
                )
                .doOnNext(requestContext::setProductDto)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return userClient.authorizeTransaction(
                        requestContext.getTransactionRequestDto()
                )
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}
