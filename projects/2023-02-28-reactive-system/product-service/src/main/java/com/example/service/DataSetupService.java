package com.example.service;

import com.example.domain.dto.ProductDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
                .flatMap(product -> productService.insertProduct(Mono.just(product)))
                .subscribe(System.out::println);
    }
}
