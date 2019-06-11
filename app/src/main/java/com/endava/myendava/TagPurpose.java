package com.endava.myendava;

import java.io.Serializable;

public class TagPurpose implements Serializable {

    private String purpose;

    public TagPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }
}
