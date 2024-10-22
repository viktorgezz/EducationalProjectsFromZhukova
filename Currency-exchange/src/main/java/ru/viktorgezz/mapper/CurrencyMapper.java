package ru.viktorgezz.mapper;

import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper {

    public CurrencyDto convertCurrencyToDto(Currency currency) {
        return new CurrencyDto.Builder()
                .setCode(currency.getCode())
                .setName(currency.getName())
                .setSign(currency.getSign())
                .build();
    }

    public Currency mapRowInCurrency(ResultSet rs) throws SQLException {
        System.out.println(rs.getInt("id"));
        return new Currency.Builder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("full_name"))
                .setCode(rs.getString("code"))
                .setSign(rs.getString("sign"))
                .build();
    }
}
