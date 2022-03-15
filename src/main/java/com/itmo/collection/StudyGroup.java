package com.itmo.collection;

import com.itmo.client.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Random;

public class StudyGroup extends StudyGroupFactory implements Serializable  {
    private static final long serialVersionUID = 32L;

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    @Getter
    @Setter
    private String ownerName;


    @Setter @Getter
    private User user;

    public StudyGroup(String name, Coordinates coordinates, long studentsCount,
                      FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin) {
        this.id = generateId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }
    public static int generateId(){
        Random r = new Random();
        Integer randomVal = Math.abs(r.nextInt());
        return randomVal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    @Override
    public String toString() {
        return "StudyGroup:" +
                "\n [ id=" + id +
                "\n   name='" + name + '\'' +
                "\n   coordinates=" + coordinates +
                "\n   creationDate=" + creationDate +
                "\n   studentsCount=" + studentsCount +
                "\n   formOfEducation=" + formOfEducation +
                "\n   semesterEnum=" + semesterEnum +
                "\n   groupAdmin=" + groupAdmin+"]\n" ;
    }
    public String getCreationDateInFormat(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }
}
