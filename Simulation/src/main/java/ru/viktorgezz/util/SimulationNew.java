package ru.viktorgezz.util;

import ru.viktorgezz.actions.Action;
import ru.viktorgezz.actions.CreationEntities;
import ru.viktorgezz.actions.WorldPopulationController;
import ru.viktorgezz.map.MapFactory;
import ru.viktorgezz.map.MapRender;

import java.util.LinkedList;
import java.util.List;

public class SimulationNew {

    private final WorldPopulationController worldPopulationController;

    private final List<Action> initActions = new LinkedList<>();

    public SimulationNew(int horizontalSize, int verticalSize) {
        MapFactory map = new MapFactory(horizontalSize, verticalSize);
        MapRender mapRender = new MapRender(map.getRoot());

        worldPopulationController = new WorldPopulationController(map.getSize().vertical, map.getSize().horizontal, map.getRoot());
        new CreationEntities(map.getSize().vertical, map.getSize().horizontal, map.getRoot()).perform();
    }

    // сделать меню


}
