package com.endava.myendava;

import android.widget.SearchView;

import java.io.Serializable;

public class TagGroup implements Serializable {

    private String name;
    private String specialization;

    public TagGroup(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}
