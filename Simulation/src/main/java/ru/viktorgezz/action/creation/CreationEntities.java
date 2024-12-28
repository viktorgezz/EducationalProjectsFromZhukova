package ru.viktorgezz.action.creation;


import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

public class CreationEntities extends ACreation {

    private static CreationEntities instance = new CreationEntities();

    private CreationEntities() {
    }

    public static CreationEntities getInstance() {
        if (instance == null) {
            instance = new CreationEntities();
        }
        return instance;
    }

    public void create(Entity entity, Node root) {
        entityInstaller.installEntityInNode(entity, root);
    }
}
