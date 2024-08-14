package ru.viktorgezz.map;

import ru.viktorgezz.entity.Entity;

import java.util.Objects;

public class GraphNode {

    private Entity entity;

    private GraphNode up;
    private GraphNode down;
    private GraphNode left;
    private GraphNode right;

    private final Coordinates coordinates;

    public GraphNode(Integer x, Integer y) {
        coordinates = new Coordinates(x, y);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public GraphNode getUp() {
        return up;
    }

    public void setUp(GraphNode up) {
        this.up = up;
    }

    public GraphNode getDown() {
        return down;
    }

    public void setDown(GraphNode down) {
        this.down = down;
    }

    public GraphNode getLeft() {
        return left;
    }

    public void setLeft(GraphNode left) {
        this.left = left;
    }

    public GraphNode getRight() {
        return right;
    }

    public void setRight(GraphNode right) {
        this.right = right;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode node = (GraphNode) o;
        return Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
