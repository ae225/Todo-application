package se.lexicon.dao;

import se.lexicon.model.Person;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PersonDAOCollection implements PersonDAO {
    private Map<Integer, Person> personCollection;
    private Map<String, Person> emailIndex;

    public PersonDAOCollection() {
        this.personCollection = new HashMap<>();
        this.emailIndex = new HashMap<>();
    }

    @Override
    public Person persist(Person person) {
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
    public Person findByEmail(String email) {
        return emailIndex.get(email);
    }

    @Override
    public Collection<Person> findAll() {
        return personCollection.values();
    }

    @Override
    public void remove(int id) {
        Person removedPerson = personCollection.remove(id);
        if (removedPerson != null) {
            emailIndex.remove(removedPerson.getEmail());
        }
    }
}
