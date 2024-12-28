package ru.viktorgezz.map;

public class MapFactory {

    private final Integer HORIZONTAL_SIZE;
    private final Integer VERTICAL_SIZE;
    private Node[][] storageNodes;

    public MapFactory(int horizontal, int vertical) {
        this.HORIZONTAL_SIZE = horizontal;
        this.VERTICAL_SIZE = vertical;
        Size.horizontal = horizontal;
        Size.vertical = vertical;
        create();
    }

    private void create() {
        initStorageNodes();
        createNodes();
        linkNodes();
    }

    public Node getRoot() {
        return storageNodes[0][0];
    }

    private void initStorageNodes() {
        storageNodes = new Node[HORIZONTAL_SIZE][VERTICAL_SIZE];
    }


    private void createNodes() {
        for (int y = 0; y < VERTICAL_SIZE; y++) {
            for (int x = 0; x < HORIZONTAL_SIZE; x++) {
                storageNodes[x][y] = new Node(y, x);
            }
        }
    }

    private void linkNodes() {
        for (int y = 0; y < VERTICAL_SIZE; y++) {
            for (int x = 0; x < HORIZONTAL_SIZE; x++) {
                if (x > 0) {
                    storageNodes[x][y].setLeft(storageNodes[x - 1][y]);
                }
                if (x < HORIZONTAL_SIZE - 1) {
                    storageNodes[x][y].setRight(storageNodes[x + 1][y]);
                }
                if (y > 0) {
                    storageNodes[x][y].setUp(storageNodes[x][y - 1]);
                }
                if (y < VERTICAL_SIZE - 1) {
                    storageNodes[x][y].setDown(storageNodes[x][y + 1]);
                }
            }
        }
    }
}
