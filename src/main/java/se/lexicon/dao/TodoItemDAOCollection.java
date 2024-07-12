package se.lexicon.dao;

import se.lexicon.model.TodoItem;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements TodoItemDAO {
    private Map<Integer, TodoItem> todoItemCollection;

    public TodoItemDAOCollection() {
        this.todoItemCollection = new HashMap<>();
    }

    @Override
    public TodoItem persist(TodoItem todoItem) {
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
    public Collection<TodoItem> findAllByDoneStatus(boolean done) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.isDone() == done)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByTitleContains(String title) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByPersonId(int personId) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getCreator().getId() == personId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByDeadlineBefore(LocalDate date) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getDeadLine().isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> findByDeadlineAfter(LocalDate date) {
        return todoItemCollection.values().stream()
                .filter(todoItem -> todoItem.getDeadLine().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(int id) {
        todoItemCollection.remove(id);
    }
}
