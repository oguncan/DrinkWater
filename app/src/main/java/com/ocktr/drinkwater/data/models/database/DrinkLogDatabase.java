package com.ocktr.drinkwater.data.models.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ocktr.drinkwater.data.models.DrinkLog;
import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.dao.DrinkLogDao;
import com.ocktr.drinkwater.data.models.dao.ProfileDao;

@Database(entities = DrinkLog.class, version = 1, exportSchema = false)
public abstract class DrinkLogDatabase extends RoomDatabase {

    private static DrinkLogDatabase database;

    public static synchronized DrinkLogDatabase getDatabase(Context context){
        if(database == null){
            database = Room.databaseBuilder(
                    context, DrinkLogDatabase.class,
                    "drink_log_db"
            ).build();
        }
        return database;
    }

    public abstract DrinkLogDao drinkLogDao();
}
