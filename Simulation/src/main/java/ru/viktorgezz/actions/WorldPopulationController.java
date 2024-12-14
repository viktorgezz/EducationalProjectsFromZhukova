package ru.viktorgezz.actions;

import ru.viktorgezz.map.Node;

public class WorldPopulationController extends CreationEntities {


    public WorldPopulationController(int vertical, int horizontal, Node root) {
        super(vertical, horizontal, root);
    }

    public boolean isHerbivoreGenerationRequired() {
        return entityInstaller.getCountHerbivore() < NUM_HERBIVORE;
    }

    public boolean isGrassGenerationRequired(int countTurn) {
        return countTurn % 20 == 0;
    }

    public boolean isPredatorGenerationRequired() {
        return entityInstaller.getCountPredator() < NUM_PREDATOR / 2;
    }
}
