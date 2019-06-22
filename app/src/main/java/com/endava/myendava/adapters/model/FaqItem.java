package com.endava.myendava.adapters.model;

import com.endava.myendava.Tag;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqItem {

    @SerializedName("faq_question")
    private String mQuestion;
    @SerializedName("faq_answer")
    private String mAnswer;
    @SerializedName("faq_tags")
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
