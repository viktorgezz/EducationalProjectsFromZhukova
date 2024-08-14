package ru.viktorgezz.actions;

import ru.viktorgezz.SearchGraph;
import ru.viktorgezz.entity.*;
import ru.viktorgezz.map.GraphNode;
import ru.viktorgezz.map.MapWorld;
import ru.viktorgezz.map.Size;

import java.util.*;

public class CreationEntities extends Action {

    private static final SearchGraph searchGraph = new SearchGraph();

    protected final static Integer NUM_HERBIVORE =
            (int) Math.ceil(Size.VERTICAL_SIZE * Size.HORIZONTAL_SIZE * 0.04);

    protected final static Integer NUM_PREDATOR =
            (int) Math.ceil(Size.VERTICAL_SIZE * Size.HORIZONTAL_SIZE * 0.08);

    private final static Integer NUM_TREE =
            (int) Math.ceil(Size.VERTICAL_SIZE * Size.HORIZONTAL_SIZE * 0.04);

    private final static Integer NUM_ROCK =
            (int) Math.ceil(Size.VERTICAL_SIZE * Size.HORIZONTAL_SIZE * 0.04);

    protected final static Integer NUM_GRASS =
            (int) Math.ceil(Size.VERTICAL_SIZE * Size.HORIZONTAL_SIZE * 0.08);

    public CreationEntities(MapWorld mapWorld) {
        super(mapWorld);
    }

    protected void installEntityInNode(Entity entity) {
        Optional<GraphNode> emptyNode = searchGraph.findEmptyNode(mapWorld.getRoot());
        if (emptyNode.isPresent()) {
            GraphNode node = emptyNode.get();
            mapWorld.addEntity(entity, node);
        }
    }

    public void perform() {
        for (int i = 0; i < NUM_HERBIVORE; i++) {
            installEntityInNode(new Herbivore(mapWorld));
        }

        for (int i = 0; i < NUM_PREDATOR; i++) {
            installEntityInNode(new Predator(mapWorld));
        }

        for (int i = 0; i < NUM_TREE; i++) {
            installEntityInNode(new Tree());
        }

        for (int i = 0; i < NUM_ROCK; i++) {
            installEntityInNode(new Rock());
        }

        for (int i = 0; i < NUM_GRASS; i++) {
            installEntityInNode(new Grass());
        }
    }
}
