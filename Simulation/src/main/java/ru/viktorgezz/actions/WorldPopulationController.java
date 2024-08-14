package ru.viktorgezz.actions;

import ru.viktorgezz.map.MapWorld;

public class WorldPopulationController extends CreationEntities {

    public WorldPopulationController(MapWorld mapWorld) {
        super(mapWorld);
    }

    public boolean isHerbivoreGenerationRequired() {
        return mapWorld.getCountHerbivore() < NUM_HERBIVORE;
    }

    public boolean isGrassGenerationRequired(int countTurn) {
        return countTurn % 20 == 0;
    }

    public boolean isPredatorGenerationRequired() {
        return mapWorld.getCountPredator() < NUM_PREDATOR / 2;
    }
}
