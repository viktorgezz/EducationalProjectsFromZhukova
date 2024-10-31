package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.util.mapper.ExchangeRateMapper;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.util.SearchCurrencyPair;
import ru.viktorgezz.dto.ExchangeDto;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.converter.ExchangeConverter;
import ru.viktorgezz.util.exception.CurrencyException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(urlPatterns = "/exchange")
public class ExchangeServlet extends HttpServlet {
    private final SearchCurrencyPair searchCurrencyPair = new SearchCurrencyPair();
    private final JsonHandler jsonHandler = new JsonHandler();
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        ExchangeRate exchangeRate;
        try {
            exchangeRate = searchCurrencyPair.perform(
                    req.getParameter("from"), req.getParameter("to"))
                    .orElseThrow();
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }

        ExchangeDto exchangeDTO = ExchangeConverter
                .convertPartsOfModelToDto(
                        exchangeRate,
                        amount,
                        searchCurrencyPair.calculateConvertedAmount(exchangeRate, amount));

        jsonHandler.send(exchangeDTO, resp, 200);
    }
}
