package com.example.mobileguicapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageDatabase extends SQLiteOpenHelper {

    //table strings
    String image_table = "images";
    String date = "date";
    String explanation = "explaination";
    String title = "title";
    String hdurl = "hdurl";
    String url = "url";
    String path = "path";

    public ImageDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "image_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table for the first time
        String create_table_query = "create table " + image_table +
                "(" + date + " text primary key, "
                + title + " text, "
                + explanation + " text, "
                + hdurl + " text, "
                + url + " text, "
                + path + " text"
                + ")";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table on upgrade
        String drop_table_query = "drop table " + image_table;
        db.execSQL(drop_table_query);
    }

    /**
     * @param date Date of the Image
     * @param url URL image URL to be fetched
     * @param title title of the image of the day
     * @param explanation story of the image of the day
     * @param hdurl HD URL of the image, to be shown in the browser
     * @param path Path to the Gallery for saved image
     * */
    public void insertImage(String date, String title,
                            String explanation, String hdurl,
                            String url, String path) {

        //mapping all the values into the ContentValues,
        //column name as key and parameter as value
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.date, date);
        contentValues.put(this.title, title);
        contentValues.put(this.explanation, explanation);
        contentValues.put(this.hdurl, hdurl);
        contentValues.put(this.url, url);
        contentValues.put(this.path, path);

        //getting database as Writable to insert the data
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(image_table, null, contentValues);
    }

    /**
     * @return List of saved images into the database
     * */
    public ArrayList<ImageResponse> getListOfImages(){
        ArrayList<ImageResponse> images = new ArrayList<>();

        //Performing read operation on the database
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(image_table,
                new String[]{date, title, explanation, hdurl, url, path},
                null, null, null, null, null);

        //reading first data
        if(cursor.moveToFirst()){
            do {
                //reading data from cursor
                String strDate = cursor.getString(0);
                String strTitle = cursor.getString(1);
                String strEx = cursor.getString(2);
                String strHDUrl = cursor.getString(3);
                String strUrl = cursor.getString(4);
                String strPath = cursor.getString(5);

                //holding cursor data to the ImageResponse object
                ImageResponse image = new ImageResponse();
                image.setDate(strDate);
                image.setTitle(strTitle);
                image.setExplanation(strEx);
                image.setHdurl(strHDUrl);
                image.setUrl(strUrl);
                image.setPath(strPath);
                //collecting image and adding into the list
                images.add(image);
            }while (cursor.moveToNext()); //moving cursor to the next if the next data
            //is available
        }
        return images;
    }

    /**
     * Get the Image data by passing the date.
     *
     * @param date Date of the Image of the Day to be fetched
     * @return only the single element data for the Image
     * */
    public ImageResponse getByDate(String date){
        ImageResponse image = new ImageResponse();

        //Reading the table using cursor
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(image_table,
                new String[]{date, title, explanation, hdurl, url, path},
                this.date + " = ?", new String[]{date}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                String strDate = cursor.getString(0);
                String strTitle = cursor.getString(1);
                String strEx = cursor.getString(2);
                String strHDUrl = cursor.getString(3);
                String strUrl = cursor.getString(4);
                String strPath = cursor.getString(5);
                image.setDate(strDate);
                image.setTitle(strTitle);
                image.setExplanation(strEx);
                image.setHdurl(strHDUrl);
                image.setUrl(strUrl);
                image.setPath(strPath);
            }while (cursor.moveToNext());
        }
        return image;
    }
}
