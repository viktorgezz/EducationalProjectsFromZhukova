package ru.viktorgezz.action.creature;

import ru.viktorgezz.action.Action;
import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

/**
 * Класс Movement отвечает за перемещение существ (Creature) по карте.
 */
public class Movement extends Action {

    /**
     * Перемещает существо к цели, либо выполняет случайное движение, если цель не найдена.
     *
     * @param targetClass   Класс цели, к которой нужно двигаться.
     * @param actionCreature Действие, которое выполняется существом при достижении цели.
     * @param creature      Существо, которое перемещается.
     * @param <T>           Тип сущности цели.
     */
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

    /**
     * Выполняет случайное перемещение существа на соседний доступный узел.
     *
     * @param creature Существо, которое движется.
     */
    private void randomMove(Creature creature) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithNullEntity(creature.getNode());

        if (!linkedNodes.isEmpty()) {
            int randomIndexNode = new Random().nextInt(linkedNodes.size());
            changePosition(linkedNodes.get(randomIndexNode), creature);
        }
    }

    /**
     * Изменяет положение существа на новый узел.
     *
     * @param newNode  Новый узел для перемещения.
     * @param creature Существо, которое перемещается.
     */
    private void changePosition(Node newNode, Creature creature) {
        if (newNode != null && newNode.getEntity() == null) {
            creature.getNode().setEntity(null);
            creature.setNode(newNode);
            newNode.setEntity(creature);
        }
    }
}
