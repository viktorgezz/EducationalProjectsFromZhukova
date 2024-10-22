package ru.viktorgezz.dto;

import ru.viktorgezz.model.Currency;

public class ExchangeRateDto {

    private Currency baseCurrency;

    private Currency targetCurrency;

    private Double rate;

    public ExchangeRateDto() {
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", rate=" + rate +
                '}';
    }
}
