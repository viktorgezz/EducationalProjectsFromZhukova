package ru.viktorgezz.action.creature;

import ru.viktorgezz.action.Action;
import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

public class Movement extends Action {

    public  <T extends Entity> void move(Class<T> targetClass,
                                           Runnable actionCreature,
                                           Creature creature) {

        Optional<Queue<Node>> shortestPathOpt = searchNode.findShortestPathToTarget(targetClass, creature.getNode());

        if (shortestPathOpt.isPresent()) {
            Queue<Node> shortestPath = shortestPathOpt.get();

            if (shortestPath.isEmpty()) {
                actionCreature.run();
            } else {
                for (int i = 0; i < Math.min(creature.getStep(), shortestPath.size()); i++) {
                    changePosition(shortestPath.poll(), creature);
                }
            }
        } else {
            randomMove(creature);
        }
    }

    private void randomMove(Creature creature) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithNullEntity(creature.getNode());

        if (!linkedNodes.isEmpty()) {
            int randomIndexNode = new Random().nextInt(linkedNodes.size());
            changePosition(linkedNodes.get(randomIndexNode), creature);
        }
    }

    private void changePosition(Node newNode, Creature creature) {
        if (newNode != null && newNode.getEntity() == null) {
            creature.getNode().setEntity(null);
            creature.setNode(newNode);
            newNode.setEntity(creature);
        }
    }
}
