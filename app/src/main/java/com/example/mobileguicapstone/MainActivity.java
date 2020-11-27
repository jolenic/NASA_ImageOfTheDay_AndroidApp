package com.example.mobileguicapstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    Button toDatePicker;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Toolbar
        Toolbar tBar = findViewById(R.id.toolbar);
        //Load the Toolbar
        setSupportActionBar(tBar);

        //initialize buttons and EditText
        nameButton = findViewById(R.id.nameButton);
        nameBox = findViewById(R.id.nameBox);
        snackbarButton = findViewById(R.id.snackbarButton);
        toListView = findViewById(R.id.toListView);
        toDatePicker = findViewById(R.id.toDatePicker);

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

        //set clickListener for toDatePicker to go to DatePicker
        final Intent datePicker = new Intent(this, DatePicker.class);
        toDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(datePicker);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_toolbar_menu, menu);
        return true;
    }

    /**
     * toolbar menu item clicks
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goPick:
                Intent intent1 = new Intent(this, DatePicker.class);
                startActivity(intent1);
                return true;
            case R.id.menuHelp:
                //showing alert dialog for help
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.help));
                builder.setPositiveButton(getResources().getString(R.string.image_okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setMessage(getResources().getString(R.string.image_help));
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}