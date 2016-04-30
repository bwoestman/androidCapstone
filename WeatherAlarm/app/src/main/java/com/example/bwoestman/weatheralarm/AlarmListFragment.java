package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.Switch;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Woestman on 2/26/16.
 * Android Programming
 * We 5:30p - 9:20p
 */

/**
 * this class is a fragment where the alarms are displayed in a list using the recycler
 * view
 */

public class AlarmListFragment extends Fragment implements AppInfo
{
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();
    private RecyclerView mAlarmRecyclerView;
    private ArrayList<Alarm> alarms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        if (singletonAlarm.getAlarms() != null)
        {
            alarms = singletonAlarm.getAlarms();
        }

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        mAlarmRecyclerView = (RecyclerView) view.findViewById(R.id.alarm_recycler_view);
        mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private class AlarmHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener
    {
        private TextView mTimeTv;
        private TextView mRainTv;
        private TextView mAdjustment;
        private Alarm mAlarm;

        public AlarmHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mTimeTv = (TextView) itemView.findViewById(R.id.tv_time);
            mRainTv = (TextView) itemView.findViewById(R.id.tv_rain);
            mAdjustment = (TextView) itemView.findViewById(R.id.tv_adjustment);

//            mEnableSw = (Switch) itemView.findViewById(R.id.sw_enable);

//            mEnableSw.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    boolean enabled = mEnableSw.isChecked();
//                    if (enabled)
//                    {
//                        mAlarm.setEnabled(1);
//                    }
//                    else
//                    {
//                        mAlarm.setEnabled(0);
//                    }
//                    DBHandler db = new DBHandler(getContext(), null, null, 1);
//                    db.updateAlarm(mAlarm);
//                }
//            });
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
            if (singletonAlarm.getIsSinglePane())
            {
                goToEditFragment();
            }
            else
            {
                AlarmEditFragment alarmEditFragment = (AlarmEditFragment) getFragmentManager()
                        .findFragmentById(R.id.fragment_alarm_edit);
                alarmEditFragment.updateUi();
            }
        }


        @Override
        public boolean onLongClick(final View v)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Alarm?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            DBHandler dbHandler = new DBHandler(v.getContext(), null, null, 1);
                            dbHandler.deleteAlarm(mAlarm);
                            singletonAlarm.setAlarms((ArrayList<Alarm>) dbHandler.getAlarms());
                            updateUI();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
            alertDialog.show();

            return false;
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
            Alarm alarm;
            String minute;
            String time;
            String rain;
            String adjustment;

            alarm = mAlarms.get(position);

            holder.bindAlarm(alarm);

            if (alarm.getMinute() < 10)
            {
                minute = "0" + alarm.getMinute();
            }
            else
            {
                minute = Integer.toString(alarm.getMinute());
            }

            time = alarm.getHour() + ":" + minute;
            rain = Integer.toString(alarm.getRain()) + "% ";
            adjustment = Integer.toString(alarm.getAdjustment()) + "m Offset";

            if (singletonAlarm.getIsSinglePane())
            {
                holder.mAdjustment.setText(adjustment);
            }
            holder.mTimeTv.setText(time);
            holder.mRainTv.setText(rain);

//            if (alarm.getEnabled() == 1)
//            {
//                holder.mEnableSw.setChecked(true);
//            } else
//            {
//                holder.mEnableSw.setChecked(false);
//            }
//
//            if (alarm.getEnabled() == 1)
//            {
//                holder.mEnableSw.setChecked(true);
//            }
//            else
//            {
//                holder.mEnableSw.setChecked(false);
//            }
        }

        @Override
        public int getItemCount()
        {
            return mAlarms.size();
        }
    }

    /**
     * this method updates and outputs the alarm list
     */

    public void updateUI()
    {
        List<Alarm> mAlarms;
        DBHandler dbHandler;
        AlarmAdapter mAdapter;

        if (singletonAlarm.getAlarms() == null)
        {
            dbHandler = new DBHandler(getContext(), null, null, 1);
            mAlarms = dbHandler.getAlarms();
            singletonAlarm.setAlarms((ArrayList<Alarm>) mAlarms);
        }
        else
        {
            mAlarms = singletonAlarm.getAlarms();
        }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    /**
     * this method switches back to the AlarmEditFragment
     */

    public void goToEditFragment()
    {
        AlarmEditFragment alarmEditFragment = new AlarmEditFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_container, alarmEditFragment)
                .addToBackStack(null)
                .commit();
    }
}