package com.example.service;

import com.example.domain.dto.TransactionRequestDto;
import com.example.domain.dto.TransactionResponseDto;
import com.example.domain.dto.TransactionStatus;
import com.example.domain.entity.UserTransactionEntity;
import com.example.repository.UserRepository;
import com.example.repository.UserTransactionRepository;
import com.example.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public TransactionService(UserRepository userRepository, UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return userRepository
                .updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(result -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(userTransactionEntity -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransactionEntity> getByUserId(int userId) {
        return userTransactionRepository.findByUserId(userId);
    }
}
