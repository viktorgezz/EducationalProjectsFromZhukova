package ru.viktorgezz.util;

public class Turn {
    private void nextTurn() {
        fillMainActions();
        turnActions.pop().perform();
        printCountTern();
        printMap();
        countTern++;
    }
}
