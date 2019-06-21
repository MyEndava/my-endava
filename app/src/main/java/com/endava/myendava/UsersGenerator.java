package com.endava.myendava;

import java.util.ArrayList;
import java.util.List;

public class UsersGenerator {

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User user;
        user = new User("Alex", "Graur", "Endava",
                "alex.graur@endava.com", "Senior Android Developer", "");
        users.add(user);
        user = new User("Gabriel", "Blaj", "Endava",
                "gabriel.blaj@endava.com", "Junior Android Developer", "");
        users.add(user);
        user = new User("Marius", "Olenici", "Endava",
                "marius.olenici@endava.com", "Android Developer", "");
        users.add(user);
        user = new User("Mihai", "Rosu", "Endava",
                "mihai.rosu@endava.com", "Android Developer", "");
        users.add(user);
        user = new User("Paula", "Suciu", "Endava",
                "alex.graur@endava.com", "Tester", "");
        users.add(user);
        user = new User("Simina", "Popescu", "Endava",
                "alex.graur@endava.com", "iOS Developer", "");
        users.add(user);
        return users;
    }
}
