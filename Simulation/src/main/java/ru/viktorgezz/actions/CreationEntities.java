package ru.viktorgezz.actions;

import ru.viktorgezz.entity.*;
import ru.viktorgezz.map.EntityInstaller;
import ru.viktorgezz.map.MapService;
import ru.viktorgezz.map.Node;

import java.util.List;

public class CreationEntities extends Action {

    private List<Creature> creatures;
    public CreationEntities(int vertical, int horizontal, Node root, List<Creature> creatures) {
        this.root = root;
        this.creatures = creatures;
        this.square = vertical * horizontal;
    }

    protected  EntityInstaller entityInstaller = new MapService(creatures);

    private Integer square; // ошибка

    protected final Node root;

    protected final Integer NUM_HERBIVORE =
            (int) Math.ceil(square * 0.04);

    protected final Integer NUM_PREDATOR =
            (int) Math.ceil(square * 0.08);

    private final Integer NUM_TREE =
            (int) Math.ceil(square * 0.04);

    private final Integer NUM_ROCK =
            (int) Math.ceil(square * 0.04);

    protected final Integer NUM_GRASS =
            (int) Math.ceil(square * 0.08);


    public void perform() {
        for (int i = 0; i < NUM_HERBIVORE; i++) {
            entityInstaller.installEntityInNode(new Herbivore(), root);
        }

        for (int i = 0; i < NUM_PREDATOR; i++) {
            entityInstaller.installEntityInNode(new Predator(), root);
        }

        for (int i = 0; i < NUM_TREE; i++) {
            entityInstaller.installEntityInNode(new Tree(), root);
        }

        for (int i = 0; i < NUM_ROCK; i++) {
            entityInstaller.installEntityInNode(new Rock(), root);
        }

        for (int i = 0; i < NUM_GRASS; i++) {
            entityInstaller.installEntityInNode(new Grass(), root);
        }
    }
}
