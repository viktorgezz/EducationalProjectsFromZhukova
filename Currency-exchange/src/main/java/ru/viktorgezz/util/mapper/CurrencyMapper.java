package ru.viktorgezz.mapper;

import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CurrencyMapper {
    public Currency mapRowInCurrency(ResultSet rs) throws SQLException {
        return new Currency.Builder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("full_name"))
                .setCode(rs.getString("code"))
                .setSign(rs.getString("sign"))
                .build();
    }
}
