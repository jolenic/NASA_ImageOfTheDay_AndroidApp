package com.example.mobileguicapstone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

//This call will return a JSON object that has a URL, DATE, and HDURL.
//You should display the date and URL on the page, and a link to the HD image.
// If the user clicks the link, you should load the URL in the built-in browser on your device.

public class NasaDailyImage extends AppCompatActivity {

    final String API_KEY = "QzibVZa0WTgDvKxiYqqe7ArqM1Bbshla4QdcUtBt";

    //declare variables
    ProgressBar progress;
    TextView title;
    //database
    protected ImageDatabase imageDatabase = new ImageDatabase(this);
    TextView dateDisplay;
    protected SQLiteDatabase db;
    ImageView image;
    String imageTitle;
    Button hdLink;
    String date;
    TextView description;
    String imageUrl;
    Bitmap imageBit;
    String imageHdUrl;
    String imageDesc;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_image_main);

        //Get Toolbar
        Toolbar tBar = findViewById(R.id.toolbar);
        //Load the Toolbar
        setSupportActionBar(tBar);

        //check if date was passed in
        Intent intent = getIntent();
        if (intent.hasExtra("date")) {
            //get date from previous activity
            Bundle bundle = getIntent().getExtras();
            date = bundle.getString("date");
        } else {
            //if no date passed, set date to current date
            //initialize calendar to find today's date
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            //this seems to be 1 lower than it should be for some reason, manually adding 1
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            date = year + "-" + (month + 1) + "-" + day;
        }


        //initialize page elements
        title = findViewById(R.id.title);
        dateDisplay = findViewById(R.id.dateDisplay);
        image = findViewById(R.id.image);
        hdLink = findViewById(R.id.hdLink);
        description = findViewById(R.id.description);

        //initialize buttons
        Button saveButton = findViewById(R.id.saveButton);

        //initialize progress bar
        progress = findViewById(R.id.progress_bar);
        progress.setVisibility(View.VISIBLE);

        ImageQuery iq = new ImageQuery();
        iq.execute("https://api.nasa.gov/planetary/apod?api_key=" + API_KEY + "&date=" + date);


        //click listener for link to hd image
        hdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(imageHdUrl));
                startActivity(browserIntent);
            }
        });

        //Saving the image data to the database PLACEHOLDER, STILL NEEDS LOGIC
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";

                //save image
                fileName = date + ".png";

                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    imageBit.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                imageDatabase.insertImage(date, imageTitle, imageDesc, imageHdUrl, imageUrl, fileName);
                message = "Image for " + date + " added to database";
                Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
            }
        });


    } //end method onCreate()

    //check if file exists already
    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        Log.i("File exists in memory", String.valueOf(file.exists()));
        return file.exists();
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

    private class ImageQuery extends AsyncTask<String, Integer, String> {

        private String imgTitle;
        private String imgUrl;
        private String hdUrl;
        private String explanation;
        private Bitmap img;

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

                URL url2 = new URL(imgUrl);
                HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    img = BitmapFactory.decodeStream(connection.getInputStream());
                }
                publishProgress(75);

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
            imageTitle = imgTitle;
            imageBit = img;
            dateDisplay.setText("Date: " + date);
            //image.setImageResource();
//            hdLink.setText(hdUrl);
            imageHdUrl = hdUrl;
            description.setText(explanation);
            imageDesc = explanation;
            image.setImageBitmap(img);
            imageUrl = imgUrl;

            progress.setVisibility(View.INVISIBLE);
        }

    } //end subclass ImageQuery

} //end class NasaDailyImage