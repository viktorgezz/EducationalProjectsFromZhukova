package ru.viktorgezz.dao;

import ru.viktorgezz.util.BdConfig;
import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.util.mapper.CurrencyMapper;
import ru.viktorgezz.model.Currency;
import ru.viktorgezz.util.converter.CurrencyConverter;
import ru.viktorgezz.util.exception.CurrencyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao {
    private static final CurrencyDao instance = new CurrencyDao();

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return instance;
    }

    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    public List<CurrencyDto> index() throws SQLException {
        String sql = "SELECT * FROM Currencies";

        List<CurrencyDto> currenciesDto = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Currency currencyTemp = currencyMapper.mapRowInCurrency(rs);
                currenciesDto.add(CurrencyConverter.convertModelToDto(currencyTemp));
            }
        }

        return currenciesDto;
    }

    public void save(CurrencyDto currencyDto) throws SQLException {
        String sql = "INSERT INTO Currencies(code, full_name, sign) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, currencyDto.getCode());
            stmt.setString(2, currencyDto.getName());
            stmt.setString(3, currencyDto.getSign());
            stmt.executeUpdate();
        }
    }

    public Optional<Currency> findCurrencyByCode(String code) throws SQLException {
        String sql = "SELECT * FROM Currencies WHERE (code = ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return Optional.empty();

            return Optional.of(currencyMapper.mapRowInCurrency(rs));
        }
    }

    public Optional<CurrencyDto> findCurrencyDtoByCode(String code) throws SQLException {
        return findCurrencyByCode(code).map(CurrencyConverter::convertModelToDto);
    }

    public Optional<Currency> getCurrencyById(int id) throws SQLException {
        String sql = "SELECT * FROM Currencies WHERE (id = ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(currencyMapper.mapRowInCurrency(rs));
        }
    }

    public Integer getIdCurrencyByCode(String code) throws SQLException, CurrencyException {
        final String param = "id";
        String sql = "SELECT " + param + " FROM Currencies WHERE (code = ?)";
        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new CurrencyException("Валюта не найдена " + code);
            }
            return stmt.executeQuery().getInt(param);
        }
    }

    public void update() {
    }

    public void delete(int id) {
    }
}
