package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;
import ru.viktorgezz.util.exception.RequestReaderException;
import ru.viktorgezz.validate.ExchangeRateValidation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateDao exchangeRateDAO = ExchangeRateDao.getInstance();
    private final ExchangeRateValidation exchangeRateValidation = new ExchangeRateValidation();
    private final JsonHandler jsonHandler = new JsonHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        try {
            List<ExchangeRateDto> exchangeRates = exchangeRateDAO.index();
            jsonHandler.send(exchangeRates, resp, 200);
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ExchangeRateDto exchangeRateDto = jsonHandler.get(req.getReader(), ExchangeRateDto.class);

        try {
            exchangeRateValidation.validateExchangeRateDto(exchangeRateDto);
        } catch (RequestReaderException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
            return;
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
            return;
        } catch (ExchangeRateException e) {
            jsonHandler.send(e.getMessage(), resp, 409);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }

        try {
            exchangeRateDAO.save(exchangeRateDto);
            jsonHandler.send("Курс добавлен", resp, 201);
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }
    }
}
