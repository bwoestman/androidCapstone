package com.example.bwoestman.weatheralarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Woestman on 4/1/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class ObjectArrayAdapter extends ArrayAdapter<Alarm>
{
    private ArrayList<Alarm> objects;

    public ObjectArrayAdapter(Context context, int resource, ArrayList<Alarm> objects)
    {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.detail_line, null);
        }

        return view;
    }
}
