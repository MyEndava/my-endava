package com.endava.myendava.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void showProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }

    void displayError(String error){
        if(error != null){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }
}
