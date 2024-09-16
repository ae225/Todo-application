package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDAOCollection implements PersonDAO {
    private final Map<Integer, Person> personCollection;

    public PersonDAOCollection() {
        this.personCollection = new HashMap<>();
    }

    @Override
    public Person create(Person person) {
        if (person == null || personCollection.containsKey(person.getId())) {
            return null;
        }
        personCollection.put(person.getId(), person);
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
        return personCollection.put(person.getId(), person);
    }

    @Override
    public boolean deleteById(int id) {
        return personCollection.remove(id) != null;
    }
}
