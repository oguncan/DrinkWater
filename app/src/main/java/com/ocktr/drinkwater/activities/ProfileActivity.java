package com.ocktr.drinkwater.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocktr.drinkwater.R;
import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.database.ProfileDatabase;
import com.ocktr.drinkwater.data.models.viewmodels.ProfileViewModel;
import com.ocktr.drinkwater.fragments.dialogs.AboutYouNameDialog;
import com.zjun.widget.RuleView;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = ProfileActivity.class.getSimpleName();

    private static final int REQUEST_NAME_CODE = 0;

    private ProfileViewModel profileViewModel;
    private Profile updateProfile;
    private Profile getProfile;
    private ImageView profileActivityBackButton;
    private TextView profileActivityClearButton;
    private Button profileActivityWomanButton, profileActivityManButton;
    private TextView profileActivityWeightText, profileActivityBirthText, profileActivityNameText, profileActivityYourGoalText;
    private AlertDialog dialogWeight, dialogDateOfBirth, dialogName;
    private ConstraintLayout profileActivityWeightLayout, profileActivityBirthLayout, profileActivityNameLayout;

    private DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
    }

    private void initViews(){
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileActivityBackButton = findViewById(R.id.profileActivityBackButton);
        profileActivityClearButton = findViewById(R.id.profileActivityClearButton);
        profileActivityWomanButton = findViewById(R.id.profileActivityWomanButton);
        profileActivityManButton = findViewById(R.id.profileActivityManButton);
        profileActivityWeightText = findViewById(R.id.profileActivityWeightText);
        profileActivityBirthText = findViewById(R.id.profileActivityBirthText);
        profileActivityNameText = findViewById(R.id.profileActivityNameText);
        profileActivityYourGoalText = findViewById(R.id.profileActivityYourGoalText);
        profileActivityWeightLayout = findViewById(R.id.profileActivityWeightLayout);
        profileActivityBirthLayout = findViewById(R.id.profileActivityBirthLayout);
        profileActivityNameLayout = findViewById(R.id.profileActivityNameLayout);


        profileActivityWeightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeightDialog();
            }
        });

        profileActivityBirthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBirthDialog();
            }
        });

        profileActivityNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameDialog();
            }
        });

        profileActivityBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profileActivityWomanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileActivityManButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.color.blue400));
                profileActivityManButton.setTextColor(getResources().getColor(R.color.tab_indicator_gray));
                profileActivityWomanButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.drawable_gender_selected));
                profileActivityWomanButton.setTextColor(getResources().getColor(R.color.white));
                configurePersonalInformations();
            }
        });

        profileActivityManButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileActivityWomanButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.color.blue400));
                profileActivityWomanButton.setTextColor(getResources().getColor(R.color.tab_indicator_gray));
                profileActivityManButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.drawable_gender_selected));
                profileActivityManButton.setTextColor(getResources().getColor(R.color.white));
                configurePersonalInformations();

            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 + "/" + i2 + "/" + i;
                profileActivityBirthText.setTextColor(getResources().getColor(R.color.tab_indicator_gray));
                profileActivityBirthText.setText(date);
                configurePersonalInformations();
            }
        };

        getProfile();
    }

    private void configurePersonalInformations(){
        String gender = profileActivityManButton.getCurrentTextColor() == getResources().getColor(R.color.white) ? "man" : "woman";
        updateProfile = new Profile(
                0,
                profileActivityNameText.getText().toString(),
                Float.parseFloat(profileActivityWeightText.getText().toString().split("kg")[0]),
                profileActivityBirthText.getText().toString(),
                gender,
                getProfile.getWakeUp(),
                getProfile.getFallAsleep(),
                getProfile.getDrinkingInterval(),
                (int) ((Float.parseFloat(profileActivityWeightText.getText().toString().split("kg")[0]) * 0.03f) * 1000)
        );
        profileViewModel.getInstance().updateProfile(updateProfile);
    }

    private void getProfile(){
        Log.i(TAG, "getProfile: xd");
        profileViewModel.getInstance().getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if(profile != null) {
                    getProfile = profile;
                    if (profile.getGender().equals("man")) {
                        profileActivityWomanButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.color.blue400));
                        profileActivityWomanButton.setTextColor(getResources().getColor(R.color.tab_indicator_gray));
                        profileActivityManButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.drawable_gender_selected));
                        profileActivityManButton.setTextColor(getResources().getColor(R.color.white));
                    }
                    else{
                        profileActivityManButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.color.blue400));
                        profileActivityManButton.setTextColor(getResources().getColor(R.color.tab_indicator_gray));
                        profileActivityWomanButton.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.drawable_gender_selected));
                        profileActivityWomanButton.setTextColor(getResources().getColor(R.color.white));
                    }

                    profileActivityBirthText.setText(profile.getDateOfBirth());

                    profileActivityWeightText.setText(String.valueOf(profile.getWeight() + " kg"));

                    profileActivityNameText.setText(profile.getName());

                    profileActivityYourGoalText.setText(String.valueOf(profile.getDrinkingGoal() + " ml"));
                }
            }
        });
    }

    private void showWeightDialog(){
        if(dialogWeight == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_profile_weight, findViewById(R.id.layoutProfileWeight));
            builder.setView(view);
            dialogWeight = builder.create();
            if(dialogWeight.getWindow() != null){
                dialogWeight.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            RuleView scalePicker = view.findViewById(R.id.layoutProfileWeightScalePicker);
            scalePicker.setCurrentValue(getProfile.getWeight());
            TextView weightText = view.findViewById(R.id.layoutProfileWeightTextWeight);
            weightText.setText(String.valueOf(scalePicker.getCurrentValue()));

            scalePicker.setOnValueChangedListener(new RuleView.OnValueChangedListener() {
                @Override
                public void onValueChanged(float value) {
                    weightText.setText(String.valueOf(value));
                }
            });

            view.findViewById(R.id.layoutProfileWeightOKButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogWeight.dismiss();
                    profileActivityWeightText.setText(String.valueOf(scalePicker.getCurrentValue()) + " kg");
                    configurePersonalInformations();
                }
            });
        }
        dialogWeight.show();
    }

    private void showBirthDialog(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 0, 1);
        int year = calendar.get(Calendar.YEAR);
//                    calendar.add(Calendar.YEAR, -12);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                ProfileActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                listener,
                year, month, day
        );

        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    private void showNameDialog(){
        if(dialogName == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_profile_name, findViewById(R.id.nameDialogLayout));
            builder.setView(view);
            dialogName = builder.create();
            if(dialogName.getWindow() != null){
                dialogName.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue900)));
            }


            EditText nameDialogEditText = view.findViewById(R.id.nameDialogEditText);
            nameDialogEditText.setText(profileActivityNameText.getText().toString());
            nameDialogEditText.requestFocus(profileActivityNameText.getText().length());

            Button nameDialogOKButton = view.findViewById(R.id.nameDialogOKButton);
            nameDialogOKButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogName.dismiss();
                    profileActivityNameText.setText(nameDialogEditText.getText().toString());
                    configurePersonalInformations();
                }
            });
        }
        dialogName.show();
    }

}