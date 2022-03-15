package com.itmo.collection;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Класс для работы с командами относящимися к коллекции
 */
public class CollectionManager {
    private static Stack<StudyGroup> collection;
    private static Date creationDate;
    private static boolean findId=false;

    public CollectionManager(Stack<StudyGroup> labWorks){
        collection = labWorks;
        creationDate = new Date();
    }

    /**
     * Инициализация коллекции
     */
    public static void initializeStack() {
        if (collection == null) {
            collection = new Stack<StudyGroup>();
            creationDate = new Date();
        }
    }

    public static void removeById(Integer id) throws IOException {
        StudyGroup sM=findById(id);
        if(!findId){
            System.out.println("no such");
        }
        findId=false;
        collection.remove(sM);
        save();

    }
    public static String filterGreater(String option){
        Stack<StudyGroup> spaceMarinesFl = new Stack<>();
        Integer intop = null;

        for (StudyGroup spaceMarine : collection){
            try {
                switch (option.toUpperCase()) {
                    case ("FOURTH") :
                        intop = 4;
                        break;
                    case ("FIFTH") :
                        intop = 5;
                        break;
                    case ("SEVENTH") :
                        intop = 7;
                        break;
                }

                if(intop<spaceMarine.getSemesterEnum().getValue() ){
                    spaceMarinesFl.add(spaceMarine);
                    System.out.println(spaceMarine);}
            }catch (IllegalStateException ex) {
                System.out.println("Unexpected value: " + option);
            }catch (NullPointerException ex){System.out.println("no such");}

        }


        if (spaceMarinesFl.isEmpty()){
            return "Не найдены продукты с таким владельцем";
        }

        return spaceMarinesFl.toString();

    }
    public static void add(StudyGroup studyGroup) throws IOException {
        initializeStack();
        collection.push(studyGroup);
        save();

    }
    public static String show() {

        initializeStack();

        if (collection.size()==0){
            System.out.println("collection still empty");
        }
      // return new StringTool().PrintLabWorkSet(collection);
        return collection.toString();

    }
    public static void exit() {
        System.out.print("exit program");
        System.exit(0);
    }
    public static String info() {
//        System.out.print("the amount of elements "+ collection.size()+ "\n");
//        System.out.print("the type of collection is "+ collection.getClass() +"\n");
//        System.out.println("Дата - "+ creationDate);
        String info = "the date of initialization " + creationDate +
                "\n the amount of elements "+ collection.size()+
                "\n the type of collection is "+  collection.getClass()  +"\n";
        return info;
    }
    public static void save() throws IOException {
        File fff = new File("ScriptFile/file.xml");
        String fd = fff.getAbsolutePath();
    }
    public static void clear() throws IOException {
        File f = new File("ScriptFile/"+"file.xml");
        FileWriter fwOb = new FileWriter(f, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
        collection.clear();
        save();

    }
    public static String reorder() throws IOException {

        Collections.reverse(collection);
        save();
        return collection.toString();
    }
    public static String printAscending() throws IOException {
        StudyGroupComparator compareSpaceMarine = new StudyGroupComparator();
        collection.sort(compareSpaceMarine);
        return collection.toString();

    }
    public static String printDescending() throws IOException {
        StudyGroupComparator compareSpaceMarine = new StudyGroupComparator();
        collection.sort(Collections.reverseOrder(compareSpaceMarine));
        return collection.toString();

    }
    public static void removeLower(StudyGroup studyGroup) throws IOException {
        initializeStack();
        collection.removeIf(entry -> entry.getStudentsCount()<studyGroup.getStudentsCount());
        save();

    }
    public static void updateId(String in, StudyGroup studyGroup) throws IOException {
        Integer id=Integer.valueOf(in);
        StudyGroup spaceMarine;
        Iterator<StudyGroup> iterator=collection.stream().iterator();
        while (iterator.hasNext()) {
            if ((spaceMarine = iterator.next()).getId().equals(id)) {
                collection.remove(spaceMarine);
                //StudyGroup insert = StudyGroupFactory.studyGroupFactory();
                studyGroup.setId(id);
                collection.push(studyGroup);
                break;
            }
        }
        save();

    }

    /**
     * Вспомогательный метод для нахождения элемента коллекции по ID
     * @param id
     * @return
     */
    private static StudyGroup findById(Integer id){
        StudyGroup p=null;
        StudyGroup m;
        Iterator<StudyGroup> iterator=collection.stream().iterator();
        out:while(iterator.hasNext()){
            if((m = iterator.next()).getId().equals(id)){
                p=m;
                findId=true;
                break out;
            }
        }
        return p;
    }

    public static Stack<StudyGroup> getCollection() {
        return collection;
    }
}
