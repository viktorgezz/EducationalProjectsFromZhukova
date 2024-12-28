package ru.viktorgezz.action;

import ru.viktorgezz.map.MapService;
import ru.viktorgezz.map.iservice.CreatureProvider;
import ru.viktorgezz.map.iservice.EntityInstaller;
import ru.viktorgezz.map.iservice.SearchNode;
import ru.viktorgezz.util.SharedResources;

/**
 * Абстрактный базовый класс для всех действий в игровом мире.
 * Предоставляет общие зависимости, такие как сервис карты (MapService),
 * провайдер существ (CreatureProvider) и установщик сущностей (EntityInstaller).
 */
public abstract class Action {

    private final MapService mapService = new MapService(SharedResources.CREATURE_STORAGE);

    protected SearchNode searchNode = mapService;

    protected CreatureProvider creatureProvider = mapService;

    protected EntityInstaller entityInstaller = mapService;
}
