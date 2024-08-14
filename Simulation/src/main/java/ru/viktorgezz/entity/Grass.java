package ru.viktorgezz.entity;

public class Grass extends Entity {

    private Integer saturation;

    public Grass() {
        this.saturation = 2;
    }

    public Integer getSaturation() {
        return saturation;
    }

    public void minusSaturation() {
        this.saturation -= 1;
    }
}
