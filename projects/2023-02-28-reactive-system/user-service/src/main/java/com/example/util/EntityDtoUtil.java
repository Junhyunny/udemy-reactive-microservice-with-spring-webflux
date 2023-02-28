package com.example.util;

import com.example.domain.dto.TransactionRequestDto;
import com.example.domain.dto.TransactionResponseDto;
import com.example.domain.dto.TransactionStatus;
import com.example.domain.dto.UserDto;
import com.example.domain.entity.UserEntity;
import com.example.domain.entity.UserTransactionEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(UserEntity userEntity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(userEntity, dto);
        return dto;
    }

    public static UserEntity toEntity(UserDto userDto) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(userDto, entity);
        return entity;
    }

    public static UserTransactionEntity toEntity(TransactionRequestDto requestDto) {
        UserTransactionEntity entity = new UserTransactionEntity();
        entity.setUserId(requestDto.getUserId());
        entity.setAmount(requestDto.getAmount());
        entity.setTransactionDate(LocalDateTime.now());
        return entity;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus transactionStatus) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setUserId(requestDto.getUserId());
        dto.setAmount(requestDto.getAmount());
        dto.setStatus(transactionStatus);
        return dto;
    }
}
