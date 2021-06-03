package com.ocktr.drinkwater.fragments.intro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ocktr.drinkwater.R;
import com.zjun.widget.RuleView;


public class WeightFragment extends Fragment {

    TextView fragmentWeightTextWeight;
    RuleView fragmentWeightScalePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        initializeViews(view);
        return view;

    }

    private void initializeViews(View view){
        fragmentWeightTextWeight = view.findViewById(R.id.fragmentWeightTextWeight);
        fragmentWeightScalePicker = view.findViewById(R.id.fragmentWeightScalePicker);
        fragmentWeightTextWeight.setText(String.valueOf(fragmentWeightScalePicker.getCurrentValue()));
        fragmentWeightScalePicker.setOnValueChangedListener(new RuleView.OnValueChangedListener() {
            @Override
            public void onValueChanged(float value) {
                fragmentWeightTextWeight.setText(String.valueOf(value));
            }
        });


    }


}