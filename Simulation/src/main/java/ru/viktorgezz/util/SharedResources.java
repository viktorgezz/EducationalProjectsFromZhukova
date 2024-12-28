package ru.viktorgezz.util;

import ru.viktorgezz.map.CreatureStorage;

/**
 * Класс предоставляет единый экземпляр хранилища, доступный из разных частей программы.
 */
public class SharedResources {

    /**
     * Статическое поле, представляющее общее хранилище существ (CreatureStorage).
     * Это позволяет всем компонентам программы работать с единым экземпляром хранилища.
     */
    public static final CreatureStorage CREATURE_STORAGE = new CreatureStorage();
}
