package com.example.mobileguicapstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SavedImageFragment extends Fragment {


    public SavedImageFragment() {
        // Required empty public constructor
    }

    //Views
    View view;
    ListView listView;

    ArrayList<ImageResponse> imageResponses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_list_view, container, false);
        listView = view.findViewById(R.id.testList);

        //Start description activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageResponse imageResponse = imageResponses.get(position);
                startActivity(new Intent(getActivity(), NasaDailyImage.class)
                        .putExtra("title", imageResponse.getTitle())
                        .putExtra("explain", imageResponse.getExplanation())
                        .putExtra("date", imageResponse.getDate())
                        .putExtra("url", imageResponse.getUrl())
                        .putExtra("hdurl", imageResponse.getHdurl()));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getting the data from the database and showing them into a listview

        ImageDatabase imageDatabase = new ImageDatabase(getActivity());

        //get the list of available data from database
        imageResponses = imageDatabase.getListOfImages();

        //setup adapter with a listview
        SavedImagesListAdapter savedImagesListAdapter = new SavedImagesListAdapter(getActivity(), imageResponses);
        listView.setAdapter(savedImagesListAdapter);
    }
}
