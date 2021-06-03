package com.ocktr.drinkwater.data.models.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ocktr.drinkwater.data.models.DrinkLog;

import java.util.List;

@Dao
public interface DrinkLogDao {

    @Query("SELECT * FROM drink_log ORDER BY drinking_hour DESC")
    LiveData<DrinkLog> getAllDrinkLog();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDrinkLog(DrinkLog drinkLog);

    @Delete
    void deleteDrinkLog(DrinkLog drinkLog);

    @Query("SELECT * FROM drink_log WHERE drinking_date = :todayDate")
    LiveData<List<DrinkLog>> getTodayDrinkLogs(String todayDate);



}
