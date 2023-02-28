package com.example.controller;

import com.example.domain.dto.TransactionRequestDto;
import com.example.domain.dto.TransactionResponseDto;
import com.example.domain.entity.UserTransactionEntity;
import com.example.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    private final TransactionService transactionService;

    public UserTransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransactionEntity> getByUserId(@RequestParam int userId) {
        return transactionService.getByUserId(userId);
    }
}
