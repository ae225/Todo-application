package se.lexicon.dao;

import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAODatabase implements PersonDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/todoit";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        person.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        Person person = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    person = new Person(rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        String sql = "SELECT * FROM person";
        Collection<Person> persons = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                persons.add(new Person(rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Collection<Person> findByName(String name) {
        String sql = "SELECT * FROM person WHERE first_name LIKE ? OR last_name LIKE ?";
        Collection<Person> persons = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    persons.add(new Person(rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setInt(3, person.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
