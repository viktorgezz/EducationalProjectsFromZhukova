package ru.viktorgezz.map;

import ru.viktorgezz.entity.*;
import ru.viktorgezz.map.iservice.CreatureProvider;
import ru.viktorgezz.map.iservice.EntityInstaller;
import ru.viktorgezz.map.iservice.SearchNode;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс отвечает за управление сущностями (Entity) и существами (Creature) на карте,
 * включая их размещение, удаление и поиск по карте.
 */
public class MapService implements EntityInstaller, SearchNode, CreatureProvider {

    private final CreatureStorage creatureStorage;

    public MapService(CreatureStorage creatureStorage) {
        this.creatureStorage = creatureStorage;
    }

    /**
     * Удаляет траву (Grass) с узла карты.
     * @param grass Удаляемый объект травы.
     */
    public void removeGrass(Grass grass) {
        grass.getNode().setEntity(null);
    }

    /**
     * Удаляет существо с карты и из хранилища.
     * @param creature Удаляемое существо.
     */
    public void removeCreature(Creature creature) {
        creature.getNode().setEntity(null);
        creatureStorage.remove(creature);
    }

    /**
     * Размещает сущность в доступном пустом узле карты.
     * @param entity Размещаемая сущность.
     * @param root   Корневой узел карты.
     */
    public void installEntityInNode(Entity entity, Node root) {
        Optional<Node> emptyNode = findEmptyNode(root);
        if (emptyNode.isPresent()) {
            Node node = emptyNode.get();
            addEntity(entity, node);
        }
    }

    /**
     * Возвращает список всех существ на карте.
     * @return Список существ.
     */
    public CopyOnWriteArrayList<Creature> getCreatures() {
        return creatureStorage.get();
    }

    /**
     * Возвращает количество травоядных на карте.
     * @return Количество травоядных.
     */
    public Integer getCountHerbivore() {
        return Math.toIntExact(creatureStorage.get().stream().filter(her -> her instanceof Herbivore).count());
    }

    /**
     * Возвращает количество хищников на карте.
     * @return Количество хищников.
     */
    public Integer getCountPredator() {
        return Math.toIntExact(creatureStorage.get().stream().filter(per -> per instanceof Predator).count());
    }

    /**
     * Добавляет сущность в указанный узел карты и обновляет хранилище, если это существо.
     * @param entity Добавляемая сущность.
     * @param node   Узел карты.
     */
    private void addEntity(Entity entity, Node node) {
        entity.setNode(node);
        node.setEntity(entity);
        if (entity instanceof Creature) {
            creatureStorage.add((Creature) entity);
        }
    }

    /**
     * Находит кратчайший путь до узла с сущностью заданного типа.
     * Алгоритм поиска A*.
     * @param targetClazz Тип сущности, которой будет производиться поиск.
     * @param currNode    Текущий узел сущности, от которой будет строиться путь.
     * @return Очередь узлов, составляющих кратчайший путь.
     */
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

    /**
     * Находит узел ближайшей сущности заданного типа.
     * @param clazz   Тип сущности.
     * @param currNode Текущий узел.
     * @return Узел с искомой сущностью.
     */
    private <T extends Entity> Node searchTarget(Class<T> clazz, Node currNode) {
        NodeIterator nodeIterator = new NodeIterator(currNode);

        while (nodeIterator.hasNext()) {
            currNode = nodeIterator.next();
            if (currNode.getEntity() != null && currNode.getEntity().getClass() == clazz)
                return currNode;

        }
        return currNode;
    }

    /**
     * Возвращает список соседних соединённых узлов без сущностей (свободных, в которых можно перейти).
     * @param currNode Текущий узел.
     * @return Список соединённых узлов.
     */
    public List<Node> getConnectNodesWithNullEntity(Node currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() == null)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех соседних соединённых узлов.
     * @param currNode Текущий узел.
     * @return Список соединённых узлов.
     */
    public List<Node> getConnectNodesWithEntity(Node currNode) {
        return Stream.of(currNode.getUp(), currNode.getRight(), currNode.getDown(), currNode.getLeft())
                .filter(node -> node != null && node.getEntity() != null)
                .collect(Collectors.toList());
    }

    /**
     * Ищет пустой узел в пределах карты.
     * @param root Корневой узел карты.
     * @return Пустой узел.
     */
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

    /**
     * Возвращает узел по координатам.
     *
     * @param coordinates Координаты узла.
     * @param root Корневой узел карты.
     * @return Узел по заданным координатам.
     */
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
