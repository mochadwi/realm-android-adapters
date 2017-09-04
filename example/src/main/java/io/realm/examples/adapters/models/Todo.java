package io.realm.examples.adapters.models;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mochadwi on 9/3/17.
 */

public class Todo extends RealmObject {

    public static final String FIELD_ID = "id";
    private static AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);

    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String due;

    public int getId() {
        return id;
    }

    public String getIdString() {
        return Integer.toString(id);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDue() {
        return due;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDue(String due) {
        this.due = due;
    }

    //  create() & delete() needs to be called inside a transaction.
    static void create(Realm realm) {
        create(realm, false);
    }

    static void create(Realm realm, boolean randomlyInsert) {
        Parent parent = realm.where(Parent.class).findFirst();
        RealmList<Todo> todos = parent.getTodoList();
        Todo todo = realm.createObject(Todo.class, increment() + System.currentTimeMillis());
        if (randomlyInsert && todos.size() > 0) {
            Random rand = new Random();
            todos.listIterator(rand.nextInt(todos.size())).add(todo);
        } else {
            todos.add(todo);
        }
    }

    static void create(Realm realm, Todo item) {
        Parent parent = realm.where(Parent.class).findFirst();
        RealmList<Todo> todos = parent.getTodoList();

        Todo todo = realm.createObject(Todo.class, increment() + System.currentTimeMillis());
//        todo = realm.copyFromRealm(item);
        todo.setTitle(item.getTitle());
        todo.setDescription(item.getDescription());
        todo.setDue(item.getDue());

        todos.add(todo);
    }

    static void update(Realm realm, Todo item) {

    }

    static void delete(Realm realm, long id) {
        Todo todo = realm.where(Todo.class).equalTo(FIELD_ID, id).findFirst();
        // Otherwise it has been deleted already.
        if (todo != null) {
            todo.deleteFromRealm();
        }
    }

    private static int increment() {
        return INTEGER_COUNTER.getAndIncrement();
    }
}
