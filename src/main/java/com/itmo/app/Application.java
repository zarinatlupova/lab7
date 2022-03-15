package com.itmo.app;

import com.itmo.collection.CollectionManager;
import com.itmo.collection.LabWorkCollection;
import com.itmo.server.ActiveUsersHandler;
import com.itmo.utils.DatabaseManager;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

public class Application implements Serializable {
    @Setter
    @Getter

    private CollectionManager collection;
    private Date date;
    public DatabaseManager manager;

    public ActiveUsersHandler activeUsers;

    public Application() throws SQLException {
        manager = new DatabaseManager();
        collection = new CollectionManager(manager.getCollectionFromDatabase());
        date = new Date();
        activeUsers = ActiveUsersHandler.getInstance();
    }



    public void syncWithDB(){
        try{
            this.collection = new CollectionManager(manager.getCollectionFromDatabase());

        }catch (SQLException e){
            System.out.println("ошибка в БД"+ e.getSQLState());
            e.printStackTrace();
        }
    }
    public Application(CollectionManager collection) {
        this.collection = collection;
    }

}
