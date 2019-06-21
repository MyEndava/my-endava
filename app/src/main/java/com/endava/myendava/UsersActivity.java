package com.endava.myendava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.endava.myendava.activities.ProfileActivity;
import com.endava.myendava.rest.RetrofitClient;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    public static final String ARG_TAG = "arg_tag_name";

    @BindView(R.id.tag_description_text_view)
    TextView descriptionTextView;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private Tag tag;
    private List<User> users = new ArrayList<>();
    private UsersAdapter adapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tag = (Tag) getIntent().getSerializableExtra(ARG_TAG);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tag.getTagName());
        }

        descriptionTextView.setText(tag.getTagDescription());
        collapsingToolbarLayout.post(() -> collapsingToolbarLayout.setScrimVisibleHeightTrigger(collapsingToolbarLayout.getMeasuredHeight() - 150));

        RecyclerView recyclerView = findViewById(R.id.people_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UsersAdapter(this, users, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitClient retrofitClient = new RetrofitClient();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(retrofitClient.getRetrofitClient().getUsersByTag(tag.getTagName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    Log.d("Users", users.toString());
                    this.users.clear();
                    this.users.addAll(users);
                    adapter.notifyDataSetChanged();
                }, throwable -> {
                    Log.e("Users", throwable.getLocalizedMessage());
                    Toast.makeText(UsersActivity.this, "Ooops! something wet wrong ...", Toast.LENGTH_SHORT).show();
                    this.users.clear();
                    this.users.addAll(UsersGenerator.generateUsers());
                    adapter.notifyDataSetChanged();
                }));
    }

    @Override
    protected void onPause() {
        compositeDisposable.clear();
        super.onPause();
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(UsersActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
