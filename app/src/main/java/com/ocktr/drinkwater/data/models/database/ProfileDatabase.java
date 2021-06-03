package com.ocktr.drinkwater.data.models.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.dao.ProfileDao;


@Database(entities = Profile.class, version = 1, exportSchema = false)
public abstract class ProfileDatabase extends RoomDatabase {

    private static ProfileDatabase database;

    public static synchronized ProfileDatabase getDatabase(Context context){
        if(database == null){
            database = Room.databaseBuilder(
                    context, ProfileDatabase.class,
                    "profile_db"
            ).build();
        }
        return database;
    }

    public abstract ProfileDao profileDao();




}
