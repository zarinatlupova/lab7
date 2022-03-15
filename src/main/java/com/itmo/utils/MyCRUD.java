package com.itmo.utils;

import com.itmo.collection.LabWork;
import com.itmo.collection.StudyGroup;

import java.sql.SQLException;
import java.util.Set;
import java.util.Stack;

public interface MyCRUD {
    boolean insertDragon(StudyGroup d);
    void insertUser(String login, String passHash);
    Stack<StudyGroup> getCollectionFromDatabase() throws SQLException;
    boolean deleteDragonById(long id);
}
