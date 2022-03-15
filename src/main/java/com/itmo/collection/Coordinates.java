package com.itmo.collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 32L;

    private Integer x; //Значение поля должно быть больше -553, Поле не может быть null
    private Integer y; //Значение поля должно быть больше -444, Поле не может быть null

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return
                "x=" + x +
                ",y=" + y +";" ;
    }
}
