package com.example.mobileguicapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class ImageDatabase extends SQLiteOpenHelper {

    //strings for the table
    String image_table = "images";
    String date = "date";
    String explanation = "explanation";
    String title = "title";
    String hdurl = "hdurl";
    String url = "url";
    String path = "path";

    public ImageDatabase(Context context) {
        super(context, "image_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table for the first startup
        String create_initTable_query = "create table " + image_table +
                "(" + date + " text primary key, "
                + title + " text, "
                + explanation + " text, "
                + hdurl + " text, "
                + url + " text, "
                + path + " text "
                + ")";

        db.execSQL(create_initTable_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade, drop table
        String drop_table_query = "drop table " + image_table;
        db.execSQL(drop_table_query);
    }

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

    /**
     * Deleting the record from the table where matched record is found
     * @param date Date of the Image of the day
     * */
    public int deleteImage(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(image_table,
                this.date + " = ? ",
                new String[]{date});

    }

    //required default constructor
    public ImageDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
