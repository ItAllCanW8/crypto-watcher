package com.example.cryptowatcher.service.impl;

import com.example.cryptowatcher.model.dto.CryptocurrencyDto;
import com.example.cryptowatcher.model.entity.Cryptocurrency;
import com.example.cryptowatcher.model.entity.UserSubscription;
import com.example.cryptowatcher.model.enumeration.AvailableCrypto;
import com.example.cryptowatcher.repository.CryptoRepository;
import com.example.cryptowatcher.repository.UserSubscriptionRepository;
import com.example.cryptowatcher.service.CryptoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.cryptowatcher.controller.CryptoController.url;

@Service
@Slf4j
@AllArgsConstructor
public class CryptoServiceImpl implements CryptoService {
    private final CryptoRepository cryptoRepo;
    private final UserSubscriptionRepository userSubscriptionRepo;
    private static final double MIN_NOTIFY_PERCENTAGE_RATE = 1;

    @Override
    public List<CryptocurrencyDto> loadAvailableCryptos() {
        List<CryptocurrencyDto> availCryptos = new ArrayList<>();
        Arrays.stream(AvailableCrypto.values()).forEach(crypto -> availCryptos.add(CryptocurrencyDto.builder()
                .symbol(crypto.getSymbol())
                .id(crypto.getId())
                .build()));

        return availCryptos;
    }

    @Override
    public Optional<Cryptocurrency> loadCryptoById(Long id) {
        return cryptoRepo.findById(id);
    }

    @Override
    @Transactional
    public void updateActualCryptoPrices() {
        List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        Arrays.stream(AvailableCrypto.values()).forEach(crypto -> {
            ResponseEntity<Cryptocurrency[]> response =
                    restTemplate.getForEntity(url.concat(crypto.getId().toString()),
                            Cryptocurrency[].class);
            Cryptocurrency[] cryptocurrency = response.getBody();

            if (cryptocurrency != null) {
                cryptocurrencies.add(cryptocurrency[0]);
            }
        });

        notifySubscribersOfChanges(cryptocurrencies);

        if (cryptocurrencies.size() > 0) {
            cryptoRepo.saveAll(cryptocurrencies);
        }
    }

    private void notifySubscribersOfChanges(List<Cryptocurrency> cryptocurrencies){
        List<UserSubscription> userSubscriptions = userSubscriptionRepo.findByActiveTrue();

        userSubscriptions.forEach(userSubscription -> {
            double regTimePrice = userSubscription.getRegTimePrice();
            double newPrice = cryptocurrencies.stream()
                    .filter(cryptocurrency ->
                            cryptocurrency.getSymbol()
                                    .equals(userSubscription.getCryptoSymbol()))
                    .findFirst().get().getPriceUsd();

            double result = newPrice > regTimePrice ? 100 * ((newPrice - regTimePrice) / regTimePrice) :
                    100 * ((regTimePrice - newPrice) / newPrice);

            if(result > MIN_NOTIFY_PERCENTAGE_RATE){
                result = newPrice > regTimePrice ? result : -result;
                log.warn("Crypto: {}, User: {}, % of price change since registration: {}",
                        userSubscription.getCryptoSymbol(), userSubscription.getUsername(), result);
                userSubscription.setActive(false);
                userSubscriptionRepo.save(userSubscription);
            }
        });
    }

    @Override
    @Transactional
    public UserSubscription subscribeUserToCrypto(UserSubscription userSubscription) {
        Optional<Cryptocurrency> cryptocurrencyOptional = cryptoRepo
                .findCryptocurrencyBySymbol(userSubscription.getCryptoSymbol());
        cryptocurrencyOptional.ifPresent(cryptocurrency ->
                userSubscription.setRegTimePrice(cryptocurrency.getPriceUsd()));
        return userSubscriptionRepo.save(userSubscription);
    }
}
