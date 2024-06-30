package se.lexicon;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TodoItemTaskTest {

    @Test
    void testTodoItemTaskCreation() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.of(2024, 6, 30), false, creator);
        Person assignee = new Person(2, "Sven", "Andersson", "sven.andersson@example.com");
        TodoItemTask todoItemTask = new TodoItemTask(1, todoItem, assignee);
        assertEquals(1, todoItemTask.getId());
        assertTrue(todoItemTask.isAssigned());
        assertEquals(todoItem, todoItemTask.getTodoItem());
        assertEquals(assignee, todoItemTask.getAssignee());
    }

    @Test
    void testTodoItemCannotBeNull() {
        Person assignee = new Person(2, "Sven", "Andersson", "sven.andersson@example.com");
        assertThrows(IllegalArgumentException.class, () -> new TodoItemTask(1, null, assignee));
    }

    @Test
    void testSetAssignee() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.of(2024, 6, 30), false, creator);
        TodoItemTask todoItemTask = new TodoItemTask(1, todoItem, null);
        assertFalse(todoItemTask.isAssigned());
        Person assignee = new Person(2, "Sven", "Andersson", "sven.andersson@example.com");
        todoItemTask.setAssignee(assignee);
        assertTrue(todoItemTask.isAssigned());
        assertEquals(assignee, todoItemTask.getAssignee());
    }
}
