package com.itmo.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Класс для создания объекта коллекции
 */
public class StudyGroupFactory {

    private static Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private static String name; //Поле не может быть null, Строка не может быть пустой
    private static Coordinates coordinates; //Поле не может быть null
    private static LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private static long studentsCount; //Значение поля должно быть больше 0
    private static FormOfEducation formOfEducation; //Поле может быть null
    private static Semester semesterEnum; //Поле не может быть null
    private static Person groupAdmin; //Поле может быть null
    private static Integer x; //Значение поля должно быть больше -553, Поле не может быть null
    private static Integer y; //Значение поля должно быть больше -444, Поле не может быть null
    private static String nameP; //Поле не может быть null, Строка не может быть пустой
    private static LocalDateTime birthday; //Поле может быть null
    private static Color eyeColor; //Поле может быть null
    private static Color hairColor; //Поле может быть null
    private static Country nationality; //Поле может быть null


    public static Integer getID() {
        return id;
    }

    public static void setName() {
        Scanner names = new Scanner(System.in);
        name = names.nextLine();
        try {
            new Double(name);
//            System.out.println("Мы вс");
//            setName();
        } catch (NumberFormatException e) {
            if (name.length() >= 1) {
                return;
            } else {
                System.out.println("Пустого названия быть не может");
                setName();
            }

        }
    }

    public static void setX() {
        Scanner scanner = new Scanner(System.in);
        String corX = scanner.nextLine();
        try {
            Integer d = Integer.parseInt(corX);
            if (d > -553) {
                x = d;
            } else {
                System.out.println("Координата не может быть меньше -553, попробуйте снова");
                setX();
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(" только ЦИФРЫ и без пустых полей");
            setX();
        }

    }

    public static void setY() {
        Scanner scanner = new Scanner(System.in);
        String corY = scanner.nextLine();
        try {
            Integer s = Integer.parseInt(corY);
            if (s > -444) {
                y = s;
            } else {
                System.out.println("Координата не может быть меньше -444, попробуйте снова");
                setY();
            }

        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(" только ЦИФРЫ и без пустых полей ");
            setY();
        }
        coordinates = new Coordinates(x, y);
    }

    public static void setStudentsCount() {
        Scanner scanner = new Scanner(System.in);
        String count = scanner.nextLine();

        try {
            Long c = Long.parseLong(count);
            if ( c > 0) {
                studentsCount = c;
            } else {
                System.out.println("TRY AGAIN! Количество студентов не может быть отрицательным=))");
                setStudentsCount();
            }
        } catch (NumberFormatException e) {
            System.out.println("попробуйте снова ввести ЦИФРЫ");
            setStudentsCount();
        }

    }

    public static void setNameP() {
        Scanner names = new Scanner(System.in);
        nameP = names.nextLine();
        try {
            new Double(nameP);
            System.out.println("Имя человека цифрами - это сильно");
            setNameP();
        } catch (NumberFormatException e) {
            if (nameP.length() >= 1) {
                return;
            } else {
                System.out.println("Пустое название? БАН");
                setNameP();
            }

        }
    }

    public static void setBirthday(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите день рождения");
        String day = scanner.nextLine();
        System.out.println("Введите месяц рождения");
        String month = scanner.nextLine();
        System.out.println("Введите год рождения");
        String year = scanner.nextLine();
        String zero = "0";
        String birth = null;
        try {
        if(Integer.parseInt(day)<10&&Integer.parseInt(month)>10){
            birth = year+"-"+month+"-"+zero+day;
        }else if(Integer.parseInt(month)<10&&Integer.parseInt(day)>10){
            birth = year+"-"+zero+month+"-"+day;
        }else if(Integer.parseInt(day)<10&&Integer.parseInt(month)<10){
            birth = year+"-"+zero+month+"-"+zero+day;
        }else {
            birth = year+"-"+month+"-"+day;
        }
        System.out.println(birth);
        birthday = LocalDate.parse(birth);

            Integer dayD = Integer.parseInt(day);
            Integer monthD = Integer.parseInt(month);
            Integer yearD = Integer.parseInt(year);

        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(" элемент пропущен");

        }catch (DateTimeParseException ex){
            System.out.println("некорректная дата , попробуйте еще раз");
            setBirthday();
        }
    }

    public static void setFormOfEducation() {

//        System.out.println("    Введите номер выбранного значения:");
        System.out.println("1 – DISTANCE_EDUCATION");
        System.out.println("2 – FULL_TIME_EDUCATION");
        System.out.println("3 – EVENING_CLASSES");
        Scanner scanner = new Scanner(System.in);
        String formOfEd = scanner.nextLine();

        try {
            if (formOfEd.equals("1") || formOfEd.equals("2") || formOfEd.equals("3") ) {
                switch (formOfEd) {
                    case ("1") :
                        formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                        break;
                    case ("2") :
                        formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                        break;
                    case ("3") :
                        formOfEducation = FormOfEducation.EVENING_CLASSES;
                        break;
                }
                System.out.println("      Элемент выбран!");
            }else if( Integer.parseInt(formOfEd) > 3  ){
                System.out.println("Такого нет, пробуйте еще раз");
                setFormOfEducation();
            }
        }catch (NumberFormatException ex){
            System.out.println("Элемент пропущен");

        }
    }

    public static void setSemester() {

        System.out.println("    Введите одно из значений, введите номер выбранного значения:");
        System.out.println("1 – FIFTH");
        System.out.println("2 – SEVENTH");
        System.out.println("3 – FOURTH");
        Scanner scanner = new Scanner(System.in);
        String _semester = scanner.nextLine();

        if (_semester.equals("1") || _semester.equals("2") || _semester.equals("3") ) {
            switch (_semester) {
                case ("1"):
                    semesterEnum = Semester.FIFTH;
                    break;

                case ("2"):
                    semesterEnum = Semester.SEVENTH;
                    break;

                case ("3"):
                    semesterEnum = Semester.FOURTH;
                    break;

            }
            System.out.println("      Элемент выбран!");
        } else {
            System.out.println("Мы принимаем только ЦИФРЫ и ЕМАЕ давай без пустых полей ладно?");
            setSemester();
        }
    }

    public static void setEyeColor() {
        System.out.println("1 – BLACK");
        System.out.println("2 – BLUE");
        System.out.println("3 – RED");
        System.out.println("4 — GREEN");
        System.out.println("5 — WHITE");
        Scanner scanner = new Scanner(System.in);
        String eyeC = scanner.nextLine();

        try {
            if (eyeC.equals("1") || eyeC.equals("2") || eyeC.equals("3") || eyeC.equals("4") || eyeC.equals("5")) {
                switch (eyeC) {
                    case ("1") :
                        eyeColor = Color.BLACK;
                        break;
                    case ("2") :
                        eyeColor = Color.BLUE;
                        break;
                    case ("3") :
                        eyeColor = Color.RED;
                        break;
                    case ("4") :
                        eyeColor = Color.GREEN;
                        break;
                    case ("5") :
                        eyeColor = Color.WHITE;
                        break;
                }
                System.out.println("      Элемент выбран!");
            } else if (Integer.parseInt(eyeC)>5) {
                System.out.println("Такого нет, пробуйте еще раз");
                setEyeColor();
            }
        }catch (NumberFormatException ex){
                System.out.println("Элемент пропущен");
        }
    }

    public static void setHairColor() {
        System.out.println("1 – ORANGE");
        System.out.println("2 – BLUE");
        System.out.println("3 – RED");
        System.out.println("4 — GREEN");
        System.out.println("5 — WHITE");
        Scanner scanner = new Scanner(System.in);
        String eyeC = scanner.nextLine();

        try {
            if (eyeC.equals("1") || eyeC.equals("2") || eyeC.equals("3") || eyeC.equals("4") || eyeC.equals("5")) {
                switch (eyeC) {
                    case ("1") :
                        hairColor = Color.ORANGE;
                        break;
                    case ("2") :
                        hairColor = Color.BLUE;
                        break;
                    case ("3") :
                        hairColor = Color.RED;
                        break;
                    case ("4") :
                        hairColor = Color.GREEN;
                        break;
                    case ("5") :
                        hairColor = Color.WHITE;
                        break;
                }
                System.out.println("      Элемент выбран!");
            } else if (Integer.parseInt(eyeC)>5){
                System.out.println("Такого нет, пробуйте еще раз");
                setHairColor();
            }
        }catch (NumberFormatException ex) {
            System.out.println("Элемент пропущен");
        }

    }

    public static void setNationality() {

        System.out.println("1 – ITALY");
        System.out.println("2 – USA");
        System.out.println("3 – VATICAN");
        System.out.println("4 — UNITED_KINGDOM");
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        try {
            if (country.equals("1") || country.equals("2") || country.equals("3") || country.equals("4")) {
                switch (country) {
                    case ("1") :
                        nationality = Country.ITALY;
                        break;
                    case ("2") :
                        nationality = Country.USA;
                        break;
                    case ("3") :
                        nationality = Country.VATICAN;
                        break;
                    case ("4") :
                        nationality = Country.UNITED_KINGDOM;
                        break;
                }
                System.out.println("      Элемент выбран!");
            } else if(Integer.parseInt(country)>4) {
                System.out.println("Такого нет, пробуйте еще раз");
                setNationality();
            }
        }catch (NumberFormatException ex){
            System.out.println("Элемент пропущен");

        }

    }

    public static StudyGroup studyGroupFactory(){

        System.out.println("Введите название группы:");
        setName();
        System.out.println("Введите значение координаты X:");
        setX();
        System.out.println("Введите значение координаты Y:");
        setY();
        System.out.println("Введите количество  студентов:");
        setStudentsCount();
        System.out.println("Введите форму обучения:");
        setFormOfEducation();
        System.out.println("Введите семестр:");
        setSemester();
        System.out.println("   Имя студента:");
        setNameP();
        System.out.println("Day of birth");
        setBirthday();
        System.out.println("Цвет глаз:");
        setEyeColor();
        System.out.println("Цвет волос:");
        setHairColor();
        System.out.println("СТрана:");
        setNationality();


        StudyGroup sp = new StudyGroup(name,new Coordinates(x,y),studentsCount, formOfEducation,semesterEnum,new Person(nameP,birthday,eyeColor,hairColor,nationality));
        return sp;
    }

}
