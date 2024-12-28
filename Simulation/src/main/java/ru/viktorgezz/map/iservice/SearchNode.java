package ru.viktorgezz.map.iservice;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface SearchNode {

    <T extends Entity> Optional<Queue<Node>> findShortestPathToTarget(Class<T> targetClazz, Node currNode);

    List<Node> getConnectNodesWithNullEntity(Node currNode);

    List<Node> getConnectNodesWithEntity(Node currNode);
}
