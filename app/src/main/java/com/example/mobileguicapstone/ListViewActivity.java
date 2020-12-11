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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ArrayList<ImageResponse> savedImages;
    ImageDatabase imageDatabase;

    //for passing to fragment as bundle
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String HD_URL = "hdUrl";
    private static final String PATH = "path";
    private final static String COL_ID = "_id";

    //get result from activity
    private static final int REQ_DEL = 1;
    private static final int YES_DEL = 2;

    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //Get Toolbar
        Toolbar tBar = findViewById(R.id.toolbar);
        //Load the Toolbar
        setSupportActionBar(tBar);

        imageDatabase = new ImageDatabase(this);
        savedImages = imageDatabase.getListOfImages();

        //initialize ListView tools
        ListView testList = findViewById(R.id.testList);
        adapter = new MyListAdapter();
        testList.setAdapter(adapter);

        //Item Click Listener will pop up an alert dialog with extra info about the item
        testList.setOnItemClickListener((list, item, position, id) -> {

            Bundle dataToPass = new Bundle();
            ImageResponse ir = savedImages.get(position);
            dataToPass.putString(TITLE, ir.getTitle());
            dataToPass.putString(DATE, ir.getDate());
            dataToPass.putString(DESCRIPTION, ir.getDescription());
            dataToPass.putString(URL, ir.getUrl());
            dataToPass.putString(HD_URL, ir.getHdurl());
            dataToPass.putString(PATH, ir.getPath());
            dataToPass.putInt(COL_ID, ir.getId());
            Intent nextActivity = new Intent(ListViewActivity.this, EmptyActivity.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);
        });

        //Long click to delete
        testList.setOnItemLongClickListener((list, item, position, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.want_to_delete))

                    //What is the message:
                    .setMessage(getResources().getString(R.string.delete_image_for) + savedImages.get(position).getDate() + "?")

                    //what the Yes button does:
                    .setPositiveButton(R.string.yes, (click, arg) -> {
                        //delete image file
                        File file = new File(savedImages.get(position).getPath());
                        boolean deleted = file.delete();
                        //delete from database
                        imageDatabase.deleteImage(savedImages.get(position));
                        //remove from list
                        savedImages.remove(position);
                        //notify adapter
                        adapter.notifyDataSetChanged();
                        //toast message to confirm deletion
                        String message = getResources().getString(R.string.image_deleted);
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    })
                    //What the No button does:
                    .setNegativeButton(R.string.no, (click, arg) -> {
                    })
                    //Show the dialog
                    .create().show();
            return true;
        });
    }


    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return savedImages.size();
        }

        @Override
        public Object getItem(int position) {
            return savedImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) savedImages.get(position).getId();
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
            ImageResponse ir = (ImageResponse) getItem(position);
            tView.setText(" Date: " + ir.getDate());
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
                builder.setMessage(getResources().getString(R.string.help_list));
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}