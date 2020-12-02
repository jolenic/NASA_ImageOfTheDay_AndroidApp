package com.example.mobileguicapstone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SavedImagesListAdapter extends BaseAdapter {

    //Collection of data to be displayed on list
    ArrayList<ImageResponse> imageResponses;
    Context context;

    static LayoutInflater inflater;

    public SavedImagesListAdapter(Context context, ArrayList<ImageResponse> imageResponses){
        this.context = context;
        this.imageResponses = imageResponses;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return imageResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_item_image_row, null);
        }
        ImageResponse imageResponse = (ImageResponse) getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        textView.setText(imageResponse.getDate());
        Bitmap bitmap = showImageFromGallery(imageResponse.getPath());
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
        return convertView;
    }

    /**
     * Getting the bitmap from the storage path
     * @param path Path to the File system URI
     * @return Bitmap to be shown on the ImageView
     * */
    private Bitmap showImageFromGallery(String path) {
        final InputStream imageStream;
        try {
            imageStream = context.getContentResolver().openInputStream(Uri.parse(path));
            return BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}