package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture10AttributeTest extends BaseTest {

    private final String FORMAT = "%d %s %d = %s";
    private final int A = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void calculateTest() {
        Flux<String> stringFlux = Flux.range(1, 10)
                .flatMap(b -> Flux.just("+", "-", "*", "/").flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);


        StepVerifier.create(stringFlux)
                .expectNextCount(40)
                .verifyComplete();
    }

    private Mono<String> send(int b, String op) {
        return this.webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(httpHeaders -> httpHeaders.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(value -> String.format(FORMAT, A, op, b, value))
                ;
    }
}
