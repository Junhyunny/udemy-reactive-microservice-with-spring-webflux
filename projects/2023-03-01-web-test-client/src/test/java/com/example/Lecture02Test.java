package com.example;

import com.example.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RestController
@RequestMapping("router")
class TestController {

    @GetMapping("square/{input}")
    public Mono<Response> getInput(@PathVariable int input) {
        return Mono.just(new Response(input * input));
    }
}

@WebFluxTest(TestController.class)
public class Lecture02Test {

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
                .value(res -> Assertions.assertThat(res.getOutput()).isEqualTo(225));
    }

}
