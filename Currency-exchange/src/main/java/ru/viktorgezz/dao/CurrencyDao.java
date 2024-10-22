package ru.viktorgezz.dao;

import ru.viktorgezz.util.BdConfig;
import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.mapper.CurrencyMapper;
import ru.viktorgezz.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao {

    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    public List<CurrencyDto> index() throws SQLException{
        String sql = "SELECT * FROM Currencies";

        List<CurrencyDto> currenciesDto = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Currency currencyTemp = currencyMapper.mapRowInCurrency(rs);
                currenciesDto.add(currencyMapper.convertCurrencyToDto(currencyTemp));
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

    public Optional<Currency> findCurrencyByCode(String code) throws SQLException{
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
        return findCurrencyByCode(code).map(currencyMapper::convertCurrencyToDto);
    }

    public Optional<Currency> findCurrencyById(int id) throws SQLException {
        String sql = "SELECT * FROM Currencies WHERE (id = ?)";

        try (Connection conn = DriverManager.getConnection(BdConfig.URL)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            return Optional.of(currencyMapper.mapRowInCurrency(rs));
        }
    }

    public void update() {
    }

    public void delete(int id) {
    }
}
