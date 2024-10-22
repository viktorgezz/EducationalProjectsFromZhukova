package ru.viktorgezz.mapper;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.model.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateMapper {

    private final CurrencyDao currencyDAO = CurrencyDao.getInstance();


    public ExchangeRate mapRowToExchangeRate(ResultSet rs) throws SQLException {
        return new ExchangeRate.Builder()
                        .setId(rs.getInt("id"))
                        .setBaseCurrency(currencyDAO.findCurrencyById(rs.getInt("base_currency_id")).orElseThrow())
                        .setTargetCurrency(currencyDAO.findCurrencyById(rs.getInt("target_currency_id")).orElseThrow())
                        .setRate(rs.getDouble("rate"))
                        .build();
    }

    public ExchangeRateDto convertModelToDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto.Builder().setBaseCurrency(exchangeRate.getBaseCurrency())
                .setTargetCurrency(exchangeRate.getTargetCurrency())
                .setRate(exchangeRate.getRate())
                .build();
    }
}
