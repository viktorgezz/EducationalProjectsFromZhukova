package ru.viktorgezz.model;

public class ExchangeRate {

    private int id;

    private Currency baseCurrency;

    private Currency TargetCurrency;

    private Double rate;

    public ExchangeRate() {
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
        return TargetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        TargetCurrency = targetCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency, Double rate) {
        this.baseCurrency = baseCurrency;
        TargetCurrency = targetCurrency;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", baseCurrency=" + baseCurrency +
                ", TargetCurrency=" + TargetCurrency +
                ", rate=" + rate +
                '}';
    }
}
