package ru.viktorgezz.entity;

public class Herbivore extends Creature {

    public Herbivore() {
        setHp(3);
        setStep(2);
        setAge(1);
    }

    public void minusHp(int damage) {
        this.setHp(getHp() - damage);
    }

}
