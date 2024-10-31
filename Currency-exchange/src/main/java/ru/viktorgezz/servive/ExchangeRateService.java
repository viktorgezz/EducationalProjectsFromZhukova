package ru.viktorgezz.servive;

import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Optional;

public class ExchangeRateService {

    private ExchangeRateService() {

    }

    private static ExchangeRateService instance;

    public static ExchangeRateService getInstance() {
        if (instance == null) {
            instance = new ExchangeRateService();
        }
        return instance;
    }

    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    public void updateExchangeRate(String code1, String code2, BigDecimal rate) throws SQLException, CurrencyException, ExchangeRateException {
        if (exchangeRateDao.findExchangeRate(code1, code2).isPresent()) {
            exchangeRateDao.update(code1, code2, rate);
        } else if (exchangeRateDao.findExchangeRate(code2, code1).isPresent()) {
            exchangeRateDao.update(code2, code1, new BigDecimal(1).divide(rate));
        } else {
            throw new ExchangeRateException("Обменный курс для пары не найден");
        }
    }

    public ExchangeRate getExchangeRate(String code1, String code2) throws SQLException, CurrencyException, ExchangeRateException {
        Optional<ExchangeRate> exchangeRateOpt = exchangeRateDao.findExchangeRateSafely(code1, code2);

        if (exchangeRateOpt.isEmpty()) {
            exchangeRateOpt = exchangeRateDao.findExchangeRate(code2, code1);
            exchangeRateOpt.orElseThrow(() -> new ExchangeRateException("Обменный курс для пары не найден"))
                    .setRate(new BigDecimal(1)
                            .divide(
                                    exchangeRateOpt.get().getRate(), 3, RoundingMode.HALF_UP));
        }

        return exchangeRateOpt
                .orElseThrow(() -> new ExchangeRateException("Обменный курс для пары не найден"));
    }
}
