package com.ocktr.drinkwater.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocktr.drinkwater.R;
import com.ocktr.drinkwater.fragments.intro.AboutYouFragment;
import com.ocktr.drinkwater.fragments.intro.ScheduleFragment;
import com.ocktr.drinkwater.fragments.intro.WeightFragment;
import com.ocktr.drinkwater.utils.SharedPreferencesManager;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG = IntroActivity.class.getSimpleName();

    FrameLayout introFragmentContainer;
    private Button introNextButton;
    private RelativeLayout firstIntroLayout, secondIntroLayout, thirdIntroLayout;
    private ImageView firstIntroImageView, secondIntroImageView, thirdIntroImageView;
    private TextView firstIntroTextView, secondIntroTextView, thirdIntroTextView;
    private TextView introBackButton;

    private Fragment mainFragment;
    private FragmentManager mainWindowFragmentManager;
    private FragmentTransaction mainWindowFragmentTransaction;

    AboutYouFragment aboutYouFragment;
    ScheduleFragment scheduleFragment;
    WeightFragment weightFragment;

    Bundle generalBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesManager.init(this);
        setContentView(R.layout.activity_intro);
        if(SharedPreferencesManager.getBoolean("completeIntro", false)){
            startActivity(new Intent(IntroActivity.this, SplashActivity.class));
            finish();
        }
        initializeViews();
        changeIntroViews();
    }

    private void initializeViews(){
        aboutYouFragment = new AboutYouFragment();
        scheduleFragment = new ScheduleFragment();
        weightFragment = new WeightFragment();

        introFragmentContainer = findViewById(R.id.introFragmentContainer);
        introBackButton = findViewById(R.id.introBackButton);
        introNextButton = findViewById(R.id.introNextButton);
        firstIntroLayout = findViewById(R.id.firstIntroLayout);
        secondIntroLayout = findViewById(R.id.secondIntroLayout);
        thirdIntroLayout = findViewById(R.id.thirdIntroLayout);
        firstIntroImageView = findViewById(R.id.firstIntroImageView);
        secondIntroImageView = findViewById(R.id.secondIntroImageView);
        thirdIntroImageView = findViewById(R.id.thirdIntroImageView);
        firstIntroTextView = findViewById(R.id.firstIntroTextView);
        secondIntroTextView = findViewById(R.id.secondIntroTextView);
        thirdIntroTextView = findViewById(R.id.thirdIntroTextView);

        setInitializeFragment();

    }

    private void setInitializeFragment(){
        mainWindowFragmentManager = getSupportFragmentManager();
        mainWindowFragmentTransaction = mainWindowFragmentManager.beginTransaction();

        mainWindowFragmentTransaction.addToBackStack("About You");
        mainWindowFragmentTransaction.replace(R.id.introFragmentContainer, aboutYouFragment);

        mainWindowFragmentTransaction.commit();
    }


    private void changeIntroViews(){
        introBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        introNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFragment = getSupportFragmentManager().findFragmentById(R.id.introFragmentContainer);

                if(mainFragment instanceof AboutYouFragment) {
                    View fragmentView = aboutYouFragment.getView();
                    if (fragmentView != null) {
                        TextView date = fragmentView.findViewById(R.id.fragmentAboutTextBirthDate);
                        TextView name = fragmentView.findViewById(R.id.fragmentAboutTextName);
                        if (!date.getText().toString().trim().equals(getResources().getString(R.string.select_your_date_of_birth)) &&
                                !date.getText().toString().trim().isEmpty()) {
                            if (!name.getText().toString().trim().equals(getResources().getString(R.string.enter_your_name)) &&
                                    !name.getText().toString().trim().isEmpty()) {
                                generalBundle.putString("birthDate", date.getText().toString());
                                Log.i(TAG, "onClick: "+date.getText().toString());
                                generalBundle.putString("userName", name.getText().toString());
                                Log.i(TAG, "onClick: "+name.getText().toString());
                                generalBundle.putString("gender", fragmentView.findViewById(R.id.fragmentAboutManGen).getAlpha()==1.0f ? "man" : "woman");

                                introBackButton.setVisibility(View.VISIBLE);
                                firstIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_inactive));
                                secondIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_active));
                                mainWindowFragmentTransaction = mainWindowFragmentManager.beginTransaction();
                                mainWindowFragmentTransaction.addToBackStack("Weight");
                                mainWindowFragmentTransaction.replace(R.id.introFragmentContainer, weightFragment);
                                mainWindowFragmentTransaction.commit();

                            } else {
                                Toast.makeText(IntroActivity.this, R.string.the_name_field_cannot_be_empty, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(IntroActivity.this, R.string.the_date_field_cannot_be_empty, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(mainFragment instanceof WeightFragment){
                    View fragmentView = weightFragment.getView();
                    if(fragmentView != null){
                        TextView weight = fragmentView.findViewById(R.id.fragmentWeightTextWeight);
                        generalBundle.putString("weight", weight.getText().toString());

                        Log.i(TAG, "onClick: "+weight.getText());
                    }
                    introBackButton.setVisibility(View.VISIBLE);
                    secondIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_inactive));
                    thirdIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_active));
                    mainWindowFragmentTransaction = mainWindowFragmentManager.beginTransaction();
                    mainWindowFragmentTransaction.addToBackStack("Schedule");
                    mainWindowFragmentTransaction.replace(R.id.introFragmentContainer, scheduleFragment);
                    mainWindowFragmentTransaction.commit();
                }
                else if(mainFragment instanceof ScheduleFragment){
                    View fragmentView = scheduleFragment.getView();
                    if (fragmentView != null) {
                        TextView wakeUp = fragmentView.findViewById(R.id.fragmentScheduleWakeUpText);
                        TextView fallAsleep = fragmentView.findViewById(R.id.fragmentScheduleFallText);
                        TextView drinkingInterval = fragmentView.findViewById(R.id.fragmentScheduleDrinkingIntervalText);
                        if(!wakeUp.getText().toString().equals(getResources().getString(R.string.select_time))
                                && !fallAsleep.getText().toString().equals(getResources().getString(R.string.select_time))
                                && !drinkingInterval.getText().toString().equals(getResources().getString(R.string.select_interval))){
                            SharedPreferencesManager.putBoolean("completeIntro", true);
                            generalBundle.putString("wakeUp", wakeUp.getText().toString());
                            generalBundle.putString("fallAsleep", fallAsleep.getText().toString());
                            generalBundle.putString("drinkingInterval", drinkingInterval.getText().toString());

                            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                            intent.putExtras(generalBundle);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(IntroActivity.this, R.string.fields_must_be_filled, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        mainFragment = getSupportFragmentManager().findFragmentById(R.id.introFragmentContainer);

        if(mainFragment instanceof WeightFragment) {
            getSupportFragmentManager().popBackStack();
            introBackButton.setVisibility(View.GONE);
            firstIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_active));
            secondIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_inactive));
        }
        else if(mainFragment instanceof ScheduleFragment){
            getSupportFragmentManager().popBackStack();
            secondIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_active));
            thirdIntroImageView.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_drop_inactive));
        }
        else if(mainFragment instanceof AboutYouFragment){
            finish();
//            super.onBackPressed();

        }

    }
}