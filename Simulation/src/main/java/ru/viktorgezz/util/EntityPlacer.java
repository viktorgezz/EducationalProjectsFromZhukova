package ru.viktorgezz.util;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

import java.util.Optional;

public class EntityPlacer {

    public void installEntityInNode(Entity entity) {
        Optional<Node> emptyNode = searchNode.findEmptyNode(map.getFirstNode());
        if (emptyNode.isPresent()) {
            Node node = emptyNode.get();
            addEntity(entity, node);
        }
    }
}
