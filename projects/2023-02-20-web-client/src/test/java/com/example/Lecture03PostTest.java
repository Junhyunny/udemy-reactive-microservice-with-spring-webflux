package com.example;

import com.example.dto.MultiplyRequestDto;
import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture03PostTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void postTest() {
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("router/multiply")
                .bodyValue(buildRequestDto(5, 7))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }
}
