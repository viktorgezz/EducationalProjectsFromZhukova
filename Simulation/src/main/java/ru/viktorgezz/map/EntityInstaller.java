package ru.viktorgezz.map;

import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Entity;

import java.util.List;

public interface EntityInstaller {

    void installEntityInNode(Entity entity, Node root);

    List<Creature> getCreatures();

    Integer getCountHerbivore();

    Integer getCountPredator();
}
