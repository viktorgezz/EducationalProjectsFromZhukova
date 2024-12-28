package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface SearchNode {

    <T extends Entity> Optional<Queue<Node>> findShortestPathToTarget(Class<T> targetClazz, Node currNode);

    /**
     * Возвращает список соседних соединённых узлов без сущностей (свободных, в которых можно перейти).
     * @param currNode Текущий узел.
     * @return Список соединённых узлов.
     */
    List<Node> getConnectNodesWithNullEntity(Node currNode);

    /**
     * Возвращает список всех соседних соединённых узлов.
     * @param currNode Текущий узел.
     * @return Список соединённых узлов.
     */
    List<Node> getConnectNodesWithEntity(Node currNode);
}
