package com.example.util;

import com.example.domain.dto.ProductDto;
import com.example.domain.entity.ProductEntity;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static ProductDto toDto(ProductEntity productEntity) {
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(productEntity, dto);
        return dto;
    }

    public static ProductEntity toEntity(ProductDto productDto) {
        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(productDto, entity);
        return entity;
    }

}
