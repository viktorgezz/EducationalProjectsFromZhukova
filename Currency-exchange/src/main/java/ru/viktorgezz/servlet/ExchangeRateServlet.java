package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.util.PathInfo;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;
import ru.viktorgezz.validate.CurrencyValidation;
import ru.viktorgezz.validate.ExchangeRateValidation;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDao();
    private final ExchangeRateValidation exchangeRateValidation = new ExchangeRateValidation();
    private final CurrencyValidation currencyValidation = new CurrencyValidation();
    private final JsonHandler jsonHandler = new JsonHandler();
    private final PathInfo pathInfo = new PathInfo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        try {
            currencyValidation.validateCurrencyCodes(pathInfo.getInfoWithoutSlash(req));
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
        }
        String code1 = pathInfo.getFirstCodeOutOfTwo(req);
        String code2 = pathInfo.getSecondCodeOutOfTwo(req);

        try {
            ExchangeRate exchangeRate = exchangeRateDao.findExchangeRate(code1, code2)
                    .orElseThrow(() -> new ExchangeRateException("Обменный курс для пары не найден")); // убрать в метод валидации
            jsonHandler.send(exchangeRate, resp, 200);
        } catch (ExchangeRateException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        } catch (CurrencyException e) {
            jsonHandler.send("Валюта в курсе не найдена", resp, 500);
            return;
        }
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            currencyValidation.validateCurrencyCodes(pathInfo.getInfoWithoutSlash(req));
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
        }
        String code1 = pathInfo.getFirstCodeOutOfTwo(req);
        String code2 = pathInfo.getSecondCodeOutOfTwo(req);
        Double rate = jsonHandler.get(req.getReader(), ExchangeRateDto.class).getRate();

        try {
            if (exchangeRateDao.findExchangeRate(code1, code2).isPresent()) {
                exchangeRateDao.update(code1, code2, rate);
                jsonHandler.send("Успех", resp, 200);
            } else if (exchangeRateDao.findExchangeRate(code2, code1).isPresent()) {
                exchangeRateDao.update(code2, code1, 1 / rate);
                jsonHandler.send("Успех", resp, 200);
            } else {
                throw new ExchangeRateException("Обменный курс для пары не найден");
            }
        } catch (ExchangeRateException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
        } catch (CurrencyException e) {
            jsonHandler.send("Валюта в курсе не найдена", resp, 500);
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH"))
            doPatch(req, resp);
        else
            super.service(req, resp);
    }
}
