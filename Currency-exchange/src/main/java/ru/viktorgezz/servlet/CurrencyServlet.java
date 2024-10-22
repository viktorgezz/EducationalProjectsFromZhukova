package ru.viktorgezz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import ru.viktorgezz.model.Currency;
import ru.viktorgezz.util.JsonHandler;
import ru.viktorgezz.util.PathInfo;
import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.validate.CurrencyValidation;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyDao currencyDAO = new CurrencyDao();
    private final JsonHandler jsonHandler = new JsonHandler();
    private final PathInfo pathInfo = new PathInfo();
    private final CurrencyValidation currencyValidation = new CurrencyValidation();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        String code = pathInfo.getInfoWithoutSlash(req);
        try {
            currencyValidation.validateCurrencyCode(code);
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 400);
            return;
        }

        try {
            Currency currency = currencyDAO.findCurrencyByCode(code)
                    .orElseThrow(() -> new CurrencyException("Валюта не найдена"));
            jsonHandler.send(currency, resp);
        } catch (CurrencyException e) {
            jsonHandler.send(e.getMessage(), resp, 404);
            return;
        } catch (SQLException e) {
            jsonHandler.send(e.getMessage(), resp, 500);
            return;
        }
    }
}
