package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouterConfig {

    private final CalculatorHandler calculatorHandler;

    public CalculatorRouterConfig(CalculatorHandler calculatorHandler) {
        this.calculatorHandler = calculatorHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
        return RouterFunctions.route()
                .path("calculator", this::serverResponseCalculatorRouterFunctions)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseCalculatorRouterFunctions() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), calculatorHandler::additionHandler)
                .GET("{a}/{b}", isOperation("-"), calculatorHandler::subtractionHandler)
                .GET("{a}/{b}", isOperation("*"), calculatorHandler::multiplicationHandler)
                .GET("{a}/{b}", isOperation("/"), calculatorHandler::divisionHandler)
                .GET("{a}/{b}", request -> ServerResponse.badRequest().bodyValue("OP should be +, -, *, /."))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(
                headers -> operation.equals(headers.asHttpHeaders().toSingleValueMap().get("OP"))
        );
    }
}
