package com.itmo.utils;

import com.itmo.client.User;
import com.itmo.collection.*;
//import com.itmo.server.url.SshConnection;
import com.itmo.server.url.UrlGetterDirectly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DatabaseManager implements MyCRUD  {

    private static final String DB_URL = new UrlGetterDirectly().getUrl() ;
    private static String USER;
    private static String PASS;
    private static final String COLLECTION_TABLE = "study_group";
    private static final String FILE_WITH_ACCOUNT = "account";
    private static final String USERS_TABLE = "users";
    private static final String pepper = "1@#$&^%$)3";


    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
              BufferedReader reader = new BufferedReader(fileReader)) {
            USER = "s288757";
            PASS = "hiphopforever1";
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PassEncoder passEncoder;

    public DatabaseManager(String dbUrl, String user, String pass) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            passEncoder = new PassEncoder();
            System.out.println("БД инициализирована: " + dbUrl);
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }

    public DatabaseManager(String address, int port, String dbName, String user, String pass) {
        this("jdbc:postgresql://" + address + ":" + port + "/" + dbName, user, pass);
    }

    public DatabaseManager() {
        this(DB_URL, USER, PASS);
    }

    @Override
    public boolean insertDragon(StudyGroup d)  {
        try {
            //String drtype = null;

//            if (d.getType() != null) drtype = d.getType().name();
            String date =d.getCreationDateInFormat();
            String state = "INSERT INTO " + COLLECTION_TABLE +
                    "(group_name," +
                    " date," +
                    " stud_count," +
                    " form," +
                    " semester, " +
                    "owner)\n" +
                    "VALUES (?, '" + date + "', ?, ?::form, ?::semester, ?)";
            PreparedStatement dragonItself = connection.prepareStatement(state);

            dragonItself.setString(1, d.getName());
            dragonItself.setLong(2, d.getStudentsCount());
            dragonItself.setString(3, d.getFormOfEducation().name());
            dragonItself.setString(4, d.getSemesterEnum().name());
            dragonItself.setString(5, d.getOwnerName());
            dragonItself.executeUpdate();

            PreparedStatement dragonCoords = connection.prepareStatement(
                    "INSERT INTO coordinates(labwork_id,x,y)" +
                            "VALUES (currval('generate_id'), ?, ?)");
            dragonCoords.setInt(1, d.getCoordinates().getX());
            dragonCoords.setInt(2, d.getCoordinates().getY());
            dragonCoords.executeUpdate();

            Person person = d.getGroupAdmin();
            if (person != null) {
                PreparedStatement dragonKiller =
                        connection.prepareStatement(
                                "INSERT INTO authors(labwork_id, author_name, birthday, color, country, hair_color)" +
                                        "VALUES (currval('generate_id'), ?,'"
                                        +person.getBirthdayInFormat() + "', ?::color, ?::country, ?::color)"
                        );
                dragonKiller.setString(1, person.getName());
                dragonKiller.setString(2, person.getEyeColor().name());
                dragonKiller.setString(3, person.getNationality().name());
                dragonKiller.setString(4, person.getHairColor().name());
                dragonKiller.executeUpdate();


            }
            return true;
        }catch (SQLException e){
            System.out.println("Ошибка при добавлении элемента в БД.");
            e.printStackTrace();
            return false;
        }
    }




    public Stack<StudyGroup> getCollectionFromDatabase() throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT * FROM "+ COLLECTION_TABLE + " ds\n" +
                                "    inner join coordinates dc\n" +
                                "on ds.id = dc.labwork_id\n" +
                                "    left outer join authors dk\n" +
                                "    on dk.labwork_id = ds.id\n"

                );
        ResultSet resultSet = statement.executeQuery();
        Stack<StudyGroup> labWorks = new Stack<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("group_name");
            Date date = resultSet.getDate("date");
            int stud_count = resultSet.getInt("stud_count");
            String wingspan = resultSet.getString("form");
            String strType = resultSet.getString("semester");

            Color type = Enum.valueOf(Color.class, strType);
            FormOfEducation formOfEducation = Enum.valueOf(FormOfEducation.class, strType);
            Semester sem = Enum.valueOf(Semester.class, strType);
//            Difficulty character = Enum.valueOf(Difficulty.class, resultSet.
//                    getString("difficulty"));
            String ownerName = resultSet.getString("owner");
            Coordinates coordinates = new Coordinates(resultSet.getInt("x"), resultSet.getInt("y"));
            Person person = null;
            if(resultSet.getString("author_name")!=null){
                person = new Person(
                        resultSet.getString("author_name"),
                        DateTimeAdapter.convertToLocalDateViaInstant(resultSet.getDate("birthday")),
                        Enum.valueOf(com.itmo.collection.Color.class, resultSet.getString("color")),
                        Enum.valueOf(com.itmo.collection.Color.class, resultSet.getString("hair_color")),
                        Enum.valueOf(Country.class, resultSet.getString("country"))
                );
            }
            StudyGroup labWork = new StudyGroup(name, coordinates, stud_count, formOfEducation, sem, person);

                labWork.setOwnerName(ownerName);
            labWork.setId(id);
            User user = new User(ownerName);
            labWork.setUser(user);
            labWorks.add(labWork);
        }
        return labWorks;
    }


    @Override
    public boolean deleteDragonById(long id) {
        String sqlRequest =
                "DELETE FROM " + COLLECTION_TABLE + " WHERE id=" + id;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

//    public long getIdOfDragon(LabWork d){
//        String sqlRequest =
//                "select id FROM " + COLLECTION_TABLE +
//                        " WHERE owner=? " +
//                        "and dragon_name=?" +
//                        " and wingspan=?" +
//                        " and dragon_character=?::dragon_character" +
//                        " and age=?" +
//                        " and reg_date='"; //+ d.getCreationDateInFormat() + "'";
//
//        try {
//            PreparedStatement statement = connection.prepareStatement(sqlRequest);
//            statement.setString(1, d.getOwnerName());
//            statement.setString(2, d.getName());
//            statement.setFloat(3, d.getMinimalpoint());
//            statement.setString(4, d.getDifficulty().name());
//            statement.setInt(5, d.getWeight());
//            ResultSet set = statement.executeQuery();
//            if (set.next()){
//                return set.getInt("id");
//            }
//            return 0;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return 0;
//        }
//    }

    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ?");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString("salt");
            String hash = passEncoder.getHash(user.getHashPass() + salt, "1@#$&^%$)3");
            statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ? " +
                    "and hashpass = ? and salt=?");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public void insertUser(String login, String passHash){
        try {
            String salt = new SimplePasswordGenerator(true, true, true, true)
                    .generate(10,10);
            String hash = passEncoder.getHash(passHash + salt, pepper);
            PreparedStatement statement = connection.prepareStatement("insert into " +
                    USERS_TABLE + " (login, hashpass, salt)" + " VALUES ( '"+login+"', '" + hash +"', '" + salt +"' )");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertUser(User user){
        insertUser(user.getName(), user.getHashPass());
    }

    //ищем пользователя только по имени
    public boolean containsUserName(String login) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}