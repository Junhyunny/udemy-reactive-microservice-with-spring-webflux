package com.example;

import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lecture02GetMultipleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest() {
        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("router/table/{input}", 4)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxTestStream() {
        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("router/table/{input}/stream", 4)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
