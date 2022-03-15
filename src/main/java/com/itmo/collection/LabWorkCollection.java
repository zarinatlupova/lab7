package com.itmo.collection;

import com.itmo.Exceptions.NotYourPropertyException;
import com.itmo.client.User;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class LabWorkCollection implements Serializable {

    private Set<StudyGroup> labWorks;
    private final Date creationDate;


    public LabWorkCollection(Set<StudyGroup> labWorks){
        this.labWorks = Collections.synchronizedSet(labWorks);
        creationDate = new Date();
    }

    public String show(){
        StringBuilder builder = new StringBuilder();
        if(labWorks.size() == 0) return "Collection is empty. Please add some...";
        TreeSet<StudyGroup> treeSet = new TreeSet<>(labWorks);
        treeSet.forEach(d ->{
                builder.append("----------\n").append(d.toString()).append("\n");
        });
        return builder.toString();
    }
    public String clear(User user){
        Set<StudyGroup> set = filterOwnDragon(user);
        for(StudyGroup d : set){
            try {
                remove(d, user);
            } catch (NotYourPropertyException ignore) {
            }
        }
        return "Your LabWorks are cleared";
    }

    private Set<StudyGroup> filterOwnDragon(User user) {
        return Collections.synchronizedSet(labWorks).stream()
                .filter(d -> d.getOwnerName().equals(user.getName())).collect(Collectors.toSet());
    }

    public String add(StudyGroup labWork){
        Set<Long> setIds = Collections.synchronizedSet(labWorks).stream().map(StudyGroup::getId).collect(Collectors.toSet());
        //генерация id
        for(long i = 0; i<Long.MAX_VALUE;i++){
            if(!setIds.contains(i)){
                labWork.setId(i);
                this.labWorks.add(labWork);
                return "LabWOrk added ";
            }
        }
        return "Дракон не добавлен потому что не удалось сгенерировать для него id.";
    }
    public String addIfMax(StudyGroup labWork){
        if(isMax(labWork)){
            return add(labWork);
        }
        return "Не добавлен, т.к. не больший";
    }
    public boolean isMax(StudyGroup labWork){
        return (findMaxValue()< labWork.getValue());
    }
    public boolean isMin(StudyGroup labWork){
        return Collections.synchronizedSet(labWorks).stream().
                noneMatch(d -> (d.getValue()< labWork.getValue()));
    }
    public String addIfMin(StudyGroup labWork){
        if(isMin(labWork)){
            return "Не добавлен, т.к. не меньший";
        }else{
            return add(labWork);
        }
    }

    /**
     * удалить из коллекции все элементы, меньшие, чем заданный
     * @param labWork дракон с которым будут сравниваться все элементы коллекции
     */
    public String removeLower(StudyGroup labWork, User user) {
        StringBuilder builder = new StringBuilder();
        filterOwnDragon(user).stream().filter(d -> d.getValue() < labWork.getValue())
                .forEach(dr -> {
                    builder.append("LabWork with that id is deleted ").append(dr.getId()).append("\n");
                    try {
                        remove(dr, user);
                    } catch (NotYourPropertyException e) {
                        e.printStackTrace();
                    }
                });
        if(builder.length()==0) return "Нет драконов меньше чем заданный";
        return builder.toString();
    }
    /**
     * фильтрует коллекцию, оставляет только тех, чьи имена начинаются с name
     * @param name является началом имени драконов которых нужно получить
     * @return сет драконов в отфильтрованном порядке
     */
    public Set<StudyGroup> filterStartsWithName(String name){
        return Collections.synchronizedSet(labWorks).stream()
                .filter(d -> d.getName().trim().startsWith(name)).collect(Collectors.toSet());
    }

    /**
     * простой метод для вывода коллекции в обратном порядке
     */
    public String printDescending(){
        StringBuilder builder = new StringBuilder();
        Collections.synchronizedSet(labWorks).stream().sorted((o1, o2) -> (int) (o2.getValue()-o1.getValue()))
                .forEach(d -> builder.append(d.getName())
                        .append(" with value ").append(d.getValue()).append("\n"));
        return builder.toString();
    }
    public boolean removeById(long id, User user) throws NotYourPropertyException{
        StudyGroup labWork = findById(id);
        if(labWork != null){
            remove(labWork, user);
            return true;
        }
        return false;
    }

    public void remove(StudyGroup d, User user) throws NotYourPropertyException {
        if(!user.getName().equals(d.getOwnerName())){
            throw new NotYourPropertyException(d.getOwnerName());
        }
        this.labWorks.remove(d);
    }

    public float findMaxValue(){
        return (labWorks.size()==0 ? 0 :
                Collections.synchronizedSet(labWorks).stream()
                .max(Comparator.comparing(StudyGroup::getValue))
                        .get().getValue());
    }

    public StudyGroup findById(long id) {
        return labWorks.stream().filter(d -> d.getId() == id).findAny().orElse(null);
    }

    public String printFieldAscendingWingspan(){
        StringBuilder builder = new StringBuilder();
        labWorks.stream().map(StudyGroup::getValue)
                .sorted()
                .forEach(v -> builder.append(v).append(" "));
        return builder.toString();
    }

    public String getCollectionInfo(){
        return "Тип коллекции: com.itmo.LabWork\nДата инициализации: " + creationDate +
        "\nКоличество элементов: " + labWorks.size();
    }

    public Set<StudyGroup> getDragons() {
        return labWorks;
    }
}