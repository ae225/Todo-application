package se.lexicon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List<Person> persons = new ArrayList<>();
    private static List<TodoItem> todoItems = new ArrayList<>();
    private static List<TodoItemTask> todoItemTasks = new ArrayList<>();
    private static int personIdCounter = 1;
    private static int todoItemIdCounter = 1;
    private static int todoItemTaskIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Todo Application Menu:");
            System.out.println("1. Add Person");
            System.out.println("2. Add Todo Item");
            System.out.println("3. Assign Todo Item to Person");
            System.out.println("4. View All Todo Items");
            System.out.println("5. View All Todo Item Tasks");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPerson(scanner);
                    break;
                case 2:
                    addTodoItem(scanner);
                    break;
                case 3:
                    assignTodoItem(scanner);
                    break;
                case 4:
                    viewAllTodoItems();
                    break;
                case 5:
                    viewAllTodoItemTasks();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addPerson(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Person person = new Person(personIdCounter++, firstName, lastName, email);
        persons.add(person);
        System.out.println("Person added: " + person.getSummary());
    }

    private static void addTodoItem(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        String deadlineStr = scanner.nextLine();
        LocalDate deadline = LocalDate.parse(deadlineStr);

        System.out.println("Choose creator by ID:");
        for (Person person : persons) {
            System.out.println(person.getId() + ": " + person.getSummary());
        }
        int creatorId = scanner.nextInt();
        scanner.nextLine();

        Person creator = findPersonById(creatorId);
        if (creator == null) {
            System.out.println("Invalid creator ID.");
            return;
        }

        TodoItem todoItem = new TodoItem(todoItemIdCounter++, title, description, deadline, false, creator);
        todoItems.add(todoItem);
        System.out.println("Todo item added: " + todoItem.getSummary());
    }

    private static void assignTodoItem(Scanner scanner) {
        System.out.println("Choose todo item by ID:");
        for (TodoItem todoItem : todoItems) {
            System.out.println(todoItem.getId() + ": " + todoItem.getSummary());
        }
        int todoItemId = scanner.nextInt();
        scanner.nextLine();

        TodoItem todoItem = findTodoItemById(todoItemId);
        if (todoItem == null) {
            System.out.println("Invalid todo item ID.");
            return;
        }

        System.out.println("Choose assignee by ID:");
        for (Person person : persons) {
            System.out.println(person.getId() + ": " + person.getSummary());
        }
        int assigneeId = scanner.nextInt();
        scanner.nextLine();

        Person assignee = findPersonById(assigneeId);
        if (assignee == null) {
            System.out.println("Invalid assignee ID.");
            return;
        }

        TodoItemTask todoItemTask = new TodoItemTask(todoItemTaskIdCounter++, todoItem, assignee);
        todoItemTasks.add(todoItemTask);
        System.out.println("Todo item task assigned: " + todoItemTask.getSummary());
    }

    private static void viewAllTodoItems() {
        if (todoItems.isEmpty()) {
            System.out.println("No todo items available.");
        } else {
            for (TodoItem todoItem : todoItems) {
                System.out.println(todoItem.getSummary());
            }
        }
    }

    private static void viewAllTodoItemTasks() {
        if (todoItemTasks.isEmpty()) {
            System.out.println("No todo item tasks available.");
        } else {
            for (TodoItemTask todoItemTask : todoItemTasks) {
                System.out.println(todoItemTask.getSummary());
            }
        }
    }

    private static Person findPersonById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    private static TodoItem findTodoItemById(int id) {
        for (TodoItem todoItem : todoItems) {
            if (todoItem.getId() == id) {
                return todoItem;
            }
        }
        return null;
    }
}
