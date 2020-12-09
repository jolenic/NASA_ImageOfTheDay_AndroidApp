package com.example.mobileguicapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageDatabase extends SQLiteOpenHelper {

    //db and table strings
    protected final static String DB_NAME = "image_database";
    protected final static int VERSION = 3;
    protected final static String IMAGE_TABLE = "images";
    protected final static String DATE = "date";
    protected final static String DESCRIPTION = "explaination";
    protected final static String TITLE = "title";
    protected final static String HDURL = "hdurl";
    protected final static String URL = "url";
    protected final static String PATH = "path";
    protected final static String COL_ID = "_id";

    //constructor
    public ImageDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table for the first time
        String create_table_query = "create table " + IMAGE_TABLE +
                "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " text, "
                + TITLE + " text, "
                + DESCRIPTION + " text, "
                + HDURL + " text, "
                + URL + " text, "
                + PATH + " text"
                + ")";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table on upgrade
        String drop_table_query = "DROP TABLE IF EXISTS " + IMAGE_TABLE;
        db.execSQL(drop_table_query);
        //create new table
        onCreate(db);
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
        contentValues.put(this.DATE, date);
        contentValues.put(this.TITLE, title);
        contentValues.put(this.DESCRIPTION, explanation);
        contentValues.put(this.HDURL, hdurl);
        contentValues.put(this.URL, url);
        contentValues.put(this.PATH, path);

        //getting database as Writable to insert the data
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(IMAGE_TABLE, null, contentValues);
    }

    /**
     * @return List of saved images into the database
     * */
    public ArrayList<ImageResponse> getListOfImages() {
        ArrayList<ImageResponse> images = new ArrayList<>();

        //Performing read operation on the database
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(IMAGE_TABLE,
                new String[]{COL_ID, DATE, TITLE, DESCRIPTION, HDURL, URL, PATH},
                null, null, null, null, null);

        //reading first data
        if (cursor.moveToFirst()) {
            do {
                //reading data from cursor
                int dbId = cursor.getInt(0);
                String strDate = cursor.getString(1);
                String strTitle = cursor.getString(2);
                String strEx = cursor.getString(3);
                String strHDUrl = cursor.getString(4);
                String strUrl = cursor.getString(5);
                String strPath = cursor.getString(6);

                //holding cursor data to the ImageResponse object
                ImageResponse image = new ImageResponse();
                image.setId(dbId);
                image.setDate(strDate);
                image.setTitle(strTitle);
                image.setDescription(strEx);
                image.setHdurl(strHDUrl);
                image.setUrl(strUrl);
                image.setPath(strPath);
                //collecting image and adding into the list
                images.add(image);
            } while (cursor.moveToNext()); //moving cursor to the next if the next data
            //is available
        }
        return images;
    }

    //check to see if record already exists in DB
//    public boolean existsInDB(String date) {
//        String query = "Select * from " + IMAGE_TABLE + " where " + DATE + "='" + date + "'";
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor c = database.rawQuery(query, null);
//        if(c.getCount() >= 0) {
//            c.close();
//            return true;
//        }
//        c.close();
//        return false;
//    }


    /**
     * Get the Image data by passing the date.
     *
     * @param date Date of the Image of the Day to be fetched
     * @return only the single element data for the Image
     */
    public ImageResponse getByDate(String date) {
        ImageResponse image = new ImageResponse();

        //Reading the table using cursor
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(IMAGE_TABLE,
                new String[]{date, TITLE, DESCRIPTION, HDURL, URL, PATH},
                this.DATE + " = ?", new String[]{date}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String strDate = cursor.getString(0);
                String strTitle = cursor.getString(1);
                String strEx = cursor.getString(2);
                String strHDUrl = cursor.getString(3);
                String strUrl = cursor.getString(4);
                String strPath = cursor.getString(5);
                image.setDate(strDate);
                image.setTitle(strTitle);
                image.setDescription(strEx);
                image.setHdurl(strHDUrl);
                image.setUrl(strUrl);
                image.setPath(strPath);
            } while (cursor.moveToNext());
        }
        return image;
    }

    /**
     * Deleting the record from the table where matched record is found
     *
     * @param date Date of the Image of the day
     */
//    public int deleteImage(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(IMAGE_TABLE,
//                this.DATE + " = ? ",
//                new String[]{date});
//    }
    protected void deleteImage(ImageResponse img) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(IMAGE_TABLE, COL_ID + "= ?", new String[]{Long.toString(img.getId())});
    }
}
