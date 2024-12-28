package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Grass;

import java.util.concurrent.CopyOnWriteArrayList;

public interface CreatureProvider {

    /**
     * Возвращает список всех существ на карте.
     * @return Список существ.
     */
    CopyOnWriteArrayList<Creature> getCreatures();

    /**
     * Возвращает количество травоядных на карте.
     * @return Количество травоядных.
     */
    Integer getCountHerbivore();

    /**
     * Возвращает количество хищников на карте.
     * @return Количество хищников.
     */
    Integer getCountPredator();

    /**
     * Удаляет траву (Grass) с узла карты.
     * @param grass Удаляемый объект травы.
     */
    void removeGrass(Grass grass);

    /**
     * Удаляет существо с карты и из хранилища.
     * @param creature Удаляемое существо.
     */
    void removeCreature(Creature creature);
}
