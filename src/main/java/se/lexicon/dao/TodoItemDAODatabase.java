package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItemDAODatabase implements TodoItemDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/todoit";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private final PersonDAO personDAO;

    public TodoItemDAODatabase(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public TodoItem create(TodoItem todo) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setDate(3, todo.getDeadline() != null ? Date.valueOf(todo.getDeadline()) : null);
            ps.setBoolean(4, todo.isDone());
            if (todo.getAssignee() != null) {
                ps.setInt(5, todo.getAssignee().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        todo.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    private TodoItem buildTodoItem(ResultSet rs) throws SQLException {
        Person assignee = null;
        int assigneeId = rs.getInt("assignee_id");
        if (!rs.wasNull()) {
            assignee = personDAO.findById(assigneeId);
        }
        return new TodoItem(
                rs.getInt("todo_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("deadline") != null ? rs.getDate("deadline").toLocalDate() : null,
                rs.getBoolean("done"),
                assignee
        );
    }

    @Override
    public TodoItem findById(int id) {
        String sql = "SELECT * FROM todo_item WHERE todo_id = ?";
        TodoItem todo = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    todo = buildTodoItem(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public Collection<TodoItem> findAll() {
        String sql = "SELECT * FROM todo_item";
        Collection<TodoItem> todos = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                todos.add(buildTodoItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean done) {
        String sql = "SELECT * FROM todo_item WHERE done = ?";
        Collection<TodoItem> todos = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, done);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todos.add(buildTodoItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int assigneeId) {
        String sql = "SELECT * FROM todo_item WHERE assignee_id = ?";
        Collection<TodoItem> todos = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, assigneeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todos.add(buildTodoItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person assignee) {
        return findByAssignee(assignee.getId());
    }

    @Override
    public Collection<TodoItem> findByUnassignedTodoItems() {
        String sql = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        Collection<TodoItem> todos = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                todos.add(buildTodoItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public TodoItem update(TodoItem todo) {
        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setDate(3, todo.getDeadline() != null ? Date.valueOf(todo.getDeadline()) : null);
            ps.setBoolean(4, todo.isDone());
            if (todo.getAssignee() != null) {
                ps.setInt(5, todo.getAssignee().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setInt(6, todo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
