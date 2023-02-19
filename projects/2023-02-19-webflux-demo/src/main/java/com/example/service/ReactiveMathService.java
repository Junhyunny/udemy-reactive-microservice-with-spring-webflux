package com.example.service;

import com.example.dto.MultiplyRequestDto;
import com.example.dto.Response;
import com.example.util.SleepUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
//                .doOnNext(index -> SleepUtil.sleepSeconds(1))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(index -> System.out.printf("math service procession %s\n", index))
                .map(index -> new Response(index * input));
    }

    public Flux<Response> multiplicationTableAsList(int input) {
        // this is not reactive style
        // pipeline is not connected with client
        List<Response> result = IntStream.rangeClosed(1, 10)
                .peek(index -> SleepUtil.sleepSeconds(1))
                .peek(index -> System.out.printf("math service procession %s\n", index))
                .mapToObj(index -> new Response(index * input))
                .collect(Collectors.toList());
        return Flux.fromIterable(result);
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> multiplyRequest) {
        return multiplyRequest
                .map(request -> request.getFirst() * request.getSecond())
                .map(Response::new);
    }
}
