package com.ocktr.drinkwater.data.models.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ocktr.drinkwater.data.models.Profile;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM user_profile")
    LiveData<Profile> getProfileInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(Profile profile);

    @Update
    void updateProfile(Profile profile);

    @Delete
    void deleteProfile(Profile profile);

}
