package ru.viktorgezz.entity;

/**
 * Абстрактный класс Creature представляет основу существ (травоядных, хищников).
 * Он расширяет класс Entity, добавляя характеристики существа.
 */
public abstract class Creature extends Entity {

    private Integer step;
    private Integer hp;
    private Integer age;

    public Creature() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}
