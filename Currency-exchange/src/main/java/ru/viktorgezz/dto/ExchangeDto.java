package ru.viktorgezz.dto;

import ru.viktorgezz.model.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeDto {

    private ExchangeRate exchangeRate;

    private BigDecimal amount;

    private BigDecimal convertedAmount;

    public ExchangeDto() {
    }

    public ExchangeDto(Builder builder) {
        this.exchangeRate = builder.exchangeRate;
        this.amount = builder.amount;
        this.convertedAmount = builder.convertedAmount;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return "ExchangeDTO{" +
                "exchangeRate=" + exchangeRate +
                ", amount=" + amount +
                ", convertedAmount=" + convertedAmount +
                '}';
    }

    public static class Builder {
        private ExchangeRate exchangeRate;
        private BigDecimal amount;
        private BigDecimal convertedAmount;

        public Builder setExchangeRate(ExchangeRate exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setConvertedAmount(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public ExchangeDto build() {
            if (exchangeRate == null || amount == null || convertedAmount == null) {
                throw new IllegalArgumentException("ExchangeRate, amount and convertedAmount должны быть заданы");
            }
            return new ExchangeDto(this);
        }
    }
}
