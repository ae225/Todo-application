package se.lexicon;

import org.junit.jupiter.api.Test;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testPersonCreation() {
        Person person = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        assertEquals(1, person.getId());
        assertEquals("Erik", person.getFirstName());
        assertEquals("Jansson", person.getLastName());
        assertEquals("erik.jansson@example.com", person.getEmail());
    }

    @Test
    void testNullFirstNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Person(1, null, "Jansson", "erik.jansson@example.com"));
    }

    @Test
    void testSetFirstName() {
        Person person = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        person.setFirstName("Sven");
        assertEquals("Sven", person.getFirstName());
    }

    @Test
    void testSetCredentials() {
        Person person = new Person(1, "Erik", "Jansson", "erik.jansson@example.com");
        AppUser appUser = new AppUser("username", "password", AppRole.ROLE_APP_USER);
        person.setCredentials(appUser);
        assertEquals(appUser, person.getCredentials());
    }
}
