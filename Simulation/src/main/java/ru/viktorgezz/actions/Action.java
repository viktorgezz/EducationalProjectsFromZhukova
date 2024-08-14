package ru.viktorgezz.actions;

import ru.viktorgezz.map.MapWorld;

public abstract class Action {

    protected final MapWorld mapWorld;

    public Action(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    public abstract void perform();

}
