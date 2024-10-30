package ru.viktorgezz.util.converter;

import ru.viktorgezz.dto.ExchangeDto;
import ru.viktorgezz.model.Exchange;
import ru.viktorgezz.model.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeConverter {
    public static ExchangeDto convertPartsOfModelToDto(ExchangeRate exchangeRate, BigDecimal amount, BigDecimal convertedAmount) {
        return new ExchangeDto.Builder()
                .setExchangeRate(exchangeRate)
                .setAmount(amount)
                .setConvertedAmount(convertedAmount)
                .build();
    }
}
