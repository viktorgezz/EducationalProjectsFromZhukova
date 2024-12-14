package ru.viktorgezz.util;

import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Coordinates;
import ru.viktorgezz.map.Node;
import ru.viktorgezz.map.GraphIterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchNode {

    public <T extends Entity> Optional<Queue<Node>> findShortestPathToTarget(Class<T> targetClazz, Node currNode) {
        Node targetNode = searchTarget(targetClazz, currNode);

        Queue<Node> closeList = new LinkedList<>();
        Set<Node> openList = new HashSet<>();

        double distanceStartMin = Double.MAX_VALUE;

        while (!(currNode.getUp() == targetNode || currNode.getDown() == targetNode
                || currNode.getRight() == targetNode || currNode.getLeft() == targetNode)) {

            List<Node> linkNodes = getConnectNodesWithNullEntity(currNode);
            if (linkNodes.isEmpty()) {
                return Optional.empty();
            }

            for (Node node : linkNodes) {
                double distanceX = Math.pow(targetNode.getCoordinates().getX() - node.getCoordinates().getX(), 2);
                double distanceY = Math.pow(targetNode.getCoordinates().getY() - node.getCoordinates().getY(), 2);

                double distanceTarget = (Math.sqrt(distanceX + distanceY) * 1000);

                if (distanceTarget < distanceStartMin) {
                    distanceStartMin = distanceTarget;
                    currNode = node;
                }

                if (openList.contains(node)) {
                    return Optional.of(closeList);
                }

                openList.add(node);
            }
            closeList.add(currNode);
        }
        return Optional.of(closeList);
    }

    private <T extends Entity> Node searchTarget(Class<T> clazz, Node currNode) {
        GraphIterator graphIterator = new GraphIterator(currNode);

        while (graphIterator.hasNext()) {
            currNode = graphIterator.next();
            if (currNode.getEntity() != null && currNode.getEntity().getClass() == clazz)
                return currNode;

        }
        return currNode;
    }

    public List<Node> getConnectNodesWithNullEntity(Node currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() == null)
                .collect(Collectors.toList());
    }

    public List<Node> getConnectNodesWithEntity(Node currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() != null)
                .collect(Collectors.toList());
    }

    public Optional<Node> findEmptyNode(Node root) {
        Random random = new Random();
        int countIteration = 0;
        Coordinates coordinates;

        while (countIteration != 10) {
            coordinates = new Coordinates(
                    random.nextInt(Map.getSize().horizontal), random.nextInt(Map.getSize().vertical));

            Node node = getNodeByCoordinates(coordinates, root);

            if (node.getEntity() == null) {
                return Optional.of(node);
            }

            countIteration++;
        }
        return Optional.empty();
    }

    private static Node getNodeByCoordinates(Coordinates coordinates, Node root) {
        Node currNode = root;

        while (!currNode.getCoordinates().equals(coordinates)) {
            if (currNode.getCoordinates().getX() < coordinates.getX()) {
                currNode = currNode.getDown();
            }

            if (currNode.getCoordinates().getY() < coordinates.getY()) {
                currNode = currNode.getRight();
            }
        }

        return currNode;
    }

}
