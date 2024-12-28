package ru.viktorgezz.action;

import ru.viktorgezz.map.MapService;
import ru.viktorgezz.map.iservice.CreatureProvider;
import ru.viktorgezz.map.iservice.EntityInstaller;
import ru.viktorgezz.map.iservice.SearchNode;
import ru.viktorgezz.util.SharedResources;

public abstract class Action {

    private final MapService mapService = new MapService(SharedResources.CREATURE_STORAGE);

    protected SearchNode searchNode = mapService;

    protected CreatureProvider creatureProvider = mapService;

    protected EntityInstaller entityInstaller = mapService;
}
