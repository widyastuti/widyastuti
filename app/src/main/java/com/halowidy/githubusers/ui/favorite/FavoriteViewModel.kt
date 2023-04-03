package com.halowidy.githubusers.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.halowidy.githubusers.data.local.FavoriteUser
import com.halowidy.githubusers.data.local.FavoriteUserDao
import com.halowidy.githubusers.data.local.UserDB

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao? = null
    private var userDb: UserDB? = null

    init {
        userDb = UserDB.getDB(application)
        userDao= userDb?.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavUser()
    }
}