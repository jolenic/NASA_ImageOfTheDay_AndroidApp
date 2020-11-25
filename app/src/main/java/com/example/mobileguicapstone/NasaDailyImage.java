package com.example.mobileguicapstone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//This call will return a JSON object that has a URL, DATE, and HDURL.
//You should display the date and URL on the page, and a link to the HD image.
// If the user clicks the link, you should load the URL in the built-in browser on your device.

public class NasaDailyImage extends AppCompatActivity {

    final String API_KEY = "QzibVZa0WTgDvKxiYqqe7ArqM1Bbshla4QdcUtBt";

    //declare variables
    ProgressBar progress;
    TextView title;
    TextView dateDisplay;
    ImageView image;
    TextView hdLink;
    TextView description;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_image_main);

        //get date from previous activity
        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date");

        //initialize page elements
        title = findViewById(R.id.title);
        dateDisplay = findViewById(R.id.dateDisplay);
        image = findViewById(R.id.image);
        hdLink = findViewById(R.id.hdLink);
        description = findViewById(R.id.description);


        //initialize progress bar
        progress = findViewById(R.id.progress_bar);
        progress.setVisibility(View.VISIBLE);

        ImageQuery iq = new ImageQuery();
        iq.execute("https://api.nasa.gov/planetary/apod?api_key=" + API_KEY + "&date=" + date);


    } //end method onCreate()

    private class ImageQuery extends AsyncTask<String, Integer, String> {

        private String imgTitle;
        private String imgUrl;
        private String hdUrl;
        private String explanation;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                publishProgress(25);
                // convert string to JSON:
                JSONObject imageData = new JSONObject(result);

                //get String data
                imgTitle = String.valueOf(imageData.get("title"));
                imgUrl = String.valueOf(imageData.get("url"));
                hdUrl = String.valueOf(imageData.get("hdurl"));
                explanation = String.valueOf(imageData.get("explanation"));
                publishProgress(50);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress(100);
            return null;
        } //end method doInBackground

        @Override
        protected void onProgressUpdate(Integer... args) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(args[0]);
        }

        @Override
        public void onPostExecute(String s) {
            title.setText(imgTitle);
            dateDisplay.setText("Date: " + date);
            //image.setImageResource();
            hdLink.setText(hdUrl);
            description.setText(explanation);

            progress.setVisibility(View.INVISIBLE);
        }

    } //end subclass ImageQuery

} //end class NasaDailyImage