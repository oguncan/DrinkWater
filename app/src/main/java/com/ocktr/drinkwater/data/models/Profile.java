package com.ocktr.drinkwater.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class Profile {

    @PrimaryKey(autoGenerate = false)
    private int profileId;

    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="weight")
    private float weight;
    @ColumnInfo(name="dateOfBirth")
    private String dateOfBirth;
    @ColumnInfo(name="gender")
    private String gender;
    @ColumnInfo(name="wakeUp")
    private String wakeUp;
    @ColumnInfo(name="fallAsleep")
    private String fallAsleep;
    @ColumnInfo(name="drinkingInterval")
    private String drinkingInterval;
    @ColumnInfo(name="drinkingGoal")
    private int drinkingGoal;

    public Profile(int profileId, String name, float weight, String dateOfBirth, String gender, String wakeUp, String fallAsleep, String drinkingInterval, int drinkingGoal) {
        this.profileId = profileId;
        this.name = name;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.wakeUp = wakeUp;
        this.fallAsleep = fallAsleep;
        this.drinkingInterval = drinkingInterval;
        this.drinkingGoal = drinkingGoal;
    }

    public int getDrinkingGoal() {
        return drinkingGoal;
    }

    public void setDrinkingGoal(int drinkingGoal) {
        this.drinkingGoal = drinkingGoal;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(String wakeUp) {
        this.wakeUp = wakeUp;
    }

    public String getFallAsleep() {
        return fallAsleep;
    }

    public void setFallAsleep(String fallAsleep) {
        this.fallAsleep = fallAsleep;
    }

    public String getDrinkingInterval() {
        return drinkingInterval;
    }

    public void setDrinkingInterval(String drinkingInterval) {
        this.drinkingInterval = drinkingInterval;
    }
}
