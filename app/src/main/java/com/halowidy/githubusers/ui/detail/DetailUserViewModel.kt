package com.halowidy.githubusers.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.halowidy.githubusers.api.RetrofitClient
import com.halowidy.githubusers.data.local.FavoriteUser
import com.halowidy.githubusers.data.local.FavoriteUserDao
import com.halowidy.githubusers.data.local.UserDB
import com.halowidy.githubusers.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application){
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao? = null
    private var userDb: UserDB? = null

    init {
        userDb = UserDB.getDB(application)
        userDao= userDb?.favUserDao()
    }

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : retrofit2.Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if(response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }


            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }

    fun addToFav(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(username, id, avatarUrl)
            userDao?.addToFav(user)
        }
    }
    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
    fun removeFromFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFav(id)
        }
    }
}