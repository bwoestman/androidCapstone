package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Woestman on 2/26/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class AlarmListFragment extends Fragment implements AppInfo
{
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();

    private RecyclerView mAlarmRecyclerView;
    private List<Alarm> mAlarms;
    private AlarmAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        mAlarmRecyclerView = (RecyclerView) view.findViewById(R.id.alarm_recycler_view);
        mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class AlarmHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTimeTv;
        private Switch mEnableSw;

        private Alarm mAlarm;

        public AlarmHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            mTimeTv = (TextView) itemView.findViewById(R.id.tv_time);
            mEnableSw = (Switch) itemView.findViewById(R.id.sw_enable);
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        public void bindAlarm(Alarm alarm)
        {
            mAlarm = alarm;
        }

        @Override
        public void onClick(View v)
        {
            singletonAlarm.setClickedAlarm(mAlarm);

            AlarmEditFragment alarmEditFragment = new AlarmEditFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container, alarmEditFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder>
    {
        private List<Alarm> mAlarms;

        public AlarmAdapter(List<Alarm> alarms)
        {
            mAlarms = alarms;
        }

        @Override
        public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_alarm, parent, false);

            return new AlarmHolder(view);
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public void onBindViewHolder(AlarmHolder holder, int position)
        {

            Alarm alarm = mAlarms.get(position);
            holder.bindAlarm(alarm);

            String minute;
            String time;

            if (alarm.getMinute() < 10)
            {
                minute = "0" + alarm.getMinute();
            }
            else
            {
                minute = Integer.toString(alarm.getMinute());
            }

            time = alarm.getHour() + ":" + minute;

            holder.mTimeTv.setText(time);

            if (alarm.getEnabled() == 1)
            {
                holder.mEnableSw.setChecked(true);
            } else
            {
                holder.mEnableSw.setChecked(false);
            }
        }

        @Override
        public int getItemCount()
        {
            return mAlarms.size();
        }
    }

    private void updateUI()
    {
        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        mAlarms = dbHandler.getAlarms();
        mAdapter = new AlarmAdapter(mAlarms);

        mAlarmRecyclerView.setAdapter(mAdapter);
    }

    /**
     * this method is for testing the db
     */

    private void echoAlarms()
    {
        DBHandler db = new DBHandler(getContext(), null, null, 1);
        List<Alarm> as = db.getAlarms();

        for (Alarm a : as)
        {
            Log.d(TAG, "echoAlarms: " + a.toString());
        }
    }
}