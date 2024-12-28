package ru.viktorgezz.map;

import java.util.Objects;

public class Coordinates {

    private final Integer y; // vertical
    private final Integer x; // horizontal

    public Coordinates(Integer y, Integer x) {
        this.y = y;
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
