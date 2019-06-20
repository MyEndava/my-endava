package com.endava.myendava.utils;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.endava.myendava.R;
import com.endava.myendava.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class MockedDataGenerator {

    public List<ProjectModel> getMockedProjectsList(Context context) {
        List<ProjectModel> projectsList = new ArrayList<>();

        ProjectModel bulbasaur = new ProjectModel();
        bulbasaur.setName(context.getResources().getString(R.string.bulbasaur));
        bulbasaur.setDescription(context.getResources().getString(R.string.bulbasaur_description));
        bulbasaur.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.bulbasaur));
        projectsList.add(bulbasaur);

        ProjectModel charmander = new ProjectModel();
        charmander.setName(context.getResources().getString(R.string.charmander));
        charmander.setDescription(context.getResources().getString(R.string.charmander_description));
        charmander.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.charmander));
        projectsList.add(charmander);

        ProjectModel squirtle = new ProjectModel();
        squirtle.setName(context.getResources().getString(R.string.squirtle));
        squirtle.setDescription(context.getResources().getString(R.string.squirtle_description));
        squirtle.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.squirtle));
        projectsList.add(squirtle);

        ProjectModel pikachu = new ProjectModel();
        pikachu.setName(context.getResources().getString(R.string.pikachu));
        pikachu.setDescription(context.getResources().getString(R.string.pikachu_description));
        pikachu.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.pikachu));
        projectsList.add(pikachu);

        return projectsList;
    }


}
