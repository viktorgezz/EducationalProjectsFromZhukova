package ru.viktorgezz.dto;

public class CurrencyDto {

    private String name;

    private String code;

    private String sign;

    public CurrencyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
