package ru.viktorgezz.util;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Optional;

public class SearchCurrencyPair {

    private final ExchangeRateDao exchangeRateDAO = ExchangeRateDao.getInstance();
    private final CurrencyDao currencyDAO = CurrencyDao.getInstance();

    public Optional<ExchangeRate> perform(String formCode, String toCode) throws SQLException, CurrencyException {
        Optional<ExchangeRate> exchangeRateOpt = exchangeRateDAO.findExchangeRate(formCode, toCode);

        if (exchangeRateOpt.isEmpty()) {
            exchangeRateOpt = exchangeRateDAO.findExchangeRate(toCode, formCode);
            if (exchangeRateOpt.isPresent()) {
                exchangeRateOpt.orElseThrow().setRate(new BigDecimal(1).divide(exchangeRateOpt.get().getRate(), 3, RoundingMode.HALF_UP));
            }
        }
        if (exchangeRateOpt.isEmpty()) {
            Optional<ExchangeRate> usdFrom = exchangeRateDAO.findExchangeRate("USD", formCode);
            Optional<ExchangeRate> usdTo = exchangeRateDAO.findExchangeRate("USD", toCode);

            if (usdFrom.isPresent() && usdTo.isPresent()) {
                ExchangeRate exchangeRateTemp = new ExchangeRate.Builder().setId(-1)
                        .setBaseCurrency(currencyDAO.findCurrencyByCode(formCode).orElseThrow())
                        .setTargetCurrency(currencyDAO.findCurrencyByCode(toCode).orElseThrow())
                        .setRate(usdFrom.get().getRate().divide( usdTo.get().getRate(), 3, RoundingMode.HALF_UP))
                        .build();
                ;
                exchangeRateOpt = Optional.of(exchangeRateTemp);
            }
        }

        return exchangeRateOpt;
    }

    public BigDecimal calculateConvertedAmount(ExchangeRate exchangeRate, BigDecimal amount) {
        return exchangeRate.getRate().multiply(amount).setScale(3, RoundingMode.HALF_UP);
    }
}
