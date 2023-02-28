package com.example.repository;

import com.example.domain.entity.UserTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity, Integer> {

    Flux<UserTransactionEntity> findByUserId(int userId);
}
