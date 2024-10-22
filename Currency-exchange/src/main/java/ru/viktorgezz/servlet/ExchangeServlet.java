package ru.viktorgezz.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.viktorgezz.util.SearchCurrencyPair;
import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.dto.ExchangeDto;
import ru.viktorgezz.model.ExchangeRate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(urlPatterns = "/exchange")
public class ExchangeServlet extends HttpServlet {

    private final CurrencyDao currencyDAO = new CurrencyDao();
    private final ExchangeRateDao exchangeRateDAO = new ExchangeRateDao();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SearchCurrencyPair searchCurrencyPair = new SearchCurrencyPair();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String formCode = req.getParameter("from");
        String toCode = req.getParameter("to");
        double amount = Double.parseDouble(req.getParameter("amount"));

        Optional<ExchangeRate> exchangeRateOpt = null;
        try {
            exchangeRateOpt = searchCurrencyPair.perform(formCode, toCode);
        } catch (SQLException e) {
            throw new RuntimeException(e); // вгимагте
        }
        if (exchangeRateOpt.isEmpty()) {
            String jsonResponse = objectMapper.writeValueAsString(new RuntimeException("Error search"));
            resp.getWriter().write(jsonResponse);

        } else {
            ExchangeRate exchangeRate = exchangeRateOpt.get();
            double convertedAmount = searchCurrencyPair.calculateConvertedAmount(exchangeRate, amount);

            ExchangeDto exchangeDTO = new ExchangeDto();
            exchangeDTO.setExchangeRate(exchangeRate);
            exchangeDTO.setAmount(amount);
            exchangeDTO.setConvertedAmount(convertedAmount); // convert

            String jsonResponse = objectMapper.writeValueAsString(exchangeDTO);
            resp.getWriter().write(jsonResponse);
        }
    }
}
