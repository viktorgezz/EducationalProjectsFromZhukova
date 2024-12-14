package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.map.Node;

public class CreationHerbivore extends CreationEntities{


    public CreationHerbivore(int vertical, int horizontal, Node root) {
        super(vertical, horizontal, root);
    }

    public void perform() {
        entityInstaller.installEntityInNode(new Herbivore(), root);
    }
}
