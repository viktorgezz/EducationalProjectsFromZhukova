package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;

import java.util.ArrayList;
import java.util.List;

public class MapWorld {

    private final List<Creature> creatures = new ArrayList<>();
    private GraphNode root;

    public MapWorld() {
    }

    public void createWorld(int horizontalSize, int verticalSize) {
        Size.setSize(horizontalSize, verticalSize);
        root = new MapFactory().createMap();
    }

    public void addEntity(Entity entity, GraphNode node) {
        entity.setNode(node);
        node.setEntity(entity);
        if (entity instanceof Creature)
            creatures.add((Creature) entity);
    }

    public void removeImmobileEntity(Entity entity) {
        entity.getNode().setEntity(null);
    }

    public void removeCreature(Creature creature) {
        creature.getNode().setEntity(null);
        creatures.remove(creature);
    }

    public void changePosition(GraphNode newNode, Creature creature) {
        if (newNode != null && newNode.getEntity() == null) {
            creature.getNode().setEntity(null);
            creature.setNode(newNode);
            newNode.setEntity(creature);
        }
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public GraphNode getRoot() {
        return root;
    }

    public Integer getCountHerbivore() {
        return Math.toIntExact(creatures.stream().filter(her -> her instanceof Herbivore).count());
    }

    public Integer getCountPredator() {
        return Math.toIntExact(creatures.stream().filter(per -> per instanceof Predator).count());
    }

}
