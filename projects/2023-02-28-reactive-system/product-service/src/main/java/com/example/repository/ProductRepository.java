package com.example.repository;

import com.example.domain.entity.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

    //    Flux<ProductEntity> findByPriceBetween(int min, int max);
    Flux<ProductEntity> findByPriceBetween(Range<Integer> range);
}
