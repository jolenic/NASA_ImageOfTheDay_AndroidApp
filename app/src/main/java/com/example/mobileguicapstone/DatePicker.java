package com.example.mobileguicapstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DatePicker extends AppCompatActivity {

    Button toImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        //initialize button and set it to go to NasaDailyImage
        toImageButton = findViewById(R.id.toImageOfDayButton);
        toImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatePicker.this, NasaDailyImage.class);
                startActivity(intent);
            }
        });
    }
}