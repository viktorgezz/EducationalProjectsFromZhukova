package ru.viktorgezz.map;

public class MapFactory {

    private final Integer HORIZONTAL_SIZE = Size.HORIZONTAL_SIZE;
    private final Integer VERTICAL_SIZE = Size.VERTICAL_SIZE;

    private final GraphNode[][] tempStorageNodes = new GraphNode[HORIZONTAL_SIZE][VERTICAL_SIZE];

    public GraphNode createMap() {
        createNodes();
        linkNodes();
        return tempStorageNodes[0][0];
    }


    private void createNodes() {
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < VERTICAL_SIZE; j++) {
                tempStorageNodes[i][j] = (new GraphNode(j, i));
            }
        }
    }

    private void linkNodes() {
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < VERTICAL_SIZE; j++) {
                if (i > 0) {
                    tempStorageNodes[i][j].setLeft(tempStorageNodes[i - 1][j]);
                }
                if (i < HORIZONTAL_SIZE - 1) {
                    tempStorageNodes[i][j].setRight(tempStorageNodes[i + 1][j]);
                }
                if (j > 0) {
                    tempStorageNodes[i][j].setUp(tempStorageNodes[i][j - 1]);
                }
                if (j < VERTICAL_SIZE - 1) {
                    tempStorageNodes[i][j].setDown(tempStorageNodes[i][j + 1]);
                }
            }
        }
    }
}
