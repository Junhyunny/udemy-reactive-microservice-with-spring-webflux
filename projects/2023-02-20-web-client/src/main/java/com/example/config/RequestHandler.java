package com.example.config;

import com.example.dto.MultiplyRequestDto;
import com.example.dto.Response;
import com.example.exception.InputValidationException;
import com.example.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    private final ReactiveMathService mathService;

    public RequestHandler(ReactiveMathService mathService) {
        this.mathService = mathService;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = mathService.findSquare(input);
        // body - publisher type
        // bodyValue - normal object
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        System.out.println(serverRequest.headers());
        Mono<MultiplyRequestDto> request = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = mathService.multiply(request);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareWithValidation(ServerRequest serverRequest) {
        int input = Integer.valueOf(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
//            InputFailedValidationResponse response = new InputFailedValidationResponse();
//            return ServerResponse.badRequest().bodyValue(response);
            // who will be handle it?
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> responseMono = mathService.findSquare(input);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }
}
