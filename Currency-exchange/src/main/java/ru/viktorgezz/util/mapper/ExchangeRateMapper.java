package ru.viktorgezz.util.mapper;

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
                .setBaseCurrency(currencyDAO.getCurrencyById(rs.getInt("base_currency_id")).orElseThrow())
                .setTargetCurrency(currencyDAO.getCurrencyById(rs.getInt("target_currency_id")).orElseThrow())
                .setRate(rs.getBigDecimal("rate"))
                .build();
    }
}
