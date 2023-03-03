package com.example;

import com.example.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lecture01Test {

    @Autowired
    WebTestClient webClient;

    @Test
    public void stepVerifierTest() {
        Flux<Response> response = this.webClient
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();


        StepVerifier.create(response)
                .expectNextMatches(res -> res.getOutput() == 225)
                .verifyComplete()
        ;
    }

    @Test
    public void fluentAssertionTest() {
        this.webClient
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(res -> Assertions.assertThat(res.getOutput()).isEqualTo(225) );
    }

}
