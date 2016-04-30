package com.example.bwoestman.weatheralarm;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppInfo
{
    private ArrayList<Alarm> alarms;
    private SingletonAlarm singletonAlarm = SingletonAlarm.getInstance();

    /**
     * this method is used to kick off the application and start the AlarmListFragment
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //todo remove this
//        DBHandler db = new DBHandler(this, null, null, 1);
//        db.deleteAllAlarms();
        alarms = singletonAlarm.getAlarms();

        if (singletonAlarm.getServiceAlarm() != null)
        {
            Alarm serviceAlarm;

            serviceAlarm = singletonAlarm.getServiceAlarm();

            AlarmController ac = new AlarmController();

            if (ac.exceedsPrecipThreshold(serviceAlarm))
            {
                ac.createAlarm(this, serviceAlarm);
            }
        }

        super.onCreate(savedInstanceState);
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            singletonAlarm.setIsSinglePane(false);
            setContentView(R.layout.tablet_layout);
        }
        else
        {
            singletonAlarm.setIsSinglePane(true);
            setContentView(R.layout.activity_main);
        }

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState == null)
            {
                AlarmListFragment alf = new AlarmListFragment();
                alf.setArguments(getIntent().getExtras());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, alf)
                        .commit();
            }
        }
    }

    /**
     * this method is called when an item is pressed on the menu
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.add_alarm:
                SingletonAlarm.getInstance().setClickedAlarm(null);
                if (singletonAlarm.getIsSinglePane())
                {
                    goToEditView();
                }
                else
                {
                    AlarmEditFragment alarmEditFragment = (AlarmEditFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.fragment_alarm_edit);
                    alarmEditFragment.clearInputs();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * this method switches to the AlarmEditFragment
     */

    public void goToEditView()
    {
        AlarmEditFragment aef = new AlarmEditFragment();

        android.support.v4.app.FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, aef)
                .addToBackStack(null)
                .commit();
    }
}