package ru.viktorgezz.map;

import ru.viktorgezz.entity.Entity;

import java.util.Objects;

public class Node {

    private Entity entity;

    private Node up;
    private Node down;
    private Node left;
    private Node right;

    private final Coordinates coordinates;

    public Node(Integer x, Integer y) {
        coordinates = new Coordinates(x, y);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
