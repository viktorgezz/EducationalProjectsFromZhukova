package ru.viktorgezz.model;

import java.math.BigDecimal;

public class ExchangeRate {

    private int id;

    private Currency baseCurrency;

    private Currency targetCurrency;

    private BigDecimal rate;

    public ExchangeRate() {
    }

    public ExchangeRate(Builder builder) {
        this.id = builder.id;
        this.baseCurrency = builder.baseCurrency;
        this.targetCurrency = builder.targetCurrency;
        this.rate = builder.rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", rate=" + rate +
                '}';
    }

    public static class Builder {
        private int id;
        private Currency baseCurrency;
        private Currency targetCurrency;
        private BigDecimal rate;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBaseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder setTargetCurrency(Currency targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public Builder setRate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRate build() {
            if (id == 0) {
                throw new RuntimeException("Ошибка build");
            }
            return new ExchangeRate(this);
        }
    }
}
