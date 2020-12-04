package com.example.mobileguicapstone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String HD_URL = "hdUrl";
    private static final String PATH = "path";
    private Bundle dataFromActivity;
    // TODO: Rename and change types of parameters
    private String title;
    private String date;
    private String description;
    private String url;
    private String hdUrl;
    private String path;

    private AppCompatActivity parentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dataFromActivity = getArguments();
        if (dataFromActivity != null) {
            title = dataFromActivity.getString(TITLE);
            date = dataFromActivity.getString(DATE);
            description = dataFromActivity.getString(DESCRIPTION);
//            url = getArguments().getString(URL);
//            hdUrl = getArguments().getString(HD_URL);
//            path = getArguments().getString(PATH);
        }

        View newView = inflater.inflate(R.layout.image_fragment, container, false);

        //initialize page elements
        TextView displayTitle = newView.findViewById(R.id.title);
        displayTitle.setText(title);
        TextView dateDisplay = newView.findViewById(R.id.dateDisplay);
        dateDisplay.setText(date);
//        ImageView image = container.findViewById(R.id.image);
//        //deal with this once i actually save the images
//        TextView hdLink = container.findViewById(R.id.hdLink);
//        hdLink.setText(hdUrl);
        TextView displayDescription = newView.findViewById(R.id.description);
        displayDescription.setText(description);

        //initialize buttons
        Button deleteButton = newView.findViewById(R.id.deleteButton);
        //Deleting the image data from the database PLACEHOLDER, STILL NEEDS LOGIC
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Image for " + date + " deleted";
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        return newView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    } //end onAttach()

}