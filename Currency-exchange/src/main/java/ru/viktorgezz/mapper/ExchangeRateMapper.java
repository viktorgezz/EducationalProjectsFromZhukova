package ru.viktorgezz.mapper;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.model.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ExchangeRateMapper {

    private final CurrencyDao currencyDAO = new CurrencyDao();


    public Optional<ExchangeRate> mapRowToExchangeRate(ResultSet rs) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        if (rs.getString("base_currency_id") == null) {
            return Optional.empty();
        }
        exchangeRate.setId(rs.getInt("id"));
        exchangeRate.setBaseCurrency(currencyDAO.findCurrencyById(rs.getInt("base_currency_id")).get());
        exchangeRate.setTargetCurrency(currencyDAO.findCurrencyById(rs.getInt("target_currency_id")).get());
        exchangeRate.setRate(rs.getDouble("rate"));

        return Optional.of(exchangeRate);
    }

//    public ExchangeRateDto mapRowToExchangeRateDTO(ResultSet rs) throws SQLException {
//        ExchangeRateDto exchangeRateDTO = new ExchangeRateDto();
//        exchangeRateDTO.setBaseCurrency(currencyDAO.findCurrencyById(rs.getInt("base_currency_id")));
//        exchangeRateDTO.setTargetCurrency(currencyDAO.findCurrencyById(rs.getInt("target_currency_id")));
//        exchangeRateDTO.setRate(rs.getDouble("rate"));
//
//        return exchangeRateDTO;
//    }

    public ExchangeRateDto convertModelToDto(ExchangeRate exchangeRate) {
        ExchangeRateDto exchangeRateDTO = new ExchangeRateDto();

        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateDTO.setRate(exchangeRate.getRate());

        return exchangeRateDTO;
    }
}
