package com.ocktr.drinkwater.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ocktr.drinkwater.R;

import java.lang.reflect.Field;

import static android.app.Activity.RESULT_OK;
import static com.ocktr.drinkwater.fragments.intro.AboutYouFragment.REQUEST_NAME_CODE;

public class DrinkIntervalDialog extends AppCompatDialogFragment {

    NumberPicker numberPickerHour;
    NumberPicker numberPickerMinute;
    Button drinkIntervalDialogOKButton;


    NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            int temp = value * 15;
            return "" + String.format("%02d",temp);
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_schedule_drink_interval, null);
        alertDialogBuilder.setView(view);
        numberPickerHour = view.findViewById(R.id.numberPickerHour);

        numberPickerMinute = view.findViewById(R.id.numberPickerMinute);
        setNumberPickers();

        drinkIntervalDialogOKButton = view.findViewById(R.id.drinkIntervalDialogOKButton);
        drinkIntervalDialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("intervalHour", numberPickerHour.getValue());
                intent.putExtra("intervalMinute", numberPickerMinute.getValue());
                getTargetFragment().onActivityResult(REQUEST_NAME_CODE, RESULT_OK, intent);
                dismiss();
            }
        });



        return alertDialogBuilder.create();
    }

    private void setNumberPickers(){
        //Minute Picker
        numberPickerHour.setMinValue(1);
        numberPickerHour.setMaxValue(12);
        numberPickerHour.setValue(1);
        try {
            Field field = NumberPicker.class.getDeclaredField("mInputText");
            field.setAccessible(true);
            EditText inputText = (EditText) field.get(numberPickerHour);
            inputText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Second Picker
        numberPickerMinute.clearFocus();
        numberPickerMinute.setMinValue(0);
        numberPickerMinute.setMaxValue(3);
        numberPickerMinute.setValue(3);
        numberPickerMinute.setFormatter(formatter);
        try {
            Field field = NumberPicker.class.getDeclaredField("mInputText");
            field.setAccessible(true);
            EditText inputText = (EditText) field.get(numberPickerMinute);
            inputText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
