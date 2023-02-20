package com.example;

import com.example.dto.InputFailedValidationResponse;
import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture06BadRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    // exchange = retrieve + additional info http status code
    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("router/square/{input}/validation", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(error -> System.out.println(error.getMessage()));


        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete()
        ;
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.statusCode() == HttpStatus.BAD_REQUEST) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        }
        return clientResponse.bodyToMono(Response.class);
    }
}
