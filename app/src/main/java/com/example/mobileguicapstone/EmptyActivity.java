package com.example.mobileguicapstone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        Bundle bundle = getIntent().getExtras();
        ImageFragment fragment = new ImageFragment(); //add an ImageFragment
        fragment.setArguments(bundle); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.
    }
}