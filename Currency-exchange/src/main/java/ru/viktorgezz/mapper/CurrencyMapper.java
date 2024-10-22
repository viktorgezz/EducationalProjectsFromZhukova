package ru.viktorgezz.mapper;

import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public CurrencyDto convertCurrencyToDto(Currency currency) {
        CurrencyDto currencyDTO = new CurrencyDto();
        currencyDTO.setCode(currency.getCode());
        currencyDTO.setName(currency.getName());
        currencyDTO.setSign(currency.getSign());
        return currencyDTO;
    }

    public Currency mapRowInCurrency(ResultSet rs) throws SQLException {
        Currency currency = new Currency();

        currency.setId(rs.getInt("id"));
        currency.setCode(rs.getString("code"));
        currency.setName(rs.getString("full_name"));
        currency.setSign(rs.getString("sign"));

        return currency;
    }
}
