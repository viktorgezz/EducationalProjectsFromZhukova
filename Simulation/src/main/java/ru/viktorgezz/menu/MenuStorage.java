package ru.viktorgezz.menu;

import ru.viktorgezz.simulation.Turn;

import java.util.List;

public class MenuStorage {

    private static Menu menu;

    static {
        initMenu();
    }

    static private void initMenu() {
        menu = new Menu(
                List.of(
                        new MenuItem("Текущий ход", new Turn()::printCurrTurn),
                        new MenuItem("Один ход", new Turn()::performOneTurn),
                        new MenuItem("Бесконечный ход", new Turn()::performInfinityTurn)
                )
        );
    }

    public static Menu getMenu() {
        return menu;
    }
}
