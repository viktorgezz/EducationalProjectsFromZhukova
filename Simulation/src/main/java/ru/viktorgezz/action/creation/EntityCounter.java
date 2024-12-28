package ru.viktorgezz.action.creation;

import ru.viktorgezz.map.Size;

public class EntityCounter {

    public EntityCounter() {
        initNumEntities(Size.vertical * Size.horizontal);
    }

    private int countHerbivore;

    private int countPredator;

    private int countTree;

    private int countRock;

    private int countGrass;

    private void initNumEntities(int square) {
        countHerbivore = (int) Math.ceil(square * 0.04);
        countPredator = (int) Math.ceil(square * 0.08);
        countTree = (int) Math.ceil(square * 0.04);
        countRock = (int) Math.ceil(square * 0.04);
        countGrass = (int) Math.ceil(square * 0.08);
    }

    public int getCountHerbivore() {
        return countHerbivore;
    }

    public int getCountPredator() {
        return countPredator;
    }

    public int getCountTree() {
        return countTree;
    }

    public int getCountRock() {
        return countRock;
    }

    public int getCountGrass() {
        return countGrass;
    }
}
