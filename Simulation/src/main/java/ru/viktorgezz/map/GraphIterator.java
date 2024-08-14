package ru.viktorgezz.map;

import java.util.*;

public class GraphIterator implements Iterator<GraphNode> {

    private final Queue<GraphNode> queue = new LinkedList<>();
    private final Set<GraphNode> set = new HashSet<>();

    public GraphIterator(GraphNode root) {
        if (root != null)
            queue.add(root);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public GraphNode next() {
        GraphNode current = queue.poll();
        Objects.requireNonNull(current);

        if (current.getRight() != null && !set.contains(current.getRight())) {
            set.add(current.getRight());
            queue.add(current.getRight());
        }

        if (current.getDown() != null && !set.contains(current.getDown())) {
            set.add(current.getDown());
            queue.add(current.getDown());
        }

        if (current.getLeft() != null && !set.contains(current.getLeft())) {
            set.add(current.getLeft());
            queue.add(current.getLeft());
        }

        if (current.getUp() != null && !set.contains(current.getUp())) {
            set.add(current.getUp());
            queue.add(current.getUp());
        }

        return current;
    }
}
