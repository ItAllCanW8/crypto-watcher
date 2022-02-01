package com.example.cryptowatcher.controller;

import com.example.cryptowatcher.model.dto.CryptocurrencyDto;
import com.example.cryptowatcher.model.dto.UserSubscriptionDto;
import com.example.cryptowatcher.model.entity.Cryptocurrency;
import com.example.cryptowatcher.model.entity.UserSubscription;
import com.example.cryptowatcher.model.enumeration.AvailableCrypto;
import com.example.cryptowatcher.service.CryptoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class CryptoController {
    public static final String url = "https://api.coinlore.net/api/ticker/?id=";
    private final CryptoService cryptoService;

    @GetMapping("/cryptocurrencies")
    public List<CryptocurrencyDto> loadAvailableCryptos() {
        return cryptoService.loadAvailableCryptos();
    }

    @GetMapping("/cryptocurrency/{id}")
    public Optional<Cryptocurrency> loadCryptocurrencyById(@PathVariable Long id) {
        Optional<Cryptocurrency> result = Optional.empty();
        if (Arrays.stream(AvailableCrypto.values())
                .anyMatch(crypto -> crypto.getId().equals(id))) {
            result = cryptoService.loadCryptoById(id);
        }
        return result;
    }

    @PostMapping("/cryptocurrency/notify")
    public ResponseEntity<?> subscribeUserToCrypto(
            @RequestBody UserSubscriptionDto userSubscriptionDto) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().body("Error :OOO");

        if (Arrays.stream(AvailableCrypto.values())
                .anyMatch(crypto -> crypto.getSymbol().equals(userSubscriptionDto.getSymbol()))) {
            UserSubscription userSubscription = UserSubscription.builder()
                    .username(userSubscriptionDto.getUsername())
                    .cryptoSymbol(userSubscriptionDto.getSymbol())
                    .active(true)
                    .build();
            if (cryptoService.subscribeUserToCrypto(userSubscription).getId() > 0) {
                response = ResponseEntity.ok("Subscribed successfully.");
            }
        }
        return response;
    }
}
