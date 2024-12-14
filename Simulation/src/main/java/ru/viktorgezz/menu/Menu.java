package ru.viktorgezz.menu;

import java.util.List;

public class Menu {

    private final List<MenuItem> items;

    public Menu(List<MenuItem> items) {
        this.items = items;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
