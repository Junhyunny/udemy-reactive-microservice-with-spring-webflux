package com.example.service;

import com.example.domain.dto.PurchaseOrderResponseDto;
import com.example.repository.PurchaseOrderRepository;
import com.example.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderQueryService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        return Flux
                .fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream()) // blocking here
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
