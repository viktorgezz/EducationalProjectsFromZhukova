package ru.viktorgezz.entity;

import ru.viktorgezz.map.GraphNode;

import java.util.Objects;
import java.util.UUID;


public abstract class Entity {

    private UUID id;

    private GraphNode node;

    public Entity() {
        setId(UUID.randomUUID());
    }

    public GraphNode getNode() {
        return node;
    }

    public void setNode(GraphNode node) {
        this.node = node;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
