package ru.viktorgezz.entity;

import ru.viktorgezz.SearchGraph;
import ru.viktorgezz.map.GraphNode;
import ru.viktorgezz.map.MapWorld;

import java.util.*;

public abstract class Creature extends Entity {

    private Integer step;
    private Integer hp;
    private Integer age;

    protected SearchGraph searchGraph = new SearchGraph();
    protected final MapWorld mapWorld;


    public Creature(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    public abstract void playAction();

    protected <T extends Entity> void move(Class<T> targetClass,
                                           Runnable actionCreature) {
        GraphNode currNode = getNode();

        Optional<Queue<GraphNode>> shortestPathOpt = searchGraph.findShortestPathToTarget(targetClass, currNode);

        if (shortestPathOpt.isPresent()) {
            Queue<GraphNode> shortestPath = shortestPathOpt.get();

            if (shortestPath.isEmpty()) {
                actionCreature.run();
            } else {
                for (int i = 0; i < Math.min(getStep(), shortestPath.size()); i++) {
                    mapWorld.changePosition(shortestPath.poll(), this);
                }
            }
        } else {
            randomMove();
        }
    }

    protected void randomMove() {
        List<GraphNode> linkedNodes = searchGraph.getConnectNodesWithNullEntity(this.getNode());

        if (!linkedNodes.isEmpty()) {
            int randomIndexNode = new Random().nextInt(linkedNodes.size());
            mapWorld.changePosition(linkedNodes.get(randomIndexNode), this);
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
