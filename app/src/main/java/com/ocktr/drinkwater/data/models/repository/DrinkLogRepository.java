package com.ocktr.drinkwater.data.models.repository;

import androidx.lifecycle.LiveData;

import com.ocktr.drinkwater.data.models.DrinkLog;
import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.dao.DrinkLogDao;
import com.ocktr.drinkwater.data.models.dao.ProfileDao;

import java.util.List;

public class DrinkLogRepository{
    private DrinkLogDao drinkLogDao;
    private LiveData<DrinkLog> drinkLog;

    public DrinkLogRepository(DrinkLogDao drinkLogDao) {
        this.drinkLogDao = drinkLogDao;
        this.drinkLog = drinkLogDao.getAllDrinkLog();
    }

    public LiveData<DrinkLog> getAllDrinkLog(){
        return drinkLogDao.getAllDrinkLog();
    }

    public void insertDrinkLog(DrinkLog drinkLog){
        drinkLogDao.insertDrinkLog(drinkLog);
    }

    public void deleteDrinkLog(DrinkLog drinkLog){
        drinkLogDao.deleteDrinkLog(drinkLog);
    }

    public LiveData<List<DrinkLog>> getTodayDrinkLogs(String todayDate) { return drinkLogDao.getTodayDrinkLogs(todayDate); }
}
