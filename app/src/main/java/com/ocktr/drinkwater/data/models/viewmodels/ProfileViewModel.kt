package com.ocktr.drinkwater.data.models.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ocktr.drinkwater.data.models.Profile
import com.ocktr.drinkwater.data.models.database.ProfileDatabase
import com.ocktr.drinkwater.data.models.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
//    val profileInfo: LiveData<Profile>
    private val profileRepository: ProfileRepository
    private var myViewModel: ProfileViewModel? = null

    @Synchronized
    fun getInstance(): ProfileViewModel? {
        if (myViewModel == null) {
            myViewModel = ProfileViewModel(getApplication())
            return myViewModel
        }
        return myViewModel
    }

    init {
        val profileDao = ProfileDatabase.getDatabase(application).profileDao()
        profileRepository = ProfileRepository(profileDao)
//        profileInfo = profileRepository.profileInfo
    }

    fun insertProfile(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.insertProfile(profile);
        }
    }

    fun updateProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateProfile(profile);
        }
    }

    fun getProfile(): LiveData<Profile>? {
        return profileRepository.profileInfo;
    }

}