package com.example.mobileguicapstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

//The first Milestone due on 20 November requires:

//The project must have a ListView somewhere to present items.
// Selecting an item from the ListView must show detailed information about the item selected.

//The project must have at least 1 progress bar and at least 1 button.

//The project must have at least 1 edit text with appropriate text input method
// and at least 1 Toast and 1 Snackbar.

//All activities must be integrated into a single working application,
// on a single emulator, and must be uploaded to GitHub.

//The functions and variables you write must be properly documented using JavaDoc comments.

public class MainActivity extends AppCompatActivity {

    Button nameButton;
    EditText nameBox;
    Button snackbarButton;
    Button toListView;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize buttons and EditText
        nameButton = findViewById(R.id.nameButton);
        nameBox = findViewById(R.id.nameBox);
        snackbarButton = findViewById(R.id.snackbarButton);
        toListView = findViewById(R.id.toListView);

        //set clickListener for nameButton to make custom Toast greeting
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Hi, " + nameBox.getText() + "!";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        //set clickListener for snackbarButton to bring up a Snackbar
        snackbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Grab a snack!";
                Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
            }
        });

        //set clickListener for toListView to go to ListViewActivity
        final Intent listViewActivity = new Intent(this, ListViewActivity.class);
        toListView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(listViewActivity);
            }
        });

    }
}