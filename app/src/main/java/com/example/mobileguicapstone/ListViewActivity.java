package com.example.mobileguicapstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ArrayList<String> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //initialize elements and populate it with sample data
        elements = new ArrayList<>();
        elements.add("Test 1");
        elements.add("Test 2");
        elements.add("Test 4");
        elements.add("OH GEEZ OH NO I LOST COUNT :(");

        //initialize ListView and adapter
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

    //private class to define MyListAdapter
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
}