package com.endava.myendava.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Faq {

    @SerializedName("question")
    private String mQuestion;
    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("tags")
    private List<Tag> mTags;

    private boolean isExpanded = false;

    public Faq(String mQuestion, String mAnswer, List<Tag> mTags) {
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
        this.mTags = mTags;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public List<Tag> getTags() {
        return mTags;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
