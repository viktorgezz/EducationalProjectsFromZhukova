package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.servive.ExchangeRateService;
import ru.viktorgezz.util.AcceptParam;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.util.PathInfo;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;
import ru.viktorgezz.util.exception.ParamException;
import ru.viktorgezz.validate.CurrencyValidation;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
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
            return;
        }

        ExchangeRate exchangeRate;
        try {
            exchangeRate = exchangeRateService.getExchangeRate(
                    pathInfo.getFirstCodeOutOfTwo(req),
                    pathInfo.getSecondCodeOutOfTwo(req));
        } catch (ExchangeRateException e) {
            jsonHandler.sendException(e.getMessage(), resp, 404);
            return;
        } catch (SQLException e) {
            jsonHandler.sendException(e.getMessage(), resp, 500);
            return;
        } catch (CurrencyException e) {
            jsonHandler.sendException("Валюта в курсе не найдена", resp, 500);
            return;
        }

        jsonHandler.send(exchangeRate, resp, 200);
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        BigDecimal rate;
        try {
            rate = new BigDecimal(AcceptParam.getFirstParam(req, "rate"));
        } catch (ParamException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }
        try {
            currencyValidation.validateCurrencyCodes(pathInfo.getInfoWithoutSlash(req));
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
            return;
        }

        try {
            exchangeRateService.updateExchangeRate(
                    pathInfo.getFirstCodeOutOfTwo(req),
                    pathInfo.getSecondCodeOutOfTwo(req),
                    rate);
            jsonHandler.send("Курс валюты обновлён", resp, 200);
        } catch (ExchangeRateException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
            return;
        } catch (CurrencyException e) {
            jsonHandler.send("Валюта в курсе не найдена", resp, 500);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
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
