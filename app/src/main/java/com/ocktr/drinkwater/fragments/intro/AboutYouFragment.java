package com.ocktr.drinkwater.fragments.intro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocktr.drinkwater.R;
import com.ocktr.drinkwater.fragments.dialogs.AboutYouNameDialog;

import java.util.Calendar;

public class AboutYouFragment extends Fragment {
    private ImageView fragmentAboutWomanGen, fragmentAboutManGen;
    private ConstraintLayout fragmentAboutDateLayout;
    private ConstraintLayout fragmentAboutNameLayout;
    private TextView fragmentAboutTextBirthDate, fragmentAboutTextName;
    private DatePickerDialog.OnDateSetListener listener;
    private String name;
    public static final int REQUEST_NAME_CODE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_you, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view){
        fragmentAboutManGen = view.findViewById(R.id.fragmentAboutManGen);
        fragmentAboutWomanGen = view.findViewById(R.id.fragmentAboutWomanGen);
        fragmentAboutManGen.setAlpha(1.0f);
        fragmentAboutWomanGen.setAlpha(0.4f);

        fragmentAboutDateLayout = view.findViewById(R.id.fragmentAboutDateLayout);
        fragmentAboutNameLayout = view.findViewById(R.id.fragmentAboutNameLayout);

        fragmentAboutTextBirthDate = view.findViewById(R.id.fragmentAboutTextBirthDate);
        fragmentAboutTextName = view.findViewById(R.id.fragmentAboutTextName);

        fragmentAboutManGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentAboutManGen.setAlpha(1.0f);
                fragmentAboutWomanGen.setAlpha(0.4f);
            }
        });

        fragmentAboutWomanGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentAboutManGen.setAlpha(0.4f);
                fragmentAboutWomanGen.setAlpha(1.0f);
            }
        });


        fragmentAboutDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2010, 0, 1);
                    int year = calendar.get(Calendar.YEAR);
//                    calendar.add(Calendar.YEAR, -12);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
                            listener,
                            year, month, day
                    );

                    dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 + "/" + i2 + "/" + i;
                fragmentAboutTextBirthDate.setTextColor(getResources().getColor(R.color.white));
                fragmentAboutTextBirthDate.setText(date);
            }
        };

        fragmentAboutNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNameDialog();
            }
        });

    }

    private void openNameDialog(){
        AboutYouNameDialog dialog = new AboutYouNameDialog();
        dialog.setTargetFragment(this, REQUEST_NAME_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Name Dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_NAME_CODE){
            name = data.getStringExtra("name");
            fragmentAboutTextName.setTextColor(getResources().getColor(R.color.white));
            fragmentAboutTextName.setText(name);
        }
    }

}