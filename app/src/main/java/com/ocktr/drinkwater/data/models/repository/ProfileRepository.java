package com.ocktr.drinkwater.data.models.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ocktr.drinkwater.data.models.Profile;
import com.ocktr.drinkwater.data.models.dao.ProfileDao;
import com.ocktr.drinkwater.data.models.database.ProfileDatabase;

public class ProfileRepository {

    private ProfileDao profileDao;
    private LiveData<Profile> profile;

    public ProfileRepository(ProfileDao profileDao) {
        this.profileDao = profileDao;
        this.profile = profileDao.getProfileInfo();
    }

    public LiveData<Profile> getProfileInfo(){
        return profileDao.getProfileInfo();
    }

    public void insertProfile(Profile profile){
        profileDao.insertProfile(profile);
    }

    public void deleteProfile(Profile profile){
        profileDao.deleteProfile(profile);
    }

    public void updateProfile(Profile profile){
        profileDao.updateProfile(profile);
    }

}
