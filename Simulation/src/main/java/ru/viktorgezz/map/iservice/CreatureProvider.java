package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;

import java.util.concurrent.CopyOnWriteArrayList;

public interface CreatureProvider {

    CopyOnWriteArrayList<Creature> getCreatures();

    Integer getCountHerbivore();

    Integer getCountPredator();

    void removeImmobileEntity(Entity entity);

    void removeCreature(Creature creature);
}
