package com.ocktr.drinkwater.fragments.intro;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ocktr.drinkwater.R;
import com.ocktr.drinkwater.fragments.dialogs.AboutYouNameDialog;
import com.ocktr.drinkwater.fragments.dialogs.DrinkIntervalDialog;


public class ScheduleFragment extends Fragment {
    private static final int REQUEST_SCHEDULE_INTERVAL_CODE = 0;
    private ConstraintLayout fragmentScheduleWakeUpLayout, fragmentScheduleFallLayout, fragmentScheduleDrinkIntervalLayout;
    private TextView fragmentScheduleWakeUpText, fragmentScheduleFallText, fragmentScheduleDrinkingIntervalText;
    private TimePickerDialog timePickerDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initializeViews(view);
        return view;

    }

    private void initializeViews(View view){
        fragmentScheduleWakeUpLayout = view.findViewById(R.id.fragmentScheduleWakeUpLayout);
        fragmentScheduleFallLayout = view.findViewById(R.id.fragmentScheduleFallLayout);
        fragmentScheduleDrinkIntervalLayout = view.findViewById(R.id.fragmentScheduleDrinkIntervalLayout);
        fragmentScheduleWakeUpText = view.findViewById(R.id.fragmentScheduleWakeUpText);
        fragmentScheduleFallText = view.findViewById(R.id.fragmentScheduleFallText);
        fragmentScheduleDrinkingIntervalText = view.findViewById(R.id.fragmentScheduleDrinkingIntervalText);

        fragmentScheduleWakeUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(
                        getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        fragmentScheduleWakeUpText.setTextColor(getResources().getColor(R.color.white));
                        fragmentScheduleWakeUpText.setText(String.format("%02d:%02d", i, i1));
                    }
                }, 0, 0, true
                );
                timePickerDialog.show();
            }
        });

        fragmentScheduleFallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(
                        getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        fragmentScheduleFallText.setTextColor(getResources().getColor(R.color.white));
                        fragmentScheduleFallText.setText(String.format("%02d:%02d", i, i1));
                    }
                }, 0, 0, true
                );
                timePickerDialog.show();
            }
        });

        fragmentScheduleDrinkIntervalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIntervalDialog();
            }
        });
    }

    private void openIntervalDialog(){
        DrinkIntervalDialog dialog = new DrinkIntervalDialog();
        dialog.setTargetFragment(this, REQUEST_SCHEDULE_INTERVAL_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Interval Dialog");
    }

    private int intervalHour, intervalMinute;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_SCHEDULE_INTERVAL_CODE){
            intervalHour = data.getIntExtra("intervalHour", 0);
            intervalMinute = data.getIntExtra("intervalMinute", 0);
            intervalMinute = intervalMinute * 15;
            fragmentScheduleDrinkingIntervalText.setTextColor(getResources().getColor(R.color.white));
            fragmentScheduleDrinkingIntervalText.setText(String.format("%02d:%02d", intervalHour, intervalMinute));
        }
    }
}