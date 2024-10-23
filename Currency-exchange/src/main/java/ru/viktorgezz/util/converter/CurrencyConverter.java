package ru.viktorgezz.util.converter;

import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.model.Currency;

public class CurrencyConverter {
    public static CurrencyDto convertModelToDto(Currency currency) {
        return new CurrencyDto.Builder()
                .setCode(currency.getCode())
                .setName(currency.getName())
                .setSign(currency.getSign())
                .build();
    }
}
