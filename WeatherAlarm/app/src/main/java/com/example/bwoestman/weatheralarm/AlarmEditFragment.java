package com.example.bwoestman.weatheralarm;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by Brian Woestman on 2/26/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlarmEditFragment extends Fragment implements AppInfo
{
    private TimePicker mTimePicker;
    private SeekBar mSbAdjustment;
    private SeekBar mSbRain;
    private Button mBtnCancel;
    private Button mBtnSave;
    private TextView mTvAdjustSbPostion;
    private TextView mTvRainSbPosition;

    private Integer mAdjSbPostion = 0;
    private Integer mRainSbPostion = 0;


    private Alarm alarm;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_alarm_edit, container, false);

        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        mSbAdjustment = (SeekBar) view.findViewById(R.id.sbAdjustment);
        mSbRain = (SeekBar) view.findViewById(R.id.sbRain);

        mTvAdjustSbPostion = (TextView) view.findViewById(R.id.tvAdjustSbPostion);
        mTvRainSbPosition = (TextView) view.findViewById(R.id.tvRainSbPosition);

        mBtnCancel = (Button) view.findViewById(R.id.btnCancel);
        mBtnSave = (Button) view.findViewById(R.id.btnSave);

        //seekbar anonymous class for Adjustment setting
        mSbAdjustment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mTvAdjustSbPostion.setText(": " + Integer.toString(progress) + " minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        //seekbar anonymous class for Rain setting
        mSbRain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mTvRainSbPosition.setText(": " + Integer.toString(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        mBtnSave.setOnClickListener(new AdapterView.OnClickListener()
        {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v)
            {
                alarm = new Alarm();
                alarm.setHour(mTimePicker.getCurrentHour());
                alarm.setMinute(mTimePicker.getCurrentMinute());
            }
        });

        return view;
    }
}
