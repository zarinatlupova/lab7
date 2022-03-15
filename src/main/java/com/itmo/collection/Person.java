package com.itmo.collection;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Person implements Serializable {
    private static final long serialVersionUID = 32L;

    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null

    public Person(String name, LocalDateTime birthday, Color eyeColor, Color hairColor, Country nationality) {
        this.name = name;
        this.birthday = birthday ;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }


    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return
                "(name='" + name + '\'' +
                ",birthday=" + birthday +
                ",eyeColor=" + eyeColor +
                ",hairColor=" + hairColor +
                ",nationality=" + nationality +
                ')';
    }
    public String getBirthdayInFormat(){
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(birthday);
    }

}
