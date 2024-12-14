package ru.viktorgezz.menu;

public class MenuItem {

    private final String description;
    private final Exec exec;

    public MenuItem(String description, Exec exec) {
        this.description = description;
        this.exec = exec;
    }

    public String getDescription() {
        return description;
    }

    public Exec getExec() {
        return exec;
    }
}
