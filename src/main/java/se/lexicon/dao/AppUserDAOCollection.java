package se.lexicon.dao;

import se.lexicon.model.AppUser;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AppUserDAOCollection implements AppUserDAO {
    private Map<String, AppUser> appUserCollection;

    public AppUserDAOCollection() {
        this.appUserCollection = new HashMap<>();
    }

    @Override
    public AppUser persist(AppUser appUser) {
        if (appUser == null || appUserCollection.containsKey(appUser.getUsername())) {
            return null;
        }
        appUserCollection.put(appUser.getUsername(), appUser);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserCollection.get(username);
    }

    @Override
    public Collection<AppUser> findAll() {
        return appUserCollection.values();
    }

    @Override
    public void remove(String username) {
        appUserCollection.remove(username);
    }
}
