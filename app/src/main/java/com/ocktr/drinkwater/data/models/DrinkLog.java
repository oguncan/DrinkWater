package com.ocktr.drinkwater.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drink_log")
public class DrinkLog {

    @PrimaryKey(autoGenerate = true)
    private int log_id;

    @ColumnInfo(name = "drink_name")
    private String drink_name;

    @ColumnInfo(name = "drink_amount")
    private long drink_amount;

    @ColumnInfo(name = "drink_actual_amount")
    private long drink_actual_amount;

    @ColumnInfo(name = "drinking_date")
    private String drinking_date;

    @ColumnInfo(name = "drinking_hour")
    private long drinking_hour;

    public DrinkLog(String drink_name, long drink_amount, long drink_actual_amount, String drinking_date, long drinking_hour) {
        this.drink_name = drink_name;
        this.drink_amount = drink_amount;
        this.drink_actual_amount = drink_actual_amount;
        this.drinking_date = drinking_date;
        this.drinking_hour = drinking_hour;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }

    public long getDrink_amount() {
        return drink_amount;
    }

    public void setDrink_amount(long drink_amount) {
        this.drink_amount = drink_amount;
    }

    public long getDrink_actual_amount() {
        return drink_actual_amount;
    }

    public void setDrink_actual_amount(long drink_actual_amount) {
        this.drink_actual_amount = drink_actual_amount;
    }

    public String getDrinking_date() {
        return drinking_date;
    }

    public void setDrinking_date(String drinking_date) {
        this.drinking_date = drinking_date;
    }

    public long getDrinking_hour() {
        return drinking_hour;
    }

    public void setDrinking_hour(long drinking_hour) {
        this.drinking_hour = drinking_hour;
    }
}
