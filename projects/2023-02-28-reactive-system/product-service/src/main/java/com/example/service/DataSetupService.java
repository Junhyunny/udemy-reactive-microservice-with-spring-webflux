package com.example.service;

import com.example.domain.dto.ProductDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    public DataSetupService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<ProductDto> dtoList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            ProductDto dto = new ProductDto();
            dto.setDescription("TV-".concat(UUID.randomUUID().toString()));
            dto.setPrice(new Random().nextInt(200));
            dtoList.add(dto);
        }
        Flux.fromIterable(dtoList)
                .concatWith(newProducts())
                .flatMap(product -> productService.insertProduct(Mono.just(product)))
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> newProducts() {
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(index -> new ProductDto("product" + index, ThreadLocalRandom.current().nextInt(10, 100)));
    }
}
