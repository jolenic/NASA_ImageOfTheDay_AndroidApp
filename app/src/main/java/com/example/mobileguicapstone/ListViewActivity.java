package com.example.mobileguicapstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ArrayList<String> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //Get Toolbar
        Toolbar tBar = findViewById(R.id.toolbar);
        //Load the Toolbar
        setSupportActionBar(tBar);
        tBar.setTitle(R.string.date_toolbar_header);

        //initialize elements and populate it with sample data
        elements = new ArrayList<>();
        elements.add("Test 1");
        elements.add("Test 2");
        elements.add("Test 4");
        elements.add("OH GEEZ OH NO I LOST COUNT :(");
        //initialize ListView tools
        ListView testList = findViewById(R.id.testList);
        MyListAdapter adapter = new MyListAdapter();
        testList.setAdapter(adapter);

        //Item Click Listener will pop up an alert dialog with extra info about the item
        testList.setOnItemClickListener((list, item, position, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("More Info Placeholder")
                    //What is the message:
                    .setMessage("The selected item is: " + position + ".")
                    //Show the dialog
                    .create().show();
        });
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            //Will need to change when using DB
            return (long) position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            //make a new row
            if (newView == null) {
                newView = inflater.inflate(R.layout.list_item_layout, parent, false);
            }
            TextView tView = newView.findViewById(R.id.listTextGoesHer);
            tView.setText(getItem(position).toString());
            return newView;
        }
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