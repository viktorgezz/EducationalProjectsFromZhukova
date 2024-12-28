package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;


public interface EntityInstaller {

    void installEntityInNode(Entity entity, Node root);
}
