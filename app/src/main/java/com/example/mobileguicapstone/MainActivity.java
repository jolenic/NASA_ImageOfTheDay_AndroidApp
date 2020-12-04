package com.example.mobileguicapstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    //nav drawer
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_toolbar);

        //Get Toolbar
        Toolbar tBar = findViewById(R.id.toolbar);
        //Load the Toolbar
        setSupportActionBar(tBar);

        //navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, tBar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goPick:
                        Intent intent1 = new Intent(MainActivity.this, DatePicker.class);
                        startActivity(intent1);
                        break;
                    case R.id.goListView:
                        Intent intent2 = new Intent(MainActivity.this, ListViewActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.goImageofDay:
                        Intent intent3 = new Intent(MainActivity.this, NasaDailyImage.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        //initialize buttons and EditText
        Button nameButton = findViewById(R.id.nameButton);
        EditText nameBox = findViewById(R.id.nameBox);
        Button snackbarButton = findViewById(R.id.snackbarButton);
        Button toListView = findViewById(R.id.toListView);
        Button toDatePicker = findViewById(R.id.toDatePicker);

        //set clickListener for nameButton to make custom Toast greeting
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hi, " + nameBox.getText() + "!";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        //set clickListener for snackbarButton to bring up a Snackbar
        snackbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Grab a snack!";
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

    } //end method onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //close the drawer before exit
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //toolbar click options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goPick:
                Intent intent1 = new Intent(this, DatePicker.class);
                startActivity(intent1);
                return true;
            case R.id.goListView:
                Intent intent2 = new Intent(this, ListViewActivity.class);
                startActivity(intent2);
                return true;
            case R.id.goImageofDay:
                Intent intent3 = new Intent(this, NasaDailyImage.class);
                startActivity(intent3);
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