package ru.viktorgezz.dto;

import ru.viktorgezz.model.ExchangeRate;

public class ExchangeDto {

    private ExchangeRate exchangeRate;

    private Double amount;

    private Double convertedAmount;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
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
        private Double amount;
        private Double convertedAmount;

        public Builder setExchangeRate(ExchangeRate exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setConvertedAmount(Double convertedAmount) {
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
