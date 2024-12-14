package ru.viktorgezz.actions;

import ru.viktorgezz.entity.Grass;
import ru.viktorgezz.map.Node;

public class CreationGrass extends CreationEntities {

    public CreationGrass(int vertical, int horizontal, Node root) {
        super(vertical, horizontal, root);
    }

    public void perform() {
        for (int i = 0; i < NUM_GRASS; i++) {
            entityInstaller.installEntityInNode(new Grass(), root);
        }
    }

}
