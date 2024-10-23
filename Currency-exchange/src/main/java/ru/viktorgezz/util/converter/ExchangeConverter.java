package ru.viktorgezz.util.converter;

import ru.viktorgezz.dto.ExchangeDto;
import ru.viktorgezz.model.Exchange;
import ru.viktorgezz.model.ExchangeRate;

public class ExchangeConverter {
    public static ExchangeDto convertPartsOfModelToDto(ExchangeRate exchangeRate, double amount,double convertedAmount) {
        return new ExchangeDto.Builder()
                .setExchangeRate(exchangeRate)
                .setAmount(amount)
                .setConvertedAmount(convertedAmount)
                .build();
    }
}
