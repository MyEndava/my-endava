package com.endava.myendava;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.endava.myendava.activities.ProfileActivity;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    public static final String ARG_TAG = "arg_tag_name";

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Tag tag = (Tag) getIntent().getSerializableExtra(ARG_TAG);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tag.getTitle());
        }

        RecyclerView recyclerView = findViewById(R.id.people_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UsersAdapter(this, UsersGenerator.generateUsers(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(UsersActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
