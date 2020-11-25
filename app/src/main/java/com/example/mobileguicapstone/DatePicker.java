package com.example.mobileguicapstone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DatePicker extends AppCompatActivity {

    public static final String TAG = "DatePickerActivity";

    Button toImageButton;
    Button datePickerFragmentButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        //initialize today button and set it to go to NasaDailyImage
        toImageButton = findViewById(R.id.toImageOfDayButton);
        toImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatePicker.this, NasaDailyImage.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //initialize calendar to find today's date
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        //this seems to be 1 lower than it should be for some reason, manually adding 1
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + (month + 1) + "-" + day;


        //Display Chosen Date
        TextView showDate = findViewById(R.id.showDate);
        showDate.setText("Date Selected: " + date);

        //initialize button to bring up date picker
        datePickerFragmentButton = findViewById(R.id.datePickerFragmentButton);
        datePickerFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(DatePicker.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                //changes display date to chosen date
                date = year + "-" + (month + 1) + "-" + day;
                Log.d(TAG, "onDateSet: date: " + date);
                showDate.setText("Date Selected: " + date);
            }
        };

    } //end onCreate()
}