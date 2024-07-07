package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void testAppUserCreation() {
        AppUser appUser = new AppUser("username", "password", AppRole.ROLE_APP_USER);
        assertEquals("username", appUser.getUsername());
        assertEquals("password", appUser.getPassword());
        assertEquals(AppRole.ROLE_APP_USER, appUser.getRole());
    }

    @Test
    void testUsernameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new AppUser(null, "password", AppRole.ROLE_APP_USER));
    }

    @Test
    void testPasswordCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new AppUser("username", null, AppRole.ROLE_APP_USER));
    }

    @Test
    void testRoleCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new AppUser("username", "password", null));
    }

    @Test
    void testSetUsername() {
        AppUser appUser = new AppUser("username", "password", AppRole.ROLE_APP_USER);
        appUser.setUsername("newUsername");
        assertEquals("newUsername", appUser.getUsername());
    }

    @Test
    void testSetPassword() {
        AppUser appUser = new AppUser("username", "password", AppRole.ROLE_APP_USER);
        appUser.setPassword("newPassword");
        assertEquals("newPassword", appUser.getPassword());
    }

    @Test
    void testSetRole() {
        AppUser appUser = new AppUser("username", "password", AppRole.ROLE_APP_USER);
        appUser.setRole(AppRole.ROLE_APP_ADMIN);
        assertEquals(AppRole.ROLE_APP_ADMIN, appUser.getRole());
    }
}
