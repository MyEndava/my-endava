package com.endava.myendava.presenters.fragments;

import com.endava.myendava.Tag;
import com.endava.myendava.TagGroup;
import com.endava.myendava.TagPurpose;
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
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("ITS", new TagGroup("Soft"), new TagPurpose("Interested")));
        tags.add(new Tag("rxJava", new TagGroup("Technical"), new TagPurpose("Mastering")));
        tags.add(new Tag("FrontDesk", new TagGroup("Soft"), new TagPurpose("Mastering")));
        for (int i = 0; i < 30; i = i + 2) {
            list.add(new FaqItem("Questioning question " + i, "Answering answer " + i, tags));
            list.add(new FaqItem("Questioning question " + (i + 1), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In velit purus, venenatis in diam quis, interdum iaculis quam. Vestibulum tellus eros, pulvinar in dictum non, porta vehicula leo. Quisque purus dui, convallis nec venenatis eget, placerat eget augue. Vestibulum vitae pretium odio, sed mollis dolor. " + (i + 1), tags));
        }
        return list;
    }
}
