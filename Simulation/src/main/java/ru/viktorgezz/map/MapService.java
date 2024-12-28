package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.iservice.CreatureProvider;
import ru.viktorgezz.map.iservice.EntityInstaller;
import ru.viktorgezz.map.iservice.SearchNode;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapService implements EntityInstaller, SearchNode, CreatureProvider {

    private final CreatureStorage creatureStorage;

    public MapService(CreatureStorage creatureStorage) {
        this.creatureStorage = creatureStorage;
    }

    public void removeImmobileEntity(Entity entity) {
        entity.getNode().setEntity(null);
    }

    public void removeCreature(Creature creature) {
        creature.getNode().setEntity(null);
        creatureStorage.remove(creature);
    }

    public void installEntityInNode(Entity entity, Node root) {
        Optional<Node> emptyNode = findEmptyNode(root);
        if (emptyNode.isPresent()) {
            Node node = emptyNode.get();
            addEntity(entity, node);
        }
    }

    public CopyOnWriteArrayList<Creature> getCreatures() {
        return creatureStorage.get();
    }

    public Integer getCountHerbivore() {
        return Math.toIntExact(creatureStorage.get().stream().filter(her -> her instanceof Herbivore).count());
    }

    public Integer getCountPredator() {
        return Math.toIntExact(creatureStorage.get().stream().filter(per -> per instanceof Predator).count());
    }

    private void addEntity(Entity entity, Node node) {
        entity.setNode(node);
        node.setEntity(entity);
        if (entity instanceof Creature) {
            creatureStorage.add((Creature) entity);
        }
    }

    // Search node
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
        NodeIterator nodeIterator = new NodeIterator(currNode);

        while (nodeIterator.hasNext()) {
            currNode = nodeIterator.next();
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

    private Optional<Node> findEmptyNode(Node root) {
        Random random = new Random();
        int countIteration = 0;
        Coordinates coordinates;

        while (countIteration != 10) {
            coordinates = new Coordinates(
                    random.nextInt(Size.vertical),random.nextInt(Size.horizontal)
            );

            Node node = getNodeByCoordinates(coordinates, root);

            if (node.getEntity() == null) {
                return Optional.of(node);
            }

            countIteration++;
        }
        return Optional.empty();
    }

    private Node getNodeByCoordinates(Coordinates coordinates, Node root) {
        Node currNode = root;

        while (!currNode.getCoordinates().equals(coordinates)) {
            if (currNode.getCoordinates().getX() < coordinates.getX()) {
                currNode = currNode.getRight();
            }

            if (currNode.getCoordinates().getY() < coordinates.getY()) {
                currNode = currNode.getDown();
            }
        }
        return currNode;
    }

}
