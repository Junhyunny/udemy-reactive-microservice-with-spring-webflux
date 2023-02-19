package com.example.service;

import com.example.dto.Response;
import com.example.util.SleepUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public Response findSquare(int input) {
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(index -> SleepUtil.sleepSeconds(1))
                .peek(index -> System.out.printf("math service procession %s\n", index))
                .mapToObj((index) -> new Response(index * input))
                .collect(Collectors.toList());
    }
}
