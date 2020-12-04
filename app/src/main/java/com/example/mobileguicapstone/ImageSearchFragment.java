package com.example.mobileguicapstone;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;

import android.widget.TextView;


import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSearchFragment extends Fragment {

    public ImageSearchFragment() {
        // Required empty public constructor
    }

    //Views
    View layout;
    TextView textView;

    //Objects
    String selectedDate = "", textDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_image_search, container, false);
        textView = layout.findViewById(R.id.calendarPick);
        textView.setOnClickListener(v -> showDatePickerDialog());
        return layout;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //Formatting the selected date to pass into the API url.
                String monthStr;
                month = month + 1;
                if (month < 10) {
                    monthStr = "0" + month;
                } else {
                    monthStr = "" + month;
                }
                String dayStr;
                if (dayOfMonth < 10) {
                    dayStr = "0" + dayOfMonth;
                } else {
                    dayStr = "" + dayOfMonth;
                }
                textDate = dayStr + "-" + monthStr + "-" + year;
                selectedDate = year + "-" + monthStr + "-" + dayStr;
                textView.setText(textDate); //update the text view
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
