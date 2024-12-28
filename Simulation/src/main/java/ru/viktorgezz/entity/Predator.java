package ru.viktorgezz.entity;

public class Predator extends Creature {

    private Integer countMurders = 0;
    private Integer damage = 1;
    private int numKillBeforeUpgrade = 2;

    public Predator() {
        setHp(2);
        setStep(1);
        setAge(0);
    }

    public Integer getCountMurders() {
        return countMurders;
    }

    public void setCountMurders(Integer countMurders) {
        this.countMurders = countMurders;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getDamage() {
        return damage;
    }

    public int getNumKillBeforeUpgrade() {
        return numKillBeforeUpgrade;
    }

    public void setNumKillBeforeUpgrade(int numKillBeforeUpgrade) {
        this.numKillBeforeUpgrade = numKillBeforeUpgrade;
    }
}
