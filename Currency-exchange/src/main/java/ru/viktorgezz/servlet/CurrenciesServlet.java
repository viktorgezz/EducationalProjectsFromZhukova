package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;
import ru.viktorgezz.util.exception.RequestReaderException;
import ru.viktorgezz.validate.CurrencyValidation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyDao currencyDAO = CurrencyDao.getInstance();
    private final JsonHandler jsonHandler = new JsonHandler();
    private final CurrencyValidation currencyValidation = new CurrencyValidation();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        List<CurrencyDto> currenciesDto = null;
        try {
            currenciesDto = currencyDAO.index();
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
        }
        jsonHandler.send(currenciesDto, resp, 200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        CurrencyDto currencyDTO = jsonHandler.get(req.getReader(), CurrencyDto.class);
        try {
            currencyValidation.validateCurrencies(currencyDTO);
        } catch (RequestReaderException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
            return;
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 409);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }

        try {
            currencyDAO.save(currencyDTO);
            jsonHandler.send("Пользователь сохранён", resp, 201);
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }
    }
}
