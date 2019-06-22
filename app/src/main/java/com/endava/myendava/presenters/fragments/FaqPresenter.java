package com.endava.myendava.presenters.fragments;

import com.endava.myendava.adapters.model.FaqItem;
import com.endava.myendava.presenters.BasePresenter;
import com.endava.myendava.utils.MySharedPreferences;
import com.endava.myendava.views.fragments.FaqView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class FaqPresenter extends BasePresenter<FaqView> {

    private MySharedPreferences mSharedPreferences;

    public FaqPresenter(MySharedPreferences mySharedPreferences) {
        mSharedPreferences = mySharedPreferences;
    }

    @Override
    public void viewReady() {
        mViewRef.get().setupRecyclerView(mSharedPreferences.isLoggedInAsGuest());
    }

    @Override
    public void viewGone() {

    }

    public List<FaqItem> getFaqData(InputStream inputStream) {
        String employeesData = inputStreamToString(inputStream);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FaqItem>>() {
        }.getType();
        return gson.fromJson(employeesData, listType);
//        List<FaqItem> list = new ArrayList<>();
//        List<Tag> tags = new ArrayList<>();
//        tags.add(new Tag("Skill", "Soft", null, -1, "ITS", ""));
//        tags.add(new Tag("Skill", "Technical", null, -1, "RxJava", ""));
//        tags.add(new Tag("Skill", "Soft", null, -1, "FrontDesk", ""));
//        for (int i = 0; i < 30; i = i + 2) {
//            list.add(new FaqItem("Cupcake ipsum dolor sit amet sugar plum. Dessert gingerbread toffee sweet roll halvah jelly-o? ", "Chocolate cake topping chocolate bar candy danish lemon drops jelly-o jelly-o. Marshmallow candy cake tiramisu gummi bears apple pie. Jelly-o wafer marshmallow cake chupa chups.", tags));
//            list.add(new FaqItem("Questioning question " + (i + 1), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In velit purus, venenatis in diam quis, interdum iaculis quam. Vestibulum tellus eros, pulvinar in dictum non, porta vehicula leo. Quisque purus dui, convallis nec venenatis eget, placerat eget augue. Vestibulum vitae pretium odio, sed mollis dolor. " + (i + 1), tags));
//        }
//        return list;
    }

//    public List<FaqItem> getGuestsData() {
//        List<FaqItem> list = new ArrayList<>();
//        List<Tag> tags = new ArrayList<>();
//        for (int i = 0; i < 30; i = i + 2) {
//            list.add(new FaqItem("Questioning question " + i, "Answering answer " + i, tags));
//            list.add(new FaqItem("Questioning question " + (i + 1), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In velit purus, venenatis in diam quis, interdum iaculis quam. Vestibulum tellus eros, pulvinar in dictum non, porta vehicula leo. Quisque purus dui, convallis nec venenatis eget, placerat eget augue. Vestibulum vitae pretium odio, sed mollis dolor. " + (i + 1), tags));
//        }
//        return list;
//    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }
}
