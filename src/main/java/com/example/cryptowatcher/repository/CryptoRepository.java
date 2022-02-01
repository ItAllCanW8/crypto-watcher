package com.example.cryptowatcher.repository;

import com.example.cryptowatcher.model.entity.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<Cryptocurrency, Long> {
    Optional<Cryptocurrency> findCryptocurrencyBySymbol(String symbol);
}
