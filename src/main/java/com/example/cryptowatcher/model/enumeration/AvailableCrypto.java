package com.example.cryptowatcher.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum AvailableCrypto {
    BITCOIN(90L, "BTC"),
    ETHEREUM(80L, "ETH"),
    SOLANA(48543L, "SOL");

    private String symbol;
    private Long id;

    AvailableCrypto(Long id, String symbol) {
        this.symbol = symbol;
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getId() {
        return id;
    }
}
