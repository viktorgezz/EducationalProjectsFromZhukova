package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.MapWorld;

public class CreationPredator extends CreationEntities{

    public CreationPredator(MapWorld mapWorld) {
        super(mapWorld);
    }

    public void perform() {
        installEntityInNode(new Predator(mapWorld));
    }
}
