package ru.viktorgezz.util.converter;

import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.model.ExchangeRate;

public class ExchangeRateConverter {
    public static ExchangeRateDto convertModelToDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto.Builder().setBaseCurrency(exchangeRate.getBaseCurrency())
                .setTargetCurrency(exchangeRate.getTargetCurrency())
                .setRate(exchangeRate.getRate())
                .build();
    }
}
