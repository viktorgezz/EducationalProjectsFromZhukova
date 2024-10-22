package ru.viktorgezz.dao;

import ru.viktorgezz.util.BdConfig;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.mapper.ExchangeRateMapper;
import ru.viktorgezz.model.Currency;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao {

    private final CurrencyDao currencyDAO = new CurrencyDao();
    private final static ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    public List<ExchangeRateDto> index() throws SQLException{
        String sql = "SELECT * FROM Exchange_rates";
        List<ExchangeRateDto> exchangeRates = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Optional<ExchangeRate> exchangeRateTemp = exchangeRateMapper.mapRowToExchangeRate(rs);
                exchangeRateTemp.ifPresent(exchangeRate -> exchangeRates.add(exchangeRateMapper.convertModelToDto(exchangeRate)));
            }
        }

        return exchangeRates;
    }

    public void save(ExchangeRateDto exchangeRateDto) throws SQLException {
        String sql = "INSERT INTO Exchange_rates (base_currency_id, target_currency_id, rate) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, exchangeRateDto.getBaseCurrency().getId());
            stmt.setInt(2,exchangeRateDto.getTargetCurrency().getId());
            stmt.setDouble(3, exchangeRateDto.getRate());

            stmt.executeUpdate();
        }
    }

    public void update(String code1, String code2, double rate) throws SQLException, CurrencyException {
        String sql = "UPDATE Exchange_rates SET rate = ? WHERE base_currency_id = ? and target_currency_id = ?";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, rate);
            stmt.setInt(2, findIdCurrencyByCode(code1));
            stmt.setInt(3, findIdCurrencyByCode(code2));

            stmt.executeUpdate();
        }
    }

    public Optional<ExchangeRate> findExchangeRate(String code1, String code2) throws SQLException, CurrencyException {
        String sql = "SELECT * FROM Exchange_rates WHERE (base_currency_id = ? and target_currency_id = ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, findIdCurrencyByCode(code1));
            stmt.setInt(2, findIdCurrencyByCode(code2));

            ResultSet rs = stmt.executeQuery();

            return exchangeRateMapper.mapRowToExchangeRate(rs);
        }
    }

    public Optional<ExchangeRate> findExchangeRateSafely(String code1, String code2) throws SQLException {
        try {
            return findExchangeRate(code1, code2);
        } catch (CurrencyException e) {
            return Optional.empty();
        }
    }

    private Integer findIdCurrencyByCode(String code) throws SQLException, CurrencyException {
        return currencyDAO.findCurrencyByCode(code)
                .orElseThrow(
                        () -> new CurrencyException("Валюта не найдена"))
                .getId();
    }

}
