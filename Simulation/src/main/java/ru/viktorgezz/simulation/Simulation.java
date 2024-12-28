package ru.viktorgezz.simulation;

import ru.viktorgezz.action.creation.InitEntities;
import ru.viktorgezz.map.MapFactory;
import ru.viktorgezz.menu.MenuService;
import ru.viktorgezz.menu.MenuStorage;

public class Simulation {

    private MapFactory mapFactory;

    public Simulation() {
        initMap();
        initCreation();
    }

    public void run() {
        initMenu();
    }

    private void initMenu() {
        new MenuService().displayAndSelectAction(MenuStorage.getMenu(), mapFactory.getRoot());
    }

    private void initMap() {
        mapFactory = new MapFactory(20, 10);
    }

    private void initCreation() {
        new InitEntities().perform(mapFactory.getRoot());
    }


}
