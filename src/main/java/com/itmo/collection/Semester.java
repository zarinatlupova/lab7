package com.itmo.collection;

public enum Semester {
    FOURTH(4),
    FIFTH(5),
    SEVENTH(7);
    private Integer value;

    Semester(Integer s) {
        this.value = s;
    }
    public Integer getValue() {
        return value;
    }

}
