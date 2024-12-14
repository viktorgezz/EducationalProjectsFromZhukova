package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.util.SearchNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapService implements EntityInstaller{

    private final SearchNode searchNode = new SearchNode();

    private final List<Creature> creatures;

    public MapService(List<Creature> creatures) {
        this.creatures = creatures;
    }


    public void removeImmobileEntity(Entity entity) {
        entity.getNode().setEntity(null);
    }

    public void removeCreature(Creature creature) {
        creature.getNode().setEntity(null);
        creatures.remove(creature);
    }

    public void changePosition(Node newNode, Creature creature) {
        if (newNode != null && newNode.getEntity() == null) {
            creature.getNode().setEntity(null);
            creature.setNode(newNode);
            newNode.setEntity(creature);
        }
    }

    private void addEntity(Entity entity, Node node) {
        entity.setNode(node);
        node.setEntity(entity);
        if (entity instanceof Creature)
            creatures.add((Creature) entity);
    }

    public void installEntityInNode(Entity entity, Node root) {
        Optional<Node> emptyNode = searchNode.findEmptyNode(root);
        if (emptyNode.isPresent()) {
            Node node = emptyNode.get();
            addEntity(entity, node);
        }
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public Integer getCountHerbivore() {
        return Math.toIntExact(creatures.stream().filter(her -> her instanceof Herbivore).count());
    }

    public Integer getCountPredator() {
        return Math.toIntExact(creatures.stream().filter(per -> per instanceof Predator).count());
    }

}
