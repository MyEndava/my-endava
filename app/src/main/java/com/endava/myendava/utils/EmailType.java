package com.endava.myendava.utils;

public enum EmailType {
    OWN_EMAIL("OWN"),
    EMPLOYEE_EMAIL("EMPLOYEE");

    private String type;

    EmailType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
