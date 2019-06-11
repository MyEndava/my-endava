package com.endava.myendava.adapters.model;

import com.endava.myendava.Tag;

import java.util.List;

public class FaqItem {

    private String mQuestion;
    private String mAnswer;
    private List<Tag> mTags;

    private boolean isExpanded = false;

    public FaqItem(String mQuestion, String mAnswer, List<Tag> mTags) {
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
