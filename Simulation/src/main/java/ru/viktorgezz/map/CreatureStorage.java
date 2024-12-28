package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс CreatureStorage представляет собой потокобезопасное хранилище для сущностей типа Creature.
 * Используется для управления коллекцией существ, поддерживая операции добавления, удаления и получения.
 */
public class CreatureStorage {
    /**
     * Коллекция существ, хранящая объекты Creature.
     * CopyOnWriteArrayList обеспечивает потокобезопасность при изменении коллекции.
     */
    private final CopyOnWriteArrayList<Creature> creatures = new CopyOnWriteArrayList<>();

    /**
     * Возвращает текущую коллекцию существ.
     * @return потокобезопасный список существ.
     */
    public CopyOnWriteArrayList<Creature> get() {
        return creatures;
    }

    /**
     * Добавляет существо в коллекцию.
     * @param creature существо, которое нужно добавить.
     */
    public void add(Creature creature) {
        creatures.add(creature);
    }

    /**
     * Удаляет существо из коллекции.
     * @param creature существо, которое нужно удалить.
     */
    public void remove(Creature creature) {
        creatures.remove(creature);
    }
}
