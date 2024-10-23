package ru.viktorgezz.dao;

import ru.viktorgezz.util.BdConfig;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.mapper.ExchangeRateMapper;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.converter.ExchangeConverter;
import ru.viktorgezz.util.converter.ExchangeRateConverter;
import ru.viktorgezz.util.exception.CurrencyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao {
    private static final ExchangeRateDao instance = new ExchangeRateDao();

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return instance;
    }

    private final CurrencyDao currencyDAO = CurrencyDao.getInstance();
    private final static ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    public List<ExchangeRateDto> index() throws SQLException{
        String sql = "SELECT * FROM Exchange_rates";
        List<ExchangeRateDto> exchangeRates = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Optional<ExchangeRate> exchangeRateTemp = Optional.of(exchangeRateMapper.mapRowToExchangeRate(rs));
                exchangeRateTemp.ifPresent(exchangeRate -> exchangeRates.add(ExchangeRateConverter.convertModelToDto(exchangeRate)));
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
            stmt.setInt(2, currencyDAO.getIdCurrencyByCode(code1));
            stmt.setInt(3, currencyDAO.getIdCurrencyByCode(code2));

            stmt.executeUpdate();
        }
    }

    public Optional<ExchangeRate> findExchangeRate(String code1, String code2) throws SQLException, CurrencyException {
        String sql = "SELECT * FROM Exchange_rates WHERE (base_currency_id = ? and target_currency_id = ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currencyDAO.getIdCurrencyByCode(code1));
            stmt.setInt(2, currencyDAO.getIdCurrencyByCode(code2));

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(exchangeRateMapper.mapRowToExchangeRate(rs));
        }
    }

    public Optional<ExchangeRate> findExchangeRateSafely(String code1, String code2) throws SQLException {
        try {
            return findExchangeRate(code1, code2);
        } catch (CurrencyException e) {
            return Optional.empty();
        }
    }
}
