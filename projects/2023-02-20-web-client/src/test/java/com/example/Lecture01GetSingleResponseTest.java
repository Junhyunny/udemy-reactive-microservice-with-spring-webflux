package com.example;

import com.example.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        Response response = this.webClient
                .get()
                .uri("router/square/{input}", 15)
                .retrieve()
                .bodyToMono(Response.class)
                .block();


        System.out.println(response);
    }

    @Test
    public void stepVerifierTest() {
        Mono<Response> response = this.webClient
                .get()
                .uri("router/square/{input}", 15)
                .retrieve()
                .bodyToMono(Response.class);


        StepVerifier.create(response)
                .expectNextMatches(res -> res.getOutput() == 225)
                .verifyComplete()
        ;
    }
}
