package ru.viktorgezz.entity;

import ru.viktorgezz.map.Node;

import java.util.Objects;
import java.util.UUID;


public abstract class Entity {

    private UUID id;

    private Node node;

    public Entity() {
        setId(UUID.randomUUID());
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
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
