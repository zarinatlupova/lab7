package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.collection.LabWork;

import java.util.Set;

/**
 * The type Filter starts with name command.
 */
public class FilterStartsWithNameCommand extends Command {

    /**
     * Instantiates a new Command.
     */
    public FilterStartsWithNameCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public String execute(Application application, User user) {
        Set<LabWork> res =
                application.getCollection().filterStartsWithName(args[0]);
        if(res.size()!=0){
            StringBuilder builder = new StringBuilder("Элементов в коллекции имена которых начинаются" +
                    "со строки " + args[0] + ": " + res.size() +"\n");
            res.stream().map(LabWork::getName).forEach(d ->builder.append(d).append("\n"));
            return builder.toString();
        }else{
            return "Драконов с именами начинающихся с " + args[0] + " нет.";
        }
    }
    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
