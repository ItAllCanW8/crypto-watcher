package com.example.cryptowatcher.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class CryptocurrencyDto {
    private Long id;
    private String symbol;
}
