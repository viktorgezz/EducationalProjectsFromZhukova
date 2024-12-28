package ru.viktorgezz.action.creation;


import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

/**
 *  Класс предоставляет функционал для создания сущностей в игровом мире.
 *  Он использует паттерн Singleton для обеспечения единственного экземпляра,
 *  и позволяет устанавливать сущности в заданный узел карты.
 */
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
