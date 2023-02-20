package com.example;

import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture04HeadersTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        Mono<Response> responseMono = this.webClient
                .get()
                .uri("calculator/{a}/{b}", 5, 7)
                .header("OP", "*")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
