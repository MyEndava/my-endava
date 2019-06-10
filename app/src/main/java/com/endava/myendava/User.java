package com.endava.myendava;

public class User {

    private String name;
    private String grade;
    private int photoId;

    public User(String name, String grade, int photoId) {
        this.name = name;
        this.grade = grade;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public int getPhotoId() {
        return photoId;
    }
}
