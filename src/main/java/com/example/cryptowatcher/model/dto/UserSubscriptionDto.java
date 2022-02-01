package com.example.cryptowatcher.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSubscriptionDto {
    private String username;
    private String symbol;
}
