package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
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

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
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
            Optional<ExchangeRate> exchangeRateOpt = exchangeRateDao.findExchangeRateSafely(code1, code2);
            if (exchangeRateOpt.isEmpty()) {
                exchangeRateOpt = exchangeRateDao.findExchangeRate(code2, code1);
                exchangeRateOpt.orElseThrow().setRate(new BigDecimal(1).divide(exchangeRateOpt.get().getRate(), 3, RoundingMode.HALF_UP));
            }
            ExchangeRate exchangeRate = exchangeRateOpt
                    .orElseThrow(() -> new ExchangeRateException("Обменный курс для пары не найден")); // убрать в метод валидации
            jsonHandler.send(exchangeRate, resp, 200);

        } catch (ExchangeRateException e) {
            jsonHandler.sendException(e.getMessage(), resp, 404);
        } catch (SQLException e) {
            jsonHandler.sendException(e.getMessage(), resp, 500);
        } catch (CurrencyException e) {
            jsonHandler.sendException("Валюта в курсе не найдена", resp, 500);
        }
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        double rate;
        try {
            rate = Double.parseDouble(AcceptParam.getFirstParam(req, "rate"));
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
        String code1 = pathInfo.getFirstCodeOutOfTwo(req);
        String code2 = pathInfo.getSecondCodeOutOfTwo(req);

        try {
            if (exchangeRateDao.findExchangeRate(code1, code2).isPresent()) {
                exchangeRateDao.update(code1, code2, rate);
                jsonHandler.send("Успех", resp, 200);
            } else if (exchangeRateDao.findExchangeRate(code2, code1).isPresent()) {
                exchangeRateDao.update(code2, code1, 1 / rate);
                jsonHandler.send("Успех", resp, 200);
                return;
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
