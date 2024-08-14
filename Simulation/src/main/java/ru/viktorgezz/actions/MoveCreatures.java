package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.map.MapWorld;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MoveCreatures extends Action {

    public MoveCreatures(MapWorld mapWorld) {
        super(mapWorld);
    }

    @Override
    public void perform() {
        List<Creature> creatures = new CopyOnWriteArrayList<>(mapWorld.getCreatures());
        for (Creature creature : creatures) {
            creature.playAction();
        }
    }

}
