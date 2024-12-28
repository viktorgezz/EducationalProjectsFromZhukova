package ru.viktorgezz.action.creation;

import ru.viktorgezz.entity.Grass;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.Node;

public class WorldPopulationController extends ACreation{

    private final CreationEntities creationEntities = CreationEntities.getInstance();

    public void isGenerationCreatureRequired(Node root) {
        int randomValue = (int) (Math.random() * 2);
// randomValue == 0 &&
        if (creatureProvider.getCountHerbivore() < entityCounter.getCountHerbivore()) {
            creationEntities.create(new Herbivore(), root);
        }
// randomValue == 1 &&
        if (creatureProvider.getCountPredator() < entityCounter.getCountPredator()) {
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
