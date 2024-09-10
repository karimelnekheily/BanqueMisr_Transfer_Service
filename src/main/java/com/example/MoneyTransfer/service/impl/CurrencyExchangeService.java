package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.enums.Currency;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class CurrencyExchangeService {

    private final Map<Currency, Map<Currency, Double>> exchangeRates;

    public CurrencyExchangeService() {
        exchangeRates = new EnumMap<>(Currency.class);
        initializeExchangeRates();
    }

    private void initializeExchangeRates() {
        // These are example rates. In a real-world scenario, you'd fetch these from an external API or database
        setExchangeRate(Currency.EGY, Currency.USD, 0.032);
        setExchangeRate(Currency.EGY, Currency.EUR, 0.030);
        setExchangeRate(Currency.EGY, Currency.SAR, 0.12);
        setExchangeRate(Currency.EGY, Currency.GBP, 0.026);

        setExchangeRate(Currency.USD, Currency.EGY, 31.25);
        setExchangeRate(Currency.USD, Currency.EUR, 0.93);
        setExchangeRate(Currency.USD, Currency.SAR, 3.75);
        setExchangeRate(Currency.USD, Currency.GBP, 0.81);

        setExchangeRate(Currency.EUR, Currency.EGY, 33.33);
        setExchangeRate(Currency.EUR, Currency.USD, 1.07);
        setExchangeRate(Currency.EUR, Currency.SAR, 4.00);
        setExchangeRate(Currency.EUR, Currency.GBP, 0.86);

        setExchangeRate(Currency.SAR, Currency.EGY, 8.33);
        setExchangeRate(Currency.SAR, Currency.USD, 0.27);
        setExchangeRate(Currency.SAR, Currency.EUR, 0.25);
        setExchangeRate(Currency.SAR, Currency.GBP, 0.22);

        setExchangeRate(Currency.GBP, Currency.EGY, 38.46);
        setExchangeRate(Currency.GBP, Currency.USD, 1.23);
        setExchangeRate(Currency.GBP, Currency.EUR, 1.16);
        setExchangeRate(Currency.GBP, Currency.SAR, 4.62);
    }

    private void setExchangeRate(Currency from, Currency to, double rate) {
        exchangeRates.computeIfAbsent(from, k -> new EnumMap<>(Currency.class)).put(to, rate);
    }

    public double convertCurrency(double amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) {
            return amount;
        }

        Double rate = exchangeRates.get(fromCurrency).get(toCurrency);
        if (rate == null) {
            throw new IllegalArgumentException("Exchange rate not available for " + fromCurrency + " to " + toCurrency);
        }

        return amount * rate;
    }
}