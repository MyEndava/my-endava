package com.endava.myendava;

import java.io.Serializable;

public class TagGroup implements Serializable {

    private String name;

    public TagGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
