package ru.viktorgezz.map;

public class MapFactory {

    private final Integer HORIZONTAL_SIZE;
    private final Integer VERTICAL_SIZE;
    private Node[][] storageNodes;

    private final Size size = new Size();

    public MapFactory(int horizontal, int vertical) {
        this.HORIZONTAL_SIZE = horizontal;
        this.VERTICAL_SIZE = vertical;
        size.horizontal = horizontal;
        size.vertical = vertical;
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

    public Size getSize() {
        return size;
    }

    private void initStorageNodes() {
        storageNodes = new Node[HORIZONTAL_SIZE][VERTICAL_SIZE];
    }


    private void createNodes() {
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < VERTICAL_SIZE; j++) {
                storageNodes[i][j] = (new Node(j, i));
            }
        }
    }

    private void linkNodes() {
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < VERTICAL_SIZE; j++) {
                if (i > 0) {
                    storageNodes[i][j].setLeft(storageNodes[i - 1][j]);
                }
                if (i < HORIZONTAL_SIZE - 1) {
                    storageNodes[i][j].setRight(storageNodes[i + 1][j]);
                }
                if (j > 0) {
                    storageNodes[i][j].setUp(storageNodes[i][j - 1]);
                }
                if (j < VERTICAL_SIZE - 1) {
                    storageNodes[i][j].setDown(storageNodes[i][j + 1]);
                }
            }
        }
    }
}
