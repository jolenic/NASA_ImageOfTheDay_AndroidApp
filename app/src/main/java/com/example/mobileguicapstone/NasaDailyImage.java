package com.example.mobileguicapstone;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class NasaDailyImage extends AppCompatActivity {

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_image_main);

        //initialize progress bar
        progress = findViewById(R.id.progress_bar);
        progress.setVisibility(View.VISIBLE);
    }
}