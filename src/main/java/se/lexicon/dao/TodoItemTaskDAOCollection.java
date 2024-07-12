package se.lexicon.dao;

import se.lexicon.model.TodoItemTask;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {
    private Map<Integer, TodoItemTask> todoItemTaskCollection;

    public TodoItemTaskDAOCollection() {
        this.todoItemTaskCollection = new HashMap<>();
    }

    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        if (todoItemTask == null || todoItemTaskCollection.containsKey(todoItemTask.getId())) {
            return null;
        }
        todoItemTaskCollection.put(todoItemTask.getId(), todoItemTask);
        return todoItemTask;
    }

    @Override
    public TodoItemTask findById(int id) {
        return todoItemTaskCollection.get(id);
    }

    @Override
    public Collection<TodoItemTask> findAll() {
        return new ArrayList<>(todoItemTaskCollection.values());
    }

    @Override
    public Collection<TodoItemTask> findByAssignedStatus(boolean status) {
        return todoItemTaskCollection.values().stream()
                .filter(todoItemTask -> todoItemTask.isAssigned() == status)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItemTask> findByPersonId(int personId) {
        return todoItemTaskCollection.values().stream()
                .filter(todoItemTask -> todoItemTask.getAssignee() != null && todoItemTask.getAssignee().getId() == personId)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(int id) {
        todoItemTaskCollection.remove(id);
    }
}
