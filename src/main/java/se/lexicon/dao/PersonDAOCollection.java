package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDAOCollection implements PersonDAO {
    private final Map<Integer, Person> personCollection;
    private final Map<String, Person> emailIndex;

    public PersonDAOCollection() {
        this.personCollection = new HashMap<>();
        this.emailIndex = new HashMap<>();
    }

    @Override
    public Person create(Person person) {
        if (person == null || personCollection.containsKey(person.getId())) {
            return null;
        }
        personCollection.put(person.getId(), person);
        emailIndex.put(person.getEmail(), person);
        return person;
    }

    @Override
    public Person findById(int id) {
        return personCollection.get(id);
    }

    @Override
    public Collection<Person> findByName(String name) {
        List<Person> result = new ArrayList<>();
        for (Person person : personCollection.values()) {
            if (person.getFirstName().equalsIgnoreCase(name) || person.getLastName().equalsIgnoreCase(name)) {
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public Collection<Person> findAll() {
        return new ArrayList<>(personCollection.values());
    }

    @Override
    public Person update(Person person) {
        if (person == null || !personCollection.containsKey(person.getId())) {
            return null;
        }
        Person oldPerson = personCollection.put(person.getId(), person);
        emailIndex.remove(oldPerson.getEmail());
        emailIndex.put(person.getEmail(), person);
        return person;
    }

    @Override
    public boolean deleteById(int id) {
        Person removedPerson = personCollection.remove(id);
        if (removedPerson != null) {
            emailIndex.remove(removedPerson.getEmail());
            return true;
        }
        return false;
    }
}
