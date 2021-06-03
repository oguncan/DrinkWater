package com.ocktr.drinkwater.data.models.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ocktr.drinkwater.data.models.DrinkLog
import com.ocktr.drinkwater.data.models.database.DrinkLogDatabase
import com.ocktr.drinkwater.data.models.repository.DrinkLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinkLogViewModel(application: Application) : AndroidViewModel(application) {
    //    val profileInfo: LiveData<DrinkLog>
    private val drinkLogRepository: DrinkLogRepository
    private var myViewModel: DrinkLogViewModel? = null

    @Synchronized
    fun getInstance(): DrinkLogViewModel? {
        if (myViewModel == null) {
            myViewModel = DrinkLogViewModel(getApplication())
            return myViewModel
        }
        return myViewModel
    }

    init {
        val drinkLogDao = DrinkLogDatabase.getDatabase(application).drinkLogDao()
        drinkLogRepository = DrinkLogRepository(drinkLogDao)
//        profileInfo = drinkLogRepository.profileInfo
    }

    fun insertDrinkLog(drinkLog: DrinkLog) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkLogRepository.insertDrinkLog(drinkLog);
        }
    }

    fun getAllDrinkLog(): LiveData<DrinkLog>? {
        return drinkLogRepository.allDrinkLog;
    }

    fun getTodayDrinkLogs(todayDate : String): LiveData<List<DrinkLog>>? {
        return drinkLogRepository.getTodayDrinkLogs(todayDate)
    }

}