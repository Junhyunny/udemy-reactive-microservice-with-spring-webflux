package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultHeaders(headers -> headers.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        // auth -> basic or oauth
        ClientRequest request = clientRequest.attribute("auth")
                .map(value -> "basic" .equals(value) ? withBasicAuthentication(clientRequest) : withOAuth(clientRequest))
                .orElse(clientRequest);
        return exchangeFunction.exchange(request);
    }

    private ClientRequest withBasicAuthentication(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(headers -> headers.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(headers -> headers.setBearerAuth("some-token"))
                .build();
    }
}
