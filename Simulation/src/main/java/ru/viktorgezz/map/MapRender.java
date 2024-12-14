package ru.viktorgezz.map;


import ru.viktorgezz.entity.Entity;
import ru.viktorgezz.entity.Predator;

import java.util.LinkedList;
import java.util.Queue;

public class MapRender {

    private final Node root;

    private static final String ANSI_RESET = "\u001b[0m";
    private static final String GREEN_COLOR = "\u001B[32m";
    private static final String RED_COLOR = "\u001B[31m";
    private static final String LIGHT_GREEN_COLOR = "\u001B[38;5;120m";
    private static final String GRAY_COLOR = "\u001B[38;5;245m";
    private static final String DARK_GREEN_COLOR = "\u001B[38;5;22m";

    public MapRender(Node root) {
        this.root = root;
    }

    public void printMap() {
        Queue<Node> queue = new LinkedList<>();
        Node node = root;

        while (node != null) {
            while (true) {
                queue.add(node);
                if (node.getEntity() != null) {
                    printSelectedSpriteEntity(node.getEntity());
                } else System.out.print("□ ");

                if (node.getRight() == null) break;
                node = node.getRight();

            }
            if (queue.peek() != null)
                node = queue.poll().getDown();
            queue.clear();
            System.out.println();
        }


        System.out.println();
        System.out.println();
    }


    private void printSelectedSpriteEntity(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Herbivore":
                System.out.print(GREEN_COLOR + "T");
                break;

            case "Predator":
                int countMurders = ((Predator) entity).getCountMurders();

                if (countMurders > 2) {
                    System.out.print(RED_COLOR + "x");
                    break;
                }

                System.out.print(RED_COLOR + "X");
                break;

            case "Grass":
                System.out.print(LIGHT_GREEN_COLOR + "*");
                break;

            case "Rock":
                System.out.print(GRAY_COLOR + "O");
                break;

            case "Tree":
                System.out.print(DARK_GREEN_COLOR + "Ψ");
                break;

            default:
                System.out.print("_");
        }

        resetColorConsole();
        System.out.print(" ");
    }

    private void resetColorConsole() {
        System.out.print(ANSI_RESET);
    }
}
