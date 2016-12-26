package com.example.ll.myapplication5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by ll on 2016/12/22.
 */
public class StoryAdapter extends ArrayAdapter<Story> {
    private int resourceId;

    public StoryAdapter(Context context, int textViewRecourceId, List<Story> objects){
        super(context,textViewRecourceId,objects);
        resourceId = textViewRecourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Story story = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        ImageView story_image= (ImageView)view.findViewById(R.id.story_image);
        TextView story_name = (TextView)view.findViewById(R.id.story_name);
        TextView author = (TextView)view.findViewById(R.id.author);
        TextView update_page = (TextView)view.findViewById(R.id.update_page);

        story_image.setImageBitmap(story.getImagePath());
        story_name.setText(story.getStoryName());
        author.setText(story.getAuthor()+" 著 | "+story.getNoTime()+"章未读");
        update_page.setText("连载至："+story.getUpPage());

        return view;
    }


}
