package com.example.ksala.healthproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by kevin on 22/11/2015.
 */
public class ImageAdapter extends BaseAdapter
{
    private Context context;
    private static final int resID []= {
            R.mipmap.ecg,
            R.mipmap.lungcapacity,
            R.mipmap.bloodpressure,
            R.mipmap.temperature,
            R.mipmap.respiratoryrate,
            R.mipmap.o2saturation };

    public ImageAdapter(Context c)
    {
        context = c;
    }

    //---returns the number of images---
    public int getCount() {
        return 6;
    }

    //---returns the ID of an item---
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //---returns an ImageView view---
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(50, 50, 50, 50);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(resID[position]);
        return imageView;
    }
}

