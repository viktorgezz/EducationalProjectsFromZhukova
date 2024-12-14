package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MoveCreatures extends CreationEntities {

    public MoveCreatures(int vertical, int horizontal, Node root) {
        super(vertical, horizontal, root);
    }

    @Override
    public void perform() {
        List<Creature> creatures = new CopyOnWriteArrayList<>(entityInstaller.getCreatures());
        for (Creature creature : creatures) {
            creature.playAction();
        }
    }
}
