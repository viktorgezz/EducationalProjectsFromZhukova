package ru.viktorgezz.dto;

public class CurrencyDto {

    private String name;

    private String code;

    private String sign;

    public CurrencyDto() {
    }

    public CurrencyDto(Builder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.sign = builder.sign;
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

    public static class Builder {
        private String name;
        private String code;
        private String sign;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setSign(String sign) {
            this.sign = sign;
            return this;
        }

        public CurrencyDto build() {
            if (name == null || code == null || sign == null) {
                throw new IllegalArgumentException("name, code, sign должны быть заданы");
            }
            return new CurrencyDto(this);
        }
    }
}
