package com.example.service;

import com.example.domain.dto.ProductDto;
import com.example.repository.ProductRepository;
import com.example.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Sinks.Many<ProductDto> sinks;

    public ProductService(ProductRepository productRepository, Sinks.Many<ProductDto> sinks) {
        this.productRepository = productRepository;
        this.sinks = sinks;
    }

    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getProductsByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto)
                .doOnNext(sinks::tryEmitNext);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(productEntity -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setId(id))
                )
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
