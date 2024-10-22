package ru.viktorgezz.util;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.model.ExchangeRate;
import ru.viktorgezz.util.exception.CurrencyException;

import java.sql.SQLException;
import java.util.Optional;

public class SearchCurrencyPair {

    private final ExchangeRateDao exchangeRateDAO = new ExchangeRateDao();
    private final CurrencyDao currencyDAO = new CurrencyDao();

    public Optional<ExchangeRate> perform(String formCode, String toCode) throws SQLException, CurrencyException {
        Optional<ExchangeRate> exchangeRateOpt = exchangeRateDAO.findExchangeRate(formCode, toCode);

        if (exchangeRateOpt.isEmpty())
            exchangeRateOpt = exchangeRateDAO.findExchangeRate(toCode, formCode);

        if (exchangeRateOpt.isEmpty()) {
            Optional<ExchangeRate> usdFrom = exchangeRateDAO.findExchangeRate("USD", formCode);
            Optional<ExchangeRate> usdTo = exchangeRateDAO.findExchangeRate("USD", toCode);

            if (usdFrom.isPresent() && usdTo.isPresent()) {
                ExchangeRate exchangeRateTemp = new ExchangeRate(
                        currencyDAO.findCurrencyByCode(formCode).get(),
                        currencyDAO.findCurrencyByCode(toCode).get(),
                        usdFrom.get().getRate() / usdTo.get().getRate()
                );
                exchangeRateOpt = Optional.of(exchangeRateTemp);
            }
        }

        return exchangeRateOpt;
    }

    public double calculateConvertedAmount(ExchangeRate exchangeRate, double amount) {
        return exchangeRate.getRate() * amount;
    }
}
