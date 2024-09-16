package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements TodoItemDAO {
    private final Map<Integer, TodoItem> todoItemCollection;

    public TodoItemDAOCollection() {
        this.todoItemCollection = new HashMap<>();
    }

    @Override
    public TodoItem create(TodoItem todoItem) {
        if (todoItem == null || todoItemCollection.containsKey(todoItem.getId())) {
            return null;
        }
        todoItemCollection.put(todoItem.getId(), todoItem);
        return todoItem;
    }

    @Override
    public TodoItem findById(int id) {
        return todoItemCollection.get(id);
    }

    @Override
    public Collection<TodoItem> findAll() {
        return todoItemCollection.values();
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean done) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.isDone() == done)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByAssignee(int personId) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getAssignee() != null && todoItem.getAssignee().getId() == personId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person person) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> person.equals(todoItem.getAssignee()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByUnassignedTodoItems() {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getAssignee() == null)
                .collect(Collectors.toList());
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        if (todoItem == null || !todoItemCollection.containsKey(todoItem.getId())) {
            return null;
        }
        return todoItemCollection.put(todoItem.getId(), todoItem);
    }

    @Override
    public boolean deleteById(int id) {
        return todoItemCollection.remove(id) != null;
    }
}
