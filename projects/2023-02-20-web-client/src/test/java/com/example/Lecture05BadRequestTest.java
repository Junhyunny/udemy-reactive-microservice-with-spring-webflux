package com.example;

import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture05BadRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {
        Mono<Response> responseMono = this.webClient
                .get()
                .uri("router/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(error -> System.out.println(error.getMessage()));


        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class)
        ;
    }
}
