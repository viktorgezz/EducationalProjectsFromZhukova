package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.map.MapWorld;

public class CreationHerbivore extends CreationEntities{

    public CreationHerbivore(MapWorld mapWorld) {
        super(mapWorld);
    }

    public void perform() {
        installEntityInNode(new Herbivore(mapWorld));
    }
}
