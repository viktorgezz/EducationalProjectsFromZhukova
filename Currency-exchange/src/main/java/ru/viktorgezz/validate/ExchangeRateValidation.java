package ru.viktorgezz.validate;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dao.ExchangeRateDao;
import ru.viktorgezz.dto.ExchangeRateDto;
import ru.viktorgezz.model.Currency;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.ExchangeRateException;
import ru.viktorgezz.util.exception.RequestReaderException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ExchangeRateValidation {

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    public void validateExchangeRateDto(ExchangeRateDto exchangeRateDto) throws RequestReaderException, SQLException, CurrencyException, ExchangeRateException {
        validateEmptyFields(exchangeRateDto.getBaseCurrency(), exchangeRateDto.getTargetCurrency(), exchangeRateDto.getRate());
        validateCurrencyExists(exchangeRateDto.getBaseCurrency().getCode(), exchangeRateDto.getTargetCurrency().getCode());
        validateCurrencyPairExists(exchangeRateDto.getBaseCurrency().getCode(), exchangeRateDto.getTargetCurrency().getCode());
    }

    private void validateEmptyFields(Currency base, Currency target, BigDecimal rate) throws RequestReaderException {
        StringBuilder errors = new StringBuilder();

        if (base == null) {
            errors.append("Отсутствует нужное поле формы baseCurrency\n");
        }
        if (target == null) {
            errors.append("Отсутствует нужное поле формы targetCurrency\n");
        }
        if (rate == null) {
            errors.append("Отсутствует нужное поле формы rate\n");
        }

        if (!errors.toString().isEmpty()) {
            throw new RequestReaderException(errors.toString());
        }
    }

    private void validateCurrencyExists(String codeBase, String codeTarget) throws SQLException, CurrencyException {
        StringBuilder errors = new StringBuilder();
        if (currencyDao.findCurrencyByCode(codeBase).isEmpty()) {
            errors.append("Валюта base отсутствует\n");
        }
        if (currencyDao.findCurrencyByCode(codeTarget).isEmpty()) {
            errors.append("Валюта target отсутствует");
        }

        if (!errors.toString().isEmpty()) {
            throw new CurrencyException(errors.toString());
        }
    }

    private void validateCurrencyPairExists(String codeBase, String codeTarget) throws SQLException, ExchangeRateException {
        if (exchangeRateDao.findExchangeRateSafely(codeBase, codeTarget).isPresent() ||
                exchangeRateDao.findExchangeRateSafely(codeBase, codeTarget).isPresent()) {
            throw new ExchangeRateException("Валютная пара уже существует");
        }
    }

}
