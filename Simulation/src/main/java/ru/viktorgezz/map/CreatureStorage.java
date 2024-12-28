package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;

import java.util.concurrent.CopyOnWriteArrayList;

public class CreatureStorage {
    private final CopyOnWriteArrayList<Creature> creatures = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<Creature> get() {
        return creatures;
    }

    public void add(Creature creature) {
        creatures.add(creature);
    }

    public void remove(Creature creature) {
        creatures.remove(creature);
    }
}
