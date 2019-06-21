package com.endava.myendava.utils;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.endava.myendava.R;
import com.endava.myendava.Tag;
import com.endava.myendava.TagGroup;
import com.endava.myendava.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class MockedProjectsGenerator {

    public List<ProjectModel> getMockedProjectsList(Context context) {
        List<ProjectModel> projectsList = new ArrayList<>();

        ProjectModel homeAssistant = new ProjectModel();
        homeAssistant.setName("Home Assistant");
        homeAssistant.setDescription(context.getResources().getString(R.string.lorem_ipsum));
        homeAssistant.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.home_assistant));
        List<Tag> tags2 = new ArrayList<>();
        tags2.add(new Tag("ITS", new TagGroup("Skill", "Soft")));
        tags2.add(new Tag("RxJava", new TagGroup("Skill", "Technical")));
        tags2.add(new Tag("FrontDesk", new TagGroup("Skill", "Soft")));
        homeAssistant.setTags(tags2);
        projectsList.add(homeAssistant);

        ProjectModel superSdk = new ProjectModel();
        superSdk.setName("Super SDK");
        superSdk.setDescription(context.getResources().getString(R.string.lorem_ipsum));
        superSdk.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.super_sdk));
        List<Tag> tags3 = new ArrayList<>();
        tags3.add(new Tag("ITS", new TagGroup("Skill", "Soft")));
        tags3.add(new Tag("RxJava", new TagGroup("Skill", "Technical")));
        tags3.add(new Tag("FrontDesk", new TagGroup("Skill", "Soft")));
        superSdk.setTags(tags3);
        projectsList.add(superSdk);

        ProjectModel bestConnectivity = new ProjectModel();
        bestConnectivity.setName("Best Connectivity");
        bestConnectivity.setDescription(context.getResources().getString(R.string.lorem_ipsum));
        bestConnectivity.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.best_connectivity));
        List<Tag> tags4 = new ArrayList<>();
        tags4.add(new Tag("ITS", new TagGroup("Skill", "Soft")));
        tags4.add(new Tag("RxJava", new TagGroup("Skill", "Technical")));
        tags4.add(new Tag("FrontDesk", new TagGroup("Skill", "Soft")));
        bestConnectivity.setTags(tags4);
        projectsList.add(bestConnectivity);

        ProjectModel smartPay = new ProjectModel();
        smartPay.setName("Smart Pay");
        smartPay.setDescription(context.getResources().getString(R.string.lorem_ipsum));
        smartPay.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.smart_pay));
        List<Tag> tags1 = new ArrayList<>();
        tags1.add(new Tag("ITS", new TagGroup("Skill", "Soft")));
        tags1.add(new Tag("RxJava", new TagGroup("Skill", "Technical")));
        tags1.add(new Tag("FrontDesk", new TagGroup("Skill", "Soft")));
        smartPay.setTags(tags1);
        projectsList.add(smartPay);

        return projectsList;
    }


}
