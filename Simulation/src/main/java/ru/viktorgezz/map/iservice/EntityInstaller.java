package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;


public interface EntityInstaller {

    /**
     * Размещает сущность в доступном пустом узле карты.
     * @param entity Размещаемая сущность.
     * @param root   Корневой узел карты.
     */
    void installEntityInNode(Entity entity, Node root);
}
