package ru.viktorgezz.action.creation;

import ru.viktorgezz.entity.Grass;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.Node;


/**
 * Класс отвечает за управление популяцией объектов в игровом мире.
 * Он проверяет необходимость генерации новых существ (травоядных, хищников)
 * или травы в заданном узле карты на основе текущего количества сущностей
 * и случайного значения.
 */
public class WorldPopulationController extends ACreation{

    private final CreationEntities creationEntities = CreationEntities.getInstance();

    public void isGenerationCreatureRequired(Node root) {
        int randomValue = (int) (Math.random() * 2);

        if (randomValue == 0 && creatureProvider.getCountHerbivore() < entityCounter.getCountHerbivore()) {
            creationEntities.create(new Herbivore(), root);
        }
        if (randomValue == 1 && creatureProvider.getCountPredator() < entityCounter.getCountPredator()) {
            creationEntities.create(new Predator(), root);
        }
    }

    public void isGenerationGrassRequired(Node root) {
        int randomValue = (int) (Math.random() * 100);

        if (randomValue >= 0 && randomValue <= 5) {
            for (int i = 0; i < 6; i++) {
                creationEntities.create(new Grass(), root);
            }
        }
    }
}
