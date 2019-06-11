package com.endava.myendava.presenters.fragments;

import com.endava.myendava.adapters.model.FaqItem;
import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.views.fragments.FaqView;

import java.util.ArrayList;
import java.util.List;

public class FaqPresenter extends BasePresenter<FaqView> {

    @Override
    public void viewReady() {

    }

    public List<FaqItem> getMockData() {
        List<FaqItem> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new FaqItem("Questioning question " + i, "Answering answer " + i));
        }
        return list;
    }
}
