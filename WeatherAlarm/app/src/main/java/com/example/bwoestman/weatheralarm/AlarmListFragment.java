package com.example.bwoestman.weatheralarm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Brian Woestman on 2/26/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmListFragment extends Fragment implements AppInfo
{
    private ArrayList<Alarm> alarms;
    private ObjectArrayAdapter mAdapter;
    private ListView mLvAlarmList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        mLvAlarmList = (ListView) view.findViewById(R.id.lv_alarm_list);

        DBHandler db = new DBHandler(getContext(), null, null, 1);

        alarms = (ArrayList<Alarm>) db.getAlarms();

        for (Alarm a : alarms)
        {
            Log.d(TAG, "onCreateView: alarms " + a.toString());
        }

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        mAdapter = new ObjectArrayAdapter(getActivity(), R.layout
                .detail_line, alarms);
        mLvAlarmList.setAdapter(mAdapter);
    }
}