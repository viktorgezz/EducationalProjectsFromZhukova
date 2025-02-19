package ru.viktorgezz.validate;

import ru.viktorgezz.dao.CurrencyDao;
import ru.viktorgezz.dto.CurrencyDto;
import ru.viktorgezz.util.exception.CurrencyException;
import ru.viktorgezz.util.exception.RequestReaderException;

import java.sql.SQLException;

public class CurrencyValidation {

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    public void validateCurrencies(CurrencyDto currencyDto) throws RequestReaderException, CurrencyException, SQLException {
        validateEmptyFields(currencyDto.getName(), currencyDto.getSign(), currencyDto.getCode());
        validateCorrectFields(currencyDto.getName(), currencyDto.getSign(), currencyDto.getCode());
        validateExistsCurrency(currencyDto.getCode().toUpperCase());
    }

    public void validateCurrencyCode(String code) throws CurrencyException {
        if (code.isEmpty()) {
            throw new CurrencyException("Код валюты отсутствует в адресе ");
        }
    }

    public void validateCurrencyCodes(String codes) throws CurrencyException {
        if (codes.length() != 6) {
            throw new CurrencyException("Код валюты отсутствует в адресе или имеет неправильную запись ");
        }
    }

    private void validateEmptyFields(String name, String sign, String code) throws RequestReaderException {
        StringBuilder errors = new StringBuilder();

        if (name == null || name.isEmpty()) {
            errors.append("Отсутствует нужное поле формы name ");
        }
        if (sign == null || sign.isEmpty()) {
            errors.append("Отсутствует нужное поле формы sign ");
        }
        if (code == null || code.isEmpty()) {
            errors.append("Отсутствует нужное поле формы code ");
        }

        if (!errors.toString().isEmpty()) {
            throw new RequestReaderException(errors.toString());
        }
    }

    private void validateCorrectFields(String name, String sign, String code) throws CurrencyException {
        StringBuilder errors = new StringBuilder();

        if (name.length() > 100) {
            errors.append("Большая длина поле name ");
        }
        if (code.length() != 3) {
            errors.append("Длина code не равна трем ");
        }
        if (code.matches(".*[a-z].*")) {
            errors.append("code содержит буквы в нижнем регистре ");
        }
        if (sign.length() > 3) {
            errors.append("Большая длина поле sign ");
        }

        if (!errors.toString().isEmpty()) {
            throw new CurrencyException(errors.toString());
        }
    }

    private void validateExistsCurrency(String code) throws CurrencyException, SQLException {
        if (currencyDao.findCurrencyDtoByCode(code).isPresent()) {
            throw new CurrencyException("Валюта с таким кодом уже существует ");
        }
    }
}
