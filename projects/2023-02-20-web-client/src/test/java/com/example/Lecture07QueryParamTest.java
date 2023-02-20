package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lecture07QueryParamTest extends BaseTest {

    private final String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";
    @Autowired
    private WebClient webClient;

    @Test
    public void queryParamsTest() {
        URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 20);
        Map<String, Integer> queryMap = Map.of(
                "count", 10,
                "page", 30
        );

        Flux<Integer> integerFlux = this.webClient
                .get()
//                .uri(uri)
//                .uri(builder -> builder.path("jobs/search").query("count={count}&page={page}").build(5, 40))
                .uri(builder -> builder.path("jobs/search").query("count={count}&page={page}").build(queryMap))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);


        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete()
        ;
    }
}
