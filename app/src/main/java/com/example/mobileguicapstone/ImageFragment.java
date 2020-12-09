package com.example.mobileguicapstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String HD_URL = "hdUrl";
    private static final String PATH = "path";
    protected final static String COL_ID = "_id";

    private Bundle dataFromActivity;
    // TODO: Rename and change types of parameters
    private String title;
    private String date;
    private String description;
    private String url;
    private String hdUrl;
    private String path;
    private int id;

    private AppCompatActivity parentActivity;


    //check if file exists already
    public boolean fileExistance(String fname) {
        File file = parentActivity.getBaseContext().getFileStreamPath(fname);
        Log.i("File exists in memory", String.valueOf(file.exists()));
        return file.exists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dataFromActivity = getArguments();
        if (dataFromActivity != null) {
            title = dataFromActivity.getString(TITLE);
            date = dataFromActivity.getString(DATE);
            description = dataFromActivity.getString(DESCRIPTION);
            url = getArguments().getString(URL);
            hdUrl = getArguments().getString(HD_URL);
            path = getArguments().getString(PATH);
            id = getArguments().getInt(COL_ID);
        }

        View newView = inflater.inflate(R.layout.image_fragment, container, false);

        //initialize page elements
        TextView displayTitle = newView.findViewById(R.id.title);
        displayTitle.setText(title);
        TextView dateDisplay = newView.findViewById(R.id.dateDisplay);
        dateDisplay.setText(date);

        //display saved image
        ImageView image = newView.findViewById(R.id.image);

        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap imgBit = BitmapFactory.decodeStream(fis);
        image.setImageBitmap(imgBit);


        //set up Hd Link
        Button hdLink = newView.findViewById(R.id.hdLink);
        //click listener for link to hd image
        hdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(hdUrl));
                startActivity(browserIntent);
            }
        });


        TextView displayDescription = newView.findViewById(R.id.description);
        displayDescription.setText(description);

        return newView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    } //end onAttach()

}