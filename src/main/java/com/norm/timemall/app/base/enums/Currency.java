package com.norm.timemall.app.base.enums;

import java.util.Arrays;

public enum Currency {

    CNY("CNY", "Chinese Yuan", "¥"),
    USD("USD", "US Dollar", "$"),
    EUR("EUR", "Euro", "€"),
    JPY("JPY", "Japanese Yen", "¥"),
    GBP("GBP", "British Pound", "£");

    private final String code;
    private final String description;
    private final String symbol;

    Currency(String code, String description, String symbol) {
        this.code = code;
        this.description = description;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Safely parses a string into a Currency enum.
     *
     * @param code The currency string code (e.g., "USD", "cny")
     * @return The matching Currency enum, or CNY as a default fallback if not found.
     */
    public static Currency fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return CNY; // Default fallback matching your DB design intent
        }

        return Arrays.stream(Currency.values())
                .filter(c -> c.getCode().equalsIgnoreCase(code.trim()))
                .findFirst()
                .orElse(CNY); // Safely fall back to CNY instead of throwing an Exception
    }
}
