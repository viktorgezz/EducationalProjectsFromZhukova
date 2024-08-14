package ru.viktorgezz;

public class Gallows {

    public Gallows() {
        this.stage = 0;
    }

    private int stage;

    public void nextStage(){
        this.stage += 1;
    }

    public boolean isOver() {
        return this.stage >= 5;
    }

    public void printGallows() {
        System.out.println();
        switch (this.stage) {
            case 1:
                System.out.println("_____");
                break;
            case 2:
                System.out.println("|\n" +
                        "|\n" +
                        "|\n" +
                        "|\n" +
                        "|_____");
                break;
            case 3:
                System.out.println("_____\n" +
                        "|    |\n" +
                        "|\n" +
                        "|\n" +
                        "|\n" +
                        "|_____");
                break;
            case 4:
                System.out.println("_____\n" +
                        "|    |\n" +
                        "|    O\n" +
                        "|\n" +
                        "|\n" +
                        "|\n" +
                        "|_____");
                break;
            case 5:
                System.out.println("_____\n" +
                        "|    |\n" +
                        "|    O\n" +
                        "|   /|\\\n" +
                        "|   / \\\n" +
                        "|\n" +
                        "|_____");
                System.out.println("Game over");
                break;
            default:
                return;
        }
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
