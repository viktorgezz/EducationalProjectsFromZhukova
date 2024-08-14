package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Grass;
import ru.viktorgezz.map.MapWorld;
import ru.viktorgezz.map.Size;

public class CreationGrass extends CreationEntities {

    public CreationGrass(MapWorld mapWorld) {
        super(mapWorld);
    }

    public void perform() {
        for (int i = 0; i < NUM_GRASS; i++) {
            installEntityInNode(new Grass());
        }
    }

}
