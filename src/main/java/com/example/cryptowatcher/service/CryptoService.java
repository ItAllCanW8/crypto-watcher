package com.example.cryptowatcher.service;

import com.example.cryptowatcher.model.dto.CryptocurrencyDto;
import com.example.cryptowatcher.model.entity.Cryptocurrency;
import com.example.cryptowatcher.model.entity.UserSubscription;

import java.util.List;
import java.util.Optional;

public interface CryptoService {
    List<CryptocurrencyDto> loadAvailableCryptos();
    Optional<Cryptocurrency> loadCryptoById(Long id);
    void updateActualCryptoPrices();
    UserSubscription subscribeUserToCrypto(UserSubscription userSubscription);
}
