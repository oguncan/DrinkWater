package com.ocktr.drinkwater.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderKt;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.ocktr.drinkwater.R;
import com.ocktr.drinkwater.data.models.DrinkLog;
import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.viewmodels.DrinkLogViewModel;
import com.ocktr.drinkwater.data.models.viewmodels.ProfileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.grabner.circleprogress.CircleProgressView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_CODE_UPDATE_PROFILE = 100;
    private TextView drinkWaterRatioText, drinkWaterPercentText;
    private ImageView addDrinkAmountButton;
    private Toolbar homeActivityToolbar;
    private DrawerLayout drawerLayout;
    private int navigation_id = -1;
    CircleProgressView waterDrinkProgressBar;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;
    List<DrinkLog> drinkLogList = new ArrayList<>();
    private Profile actualProfile;

    private ProfileViewModel profileViewModel;
    private DrinkLogViewModel drinkLogViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        drinkLogViewModel = ViewModelProviders.of(this).get(DrinkLogViewModel.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String birthDate = bundle.getString("birthDate");
            String userName = bundle.getString("userName");
            String gender = bundle.getString("gender");
            String weight = bundle.getString("weight");
            String wakeUp = bundle.getString("wakeUp");
            String fallAsleep = bundle.getString("fallAsleep");
            String drinkingInterval = bundle.getString("drinkingInterval");
            int drinkGoal = (int) ((Float.valueOf(weight) * 0.03f) * 1000);
            Profile profile = new Profile(0, userName, Float.parseFloat(weight), birthDate, gender, wakeUp, fallAsleep, drinkingInterval, drinkGoal);
            //ProfileDatabase.getDatabase(getApplicationContext()).profileDao().insertProfile(profile);
            profileViewModel.insertProfile(profile);
        }
        initializeViews();
//        getProfile();
    }

    private void performDrawerNavigation(){
        if(navigation_id == R.id.nav_history){
            Intent activityIntent = new Intent(getApplicationContext(), HistoryActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activityIntent);
            overridePendingTransition(0, 0);
        }
        else if(navigation_id == R.id.nav_stats){
            Intent activityIntent = new Intent(getApplicationContext(), StatsActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activityIntent);
            overridePendingTransition(0, 0);
        }
        else if(navigation_id == R.id.nav_reminder){
            Intent activityIntent = new Intent(getApplicationContext(), RemindersActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activityIntent);
            overridePendingTransition(0, 0);
        }
    }

    private void initializeViews(){
        Date date = new Date();
        String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        addDrinkAmountButton = findViewById(R.id.addDrinkAmountButton);
        drinkWaterRatioText = findViewById(R.id.drinkWaterRatioText);
        drinkWaterPercentText = findViewById(R.id.drinkWaterPercentText);
        waterDrinkProgressBar = findViewById(R.id.waterDrinkProgressBar);
        // you can set max and current progress values individually

        homeActivityToolbar = findViewById(R.id.homeActivityToolbar);
        setSupportActionBar(homeActivityToolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, homeActivityToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(navigation_id != -1){
                    performDrawerNavigation();
                }
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addDrinkAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] drinkType = {1};
                //Date
                Date currentTime = Calendar.getInstance().getTime();
                //Hour and Minute
                Time dtNow = new Time();
                dtNow.setToNow();
                int hours = dtNow.hour;
                String lsNow = dtNow.format("%H:%M");
                //                Log.i(TAG, "onClick: "+dtNow.parse(lsNow));
                Date date = new Date();
                String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                //Aşağıdaki satırda bulunan ifade ile birlikte, Room Database içerisine saat bilgisini gömmüş bulunacağız.
//                Calendar time = Calendar.getInstance();
//                time.setTimeInMillis(System.currentTimeMillis());
//                Log.i(TAG, "onClick: "+(int)time.get(Calendar.HOUR));
//                Log.i(TAG, "onClick: "+time.get(Calendar.MINUTE));

                long currentTimeMillis = System.currentTimeMillis();

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        HomeActivity.this, R.style.BottomSheetDialogTheme
                );
                bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_add_drink_dialog,
                        findViewById(R.id.bottomSheetDialogContainer)
                );

                LinearLayout layoutScrollDrinkWater = bottomSheetView.findViewById(R.id.layoutScrollDrinkWater);
                LinearLayout layoutScrollDrinkCoffee = bottomSheetView.findViewById(R.id.layoutScrollDrinkCoffee);
                LinearLayout layoutScrollDrinkTea = bottomSheetView.findViewById(R.id.layoutScrollDrinkTea);
                LinearLayout layoutScrollDrinkMilk = bottomSheetView.findViewById(R.id.layoutScrollDrinkMilk);
                LinearLayout layoutScrollDrinkFruitJuice = bottomSheetView.findViewById(R.id.layoutScrollDrinkFruitJuice);
                LinearLayout layoutScrollDrinkSoda = bottomSheetView.findViewById(R.id.layoutScrollDrinkSoda);
                LinearLayout layoutScrollDrinkAddDifferent = bottomSheetView.findViewById(R.id.layoutScrollDrinkAddDifferent);

                TextView layoutScrollDrinkWaterText = bottomSheetView.findViewById(R.id.layoutScrollDrinkWaterText);
                TextView layoutScrollDrinkCoffeeText = bottomSheetView.findViewById(R.id.layoutScrollDrinkCoffeeText);
                TextView layoutScrollDrinkTeaText = bottomSheetView.findViewById(R.id.layoutScrollDrinkTeaText);
                TextView layoutScrollDrinkMilkText = bottomSheetView.findViewById(R.id.layoutScrollDrinkMilkText);
                TextView layoutScrollDrinkFruitJuiceText = bottomSheetView.findViewById(R.id.layoutScrollDrinkFruitJuiceText);
                TextView layoutScrollDrinkSodaText = bottomSheetView.findViewById(R.id.layoutScrollDrinkSodaText);

                Button layoutAddDrinkDialogDateButton = bottomSheetView.findViewById(R.id.layoutAddDrinkDialogDateButton);
                Button layoutAddDrinkDialogTimeButton = bottomSheetView.findViewById(R.id.layoutAddDrinkDialogTimeButton);

                layoutAddDrinkDialogDateButton.setText(todayDate);
                layoutAddDrinkDialogTimeButton.setText(lsNow);

                NumberPicker layoutAddDrinkDialogNumberPicker = bottomSheetView.findViewById(R.id.layoutAddDrinkDialogNumberPicker);
                layoutAddDrinkDialogNumberPicker.setFormatter(formatter);
                layoutAddDrinkDialogNumberPicker.setValue(20);
                layoutAddDrinkDialogNumberPicker.setMaxValue(500);
                layoutAddDrinkDialogNumberPicker.setMinValue(1);

                bottomSheetView.findViewById(R.id.layoutAddDrinkDialogOKButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        addDrinkLog(layoutAddDrinkDialogNumberPicker.getValue() * 10, todayDate, currentTimeMillis, getDrinkTypeName(drinkType[0]));
                    }
                });

                layoutScrollDrinkCoffee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 2;
                        //Set Actual Layout
                        layoutScrollDrinkCoffee.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.coffee_background));
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts background colors
                        layoutScrollDrinkWater.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkTea.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkMilk.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkFruitJuice.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkSoda.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    }
                });

                layoutScrollDrinkWater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 1;
                        //Set Actual Layout
                        layoutScrollDrinkWater.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.water_background));
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts
                        layoutScrollDrinkCoffee.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkTea.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkMilk.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkFruitJuice.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkSoda.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    }
                });

                layoutScrollDrinkTea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 3;
                        //Set Actual Layout
                        layoutScrollDrinkTea.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.tea_background));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts
                        layoutScrollDrinkCoffee.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkWater.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkMilk.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkFruitJuice.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkSoda.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    }
                });

                layoutScrollDrinkMilk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 4;
                        //Set Actual Layout
                        layoutScrollDrinkMilk.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.milk_background));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts
                        layoutScrollDrinkCoffee.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkWater.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkTea.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkFruitJuice.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkSoda.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    }
                });

                layoutScrollDrinkFruitJuice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 5;
                        //Set Actual Layout
                        layoutScrollDrinkFruitJuice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.fruit_background));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts
                        layoutScrollDrinkCoffee.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkWater.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkTea.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkMilk.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkSoda.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    }
                });

                layoutScrollDrinkSoda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkType[0] = 6;
                        //Set Actual Layout
                        layoutScrollDrinkSoda.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.soda_background));
                        layoutScrollDrinkSodaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        //Set Other Layouts
                        layoutScrollDrinkCoffee.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkWater.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkTea.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkMilk.setBackgroundColor(Color.TRANSPARENT);
                        layoutScrollDrinkFruitJuice.setBackgroundColor(Color.TRANSPARENT);

                        //Set Other TextViews colors
                        layoutScrollDrinkWaterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkTeaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkMilkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkFruitJuiceText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        layoutScrollDrinkCoffeeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    }
                });

                Button layoutAddDrink150ml = bottomSheetView.findViewById(R.id.layoutAddDrink150ml);
                Button layoutAddDrink200ml = bottomSheetView.findViewById(R.id.layoutAddDrink200ml);
                Button layoutAddDrink250ml = bottomSheetView.findViewById(R.id.layoutAddDrink250ml);
                Button layoutAddDrink300ml = bottomSheetView.findViewById(R.id.layoutAddDrink300ml);

                layoutAddDrink150ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        addDrinkLog(150, todayDate, currentTimeMillis, getDrinkTypeName(drinkType[0]));
                    }
                });

                layoutAddDrink200ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        addDrinkLog(200, todayDate, currentTimeMillis, getDrinkTypeName(drinkType[0]));
                    }
                });

                layoutAddDrink250ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        addDrinkLog(250, todayDate, currentTimeMillis, getDrinkTypeName(drinkType[0]));
                    }
                });

                layoutAddDrink300ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        addDrinkLog(300, todayDate, currentTimeMillis, getDrinkTypeName(drinkType[0]));
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        setCircularProgressView(todayDate);
    }

    NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            int diff = value * 10;
            return "" + diff;
        }
    };


    private void addDrinkLog(long amount, String todayDate, long currentTimeMillis, String drinkType){
        drinkLogViewModel.getInstance().insertDrinkLog(
                new DrinkLog(
                        drinkType,
                        amount,
                        amount,
                        todayDate,
                        currentTimeMillis
                )
        );
        setCircularProgressView(todayDate);
    }

    private void setCircularProgressView(String todayDate){

        profileViewModel.getInstance().getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                drinkLogViewModel.getInstance().getTodayDrinkLogs(todayDate).observe(HomeActivity.this, new Observer<List<DrinkLog>>() {
                    @Override
                    public void onChanged(List<DrinkLog> drinkLogs) {
                        waterDrinkProgressBar.setMaxValue(profile.getDrinkingGoal());
                        if(drinkLogs.isEmpty()){
                            drinkWaterRatioText.setText("0" + " / " + profile.getDrinkingGoal());
                            drinkWaterPercentText.setText("0 %");
                            waterDrinkProgressBar.setValue(0);
                        }
                        else{
                            long todayDailyDrink = 0;
                            for(DrinkLog drinkLog : drinkLogs){
                                Log.i(TAG, "onChanged: "+drinkLog);
                                todayDailyDrink += drinkLog.getDrink_actual_amount();
                            }
                            drinkWaterRatioText.setText(todayDailyDrink + " / " + profile.getDrinkingGoal());
                            int percent = (int) (((double) todayDailyDrink / (double) profile.getDrinkingGoal()) * 100);
                            if(percent > 100 && todayDailyDrink > profile.getDrinkingGoal()){
                                drinkWaterPercentText.setText("100" + " %");
                                waterDrinkProgressBar.setValue(profile.getDrinkingGoal());
                            }
                            else{
                                waterDrinkProgressBar.setValue(todayDailyDrink);
                                drinkWaterPercentText.setText(String.valueOf(percent) + " %");
                            }
                        }
                    }
                });
            }
        });


//        drinkWaterRatioText.setText(String.valueOf(allDailyAmountDrink) + " / " + String.valueOf(actualProfile.getDrinkingGoal()) +" ml");
    }



    private String getDrinkTypeName(int drinkType){
        String drinkTypeName = "";
        if(drinkType == 1){
            drinkTypeName = "Water";
        }
        else if(drinkType == 2){
            drinkTypeName = "Coffee";
        }
        else if(drinkType == 3){
            drinkTypeName = "Tea";
        }
        else if(drinkType == 4){
            drinkTypeName = "Milk";
        }
        else if(drinkType == 5){
            drinkTypeName = "Fruit Juice";
        }
        else if(drinkType == 6){
            drinkTypeName = "Soda";
        }
        return drinkTypeName;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int toolbarItemId = item.getItemId();
        if(toolbarItemId == R.id.toolbar_profile){
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigation_id =item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}