package ru.viktorgezz;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Coordinates;
import ru.viktorgezz.map.GraphNode;
import ru.viktorgezz.map.GraphIterator;
import ru.viktorgezz.map.Size;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchGraph {

    public <T extends Entity> Optional<Queue<GraphNode>> findShortestPathToTarget(Class<T> targetClazz, GraphNode currNode) {
        GraphNode targetNode = searchTarget(targetClazz, currNode);

        Queue<GraphNode> closeList = new LinkedList<>();
        Set<GraphNode> openList = new HashSet<>();

        double distanceStartMin = Double.MAX_VALUE;

        while (!(currNode.getUp() == targetNode || currNode.getDown() == targetNode
                || currNode.getRight() == targetNode || currNode.getLeft() == targetNode)) {

            List<GraphNode> linkNodes = getConnectNodesWithNullEntity(currNode);
            if (linkNodes.isEmpty()) {
                return Optional.empty();
            }

            for (GraphNode node : linkNodes) {
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

    private <T extends Entity> GraphNode searchTarget(Class<T> clazz, GraphNode currNode) {
        GraphIterator graphIterator = new GraphIterator(currNode);

        while (graphIterator.hasNext()) {
            currNode = graphIterator.next();
            if (currNode.getEntity() != null && currNode.getEntity().getClass() == clazz)
                return currNode;

        }
        return currNode;
    }

    public List<GraphNode> getConnectNodesWithNullEntity(GraphNode currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() == null)
                .collect(Collectors.toList());
    }

    public List<GraphNode> getConnectNodesWithEntity(GraphNode currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() != null)
                .collect(Collectors.toList());
    }

    public Optional<GraphNode> findEmptyNode(GraphNode root) {
        Random random = new Random();
        int countIteration = 0;
        Coordinates coordinates;

        while (countIteration != 10) {
            coordinates = new Coordinates(
                    random.nextInt(Size.HORIZONTAL_SIZE), random.nextInt(Size.VERTICAL_SIZE));

            GraphNode node = getNodeByCoordinates(coordinates, root);

            if (node.getEntity() == null) {
                return Optional.of(node);
            }

            countIteration++;
        }
        return Optional.empty();
    }

    private static GraphNode getNodeByCoordinates(Coordinates coordinates, GraphNode root) {
        GraphNode currNode = root;

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
