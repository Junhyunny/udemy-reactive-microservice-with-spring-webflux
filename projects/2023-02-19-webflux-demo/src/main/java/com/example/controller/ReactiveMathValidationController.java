package com.example.controller;

import com.example.dto.Response;
import com.example.exception.InputValidationException;
import com.example.service.ReactiveMathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
public class ReactiveMathValidationController {

    private final ReactiveMathService mathService;

    public ReactiveMathValidationController(ReactiveMathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return mathService.findSquare(input);
    }

    @GetMapping("/square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono
                .just(input)
                .handle((integer, sink) -> {
                    if (integer < 10 || integer > 20) {
                        sink.error(new InputValidationException(integer));
                    } else {
                        sink.next(integer);
                    }
                })
                .cast(Integer.class)
                .flatMap((integer) -> mathService.findSquare(integer));
    }

    @GetMapping("/square/{input}/assignment")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable int input) {
        return Mono
                .just(input)
                .filter(integer -> integer >= 10 && integer <= 20)
                .flatMap((integer) -> mathService.findSquare(integer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
