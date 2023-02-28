package com.example;

import com.example.client.ProductClient;
import com.example.client.UserClient;
import com.example.domain.dto.ProductDto;
import com.example.domain.dto.PurchaseOrderRequestDto;
import com.example.domain.dto.PurchaseOrderResponseDto;
import com.example.domain.dto.UserDto;
import com.example.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Test
    void contextLoads() {

        Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(tuple -> buildDto(tuple.getT1(), tuple.getT2()))
                .flatMap(dto -> orderFulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);


        StepVerifier.create(dtoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setUserId(userDto.getId());
        dto.setProductId(productDto.getId());
        return dto;
    }

}
