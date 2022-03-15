package com.itmo.collection;

import com.itmo.client.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type com.itmo.Dragon.
 */
public class LabWork implements Comparable<LabWork>, Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int weight; //Значение поля должно быть больше 0
    private float minimalpoint; //Значение поля должно быть больше 0
    private HairColor type; //Поле может быть null
    private Difficulty difficulty; //Поле не может быть null
    private Person person; //Поле может быть null

    @Getter @Setter
    private String ownerName;


    @Setter @Getter
    private User user;

    /**
     * Конструктор со всеми не автогенерируемыми полями
     *
     * @param name        - имя дракона
     * @param coordinates - координаты
     * @param weight         - возраст дракона. value = age * wingspan
     * @param minimalpoint    - размах крыла
     * @param type        - тип дракона
     * @param difficulty   - характер дракона
     * @param person      - убийца дракона
     */
    public LabWork(String name, Coordinates coordinates, int weight, float minimalpoint,
                   HairColor type, Difficulty difficulty, Person person){
        this.name = name;
        this.coordinates = coordinates;
        this.weight = weight;
        this.minimalpoint = minimalpoint;
        this.type = type;
        this.difficulty = difficulty;
        this.person = person;
        creationDate = new Date();
    }


    public LabWork(String name, Coordinates coordinates, Date creationDate, int weight, float minimalpoint,
                   HairColor type, Difficulty difficulty, Person person){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.weight = weight;
        this.minimalpoint = minimalpoint;
        this.type = type;
        this.difficulty = difficulty;
        this.person = person;
    }

    /**
     *
     * @param id          the id
     * @param name        the name
     * @param coordinates the coordinates
     * @param weight         the age
     * @param minimalpoint    the wingspan
     * @param type        the type
     * @param difficulty   the character
     * @param person      the killer
     */
    public LabWork(Long id, String name, Coordinates coordinates, int weight, float minimalpoint, HairColor type,
                   Difficulty difficulty, Person person){
        this.name = name;
        this.coordinates = coordinates;
        this.weight = weight;
        this.minimalpoint = minimalpoint;
        this.type = type;
        this.difficulty = difficulty;
        this.person = person;
        creationDate = new Date();
        this.id = id;
    }

    /**
     * Get value float.
     *
     * @return the float
     */
    public float getValue(){
        return this.minimalpoint *this.weight;
    }

    /**
     * Instantiates a new com.itmo.Dragon.
     *
     * @param name        the name
     * @param coordinates the coordinates
     * @param weight         the age
     * @param minimalpoint    the wingspan
     * @param difficulty   the character
     */
    public LabWork(String name, Coordinates coordinates, int weight, float minimalpoint,
                   Difficulty difficulty){
        this.name = name;
        this.coordinates = coordinates;
        this.weight = weight;
        this.minimalpoint = minimalpoint;
        this.difficulty = difficulty;
        creationDate = new Date();
    }

    /**
     * Set killer.
     *
     * @param person the killer
     */
    public void setPerson(Person person){
        this.person = person;
    }

    /**
     * Set type.
     *
     * @param type the type
     */
    public void setType(HairColor type){
        this.type = type;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }
    public String getCreatinoDateInFormat(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets wingspan.
     *
     * @return the wingspan
     */
    public float getMinimalpoint() {
        return minimalpoint;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public HairColor getType() {
        return type;
    }

    /**
     * Gets character.
     *
     * @return the character
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Gets killer.
     *
     * @return the killer
     */
    public Person getPerson() {
        return person;
    }
    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    /**
     * костыль для команды updateById
     *
     * @param id id дракона
     */
    public void setId(long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "owner: " + ownerName + "\n" +
                "id: " + this.id + "\n" +
                "name: " + this.name + "\n" +
                "coordinates: " + coordinates.getX().toString() +
                ", " + coordinates.getY() + "\n" +
                "creationDate: " + this.creationDate.toString() + "\n" +
                "weight: " + this.weight + "\n" +
                "minimal point: " + this.minimalpoint + "\n" +
                "hair color: " + ((type == null) ? "null" : this.type.toString()) + "\n" +
                "difficulty: " + this.difficulty.toString() + "\n" +
                "person: " + ((this.person == null) ? "null" : this.person.toString());
    }

    /**
     * драконы сравниваются по value = age * wingspan
     * @param labWork сравниваемый дракон
     */
    @Override
    public int compareTo(LabWork labWork) {
        return Float.compare(getValue(), labWork.getValue());
    }

    public String getCreationDateInFormat(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }
}