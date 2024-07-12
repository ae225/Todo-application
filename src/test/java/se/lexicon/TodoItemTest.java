package se.lexicon;

import org.junit.jupiter.api.Test;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void testTodoItemCreation() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.of(2024, 6, 30), false, creator);
        assertEquals(1, todoItem.getId());
        assertEquals("Test Task", todoItem.getTitle());
        assertEquals("Description", todoItem.getTaskDescription());
        assertEquals(LocalDate.of(2024, 6, 30), todoItem.getDeadLine());
        assertFalse(todoItem.isDone());
        assertEquals(creator, todoItem.getCreator());
    }

    @Test
    void testTitleCannotBeNull() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        assertThrows(IllegalArgumentException.class, () -> new TodoItem(1, null, "Description", LocalDate.of(2024, 6, 30), false, creator));
    }

    @Test
    void testSetTitle() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.of(2024, 6, 30), false, creator);
        todoItem.setTitle("New Title");
        assertEquals("New Title", todoItem.getTitle());
    }

    @Test
    void testSetCreator() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.of(2024, 6, 30), false, creator);
        Person newCreator = new Person(2, "Sven", "Andersson", "sven.andersson@example.com");
        todoItem.setCreator(newCreator);
        assertEquals(newCreator, todoItem.getCreator());
    }

    @Test
    void testIsOverdue() {
        Person creator = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        TodoItem todoItem = new TodoItem(1, "Test Task", "Description", LocalDate.now().minusDays(1), false, creator);
        assertTrue(todoItem.isOverdue());
    }
}
