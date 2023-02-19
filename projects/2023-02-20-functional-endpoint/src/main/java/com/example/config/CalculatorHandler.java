package com.example.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    // creating multiple handlers intentionally
    public Mono<ServerResponse> additionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> {
            if (b == 0) {
                return ServerResponse.badRequest().bodyValue("b cannot be 0");
            }
            return ServerResponse.ok().bodyValue(a / b);
        });
    }

    public Mono<ServerResponse> process(ServerRequest serverRequest,
                                        BiFunction<Integer, Integer, Mono<ServerResponse>> operationLogic) {
        int a = getValue(serverRequest, "a");
        int b = getValue(serverRequest, "b");
        return operationLogic.apply(a, b);
    }

    private int getValue(ServerRequest serverRequest, String key) {
        return Integer.parseInt(serverRequest.pathVariable(key));
    }
}
