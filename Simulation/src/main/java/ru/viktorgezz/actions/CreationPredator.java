package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.Node;

public class CreationPredator extends CreationEntities{

    public CreationPredator(int vertical, int horizontal, Node root) {
        super(vertical, horizontal, root);
    }

    public void perform() {
        entityInstaller.installEntityInNode(new Predator(), root);
    }
}
