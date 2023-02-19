package com.example.config;

import com.example.dto.InputFailedValidationResponse;
import com.example.exception.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final RequestHandler requestHandler;

    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, request) -> {
            InputValidationException exception = (InputValidationException) error;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setErrorCode(InputValidationException.getErrorCode());
            response.setMessage(exception.getMessage());
            response.setInput(exception.getInput());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}",
                        RequestPredicates
                                .path("*/1?")
                                .or(RequestPredicates.path("*/20")),
                        requestHandler::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10-19 allow"))
                .GET("square/{input}/validation", requestHandler::squareWithValidation)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                // 에외 처리
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    // 부모 router 그룹핑
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }
}
