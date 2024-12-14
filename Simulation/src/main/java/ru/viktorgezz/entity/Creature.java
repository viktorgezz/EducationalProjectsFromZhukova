package ru.viktorgezz.entity;

import ru.viktorgezz.util.SearchNode;
import ru.viktorgezz.map.Node;
import ru.viktorgezz.map.MapService;

import java.util.*;

public abstract class Creature extends Entity {

    private Integer step;
    private Integer hp;
    private Integer age;

    protected SearchNode searchNode = new SearchNode();
    protected final MapService mapService = new MapService();


    public Creature() {
    }

    public abstract void playAction();

    protected <T extends Entity> void move(Class<T> targetClass,
                                           Runnable actionCreature) {
        Node currNode = getNode();

        Optional<Queue<Node>> shortestPathOpt = searchNode.findShortestPathToTarget(targetClass, currNode);

        if (shortestPathOpt.isPresent()) {
            Queue<Node> shortestPath = shortestPathOpt.get();

            if (shortestPath.isEmpty()) {
                actionCreature.run();
            } else {
                for (int i = 0; i < Math.min(getStep(), shortestPath.size()); i++) {
                    mapService.changePosition(shortestPath.poll(), this);
                }
            }
        } else {
            randomMove();
        }
    }

    protected void randomMove() {
        List<Node> linkedNodes = searchNode.getConnectNodesWithNullEntity(this.getNode());

        if (!linkedNodes.isEmpty()) {
            int randomIndexNode = new Random().nextInt(linkedNodes.size());
            mapService.changePosition(linkedNodes.get(randomIndexNode), this);
        }
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}
