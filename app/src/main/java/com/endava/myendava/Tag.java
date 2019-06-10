package com.endava.myendava;

import java.io.Serializable;

public class Tag implements Serializable {

    private String title;

    public Tag(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
