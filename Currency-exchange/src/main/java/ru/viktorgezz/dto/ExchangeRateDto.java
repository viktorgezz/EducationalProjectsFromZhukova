package ru.viktorgezz.dto;

import ru.viktorgezz.model.Currency;

public class ExchangeRateDto {

    private Currency baseCurrency;

    private Currency targetCurrency;

    private Double rate;

    public ExchangeRateDto() {
    }

    public ExchangeRateDto(Builder builder) {
        this.baseCurrency = builder.baseCurrency;
        this.targetCurrency = builder.targetCurrency;
        this.rate = builder.rate;
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

    public static class Builder {
        private Currency baseCurrency;
        private Currency targetCurrency;
        private Double rate;

        public Builder setBaseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder setTargetCurrency(Currency targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public Builder setRate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRateDto build() {
            if (baseCurrency == null || targetCurrency == null || rate == null) {
                throw new IllegalArgumentException("baseCurrency, targetCurrency and rate должны быть заданы");
            }
            return new ExchangeRateDto(this);
        }
    }
}
