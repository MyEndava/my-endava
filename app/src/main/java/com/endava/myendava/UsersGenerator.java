package com.endava.myendava;

import java.util.ArrayList;
import java.util.List;

public class UsersGenerator {

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User user;
        user = new User("Alex Graur", "Senior Android Developer", R.drawable.richard_madden);
        users.add(user);
        user = new User("Gabriel Blaj", "Junior Android Developer", R.drawable.pedro_pascal);
        users.add(user);
        user = new User("Marius Olenici", "Android Developer", R.drawable.kit_harrington);
        users.add(user);
        user = new User("Mihai Rosu", "Android Developer", R.drawable.aiden_gillen);
        users.add(user);
        user = new User("Paula Suciu", "Tester", R.drawable.sophie_turner);
        users.add(user);
        user = new User("Simina Popescu", "iOS Developer", R.drawable.emilia_clarke);
        users.add(user);
        return users;
    }
}
