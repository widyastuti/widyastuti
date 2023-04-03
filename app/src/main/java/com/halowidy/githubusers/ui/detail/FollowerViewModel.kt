package com.halowidy.githubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halowidy.githubusers.User
import com.halowidy.githubusers.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel(){
    val listfollower = MutableLiveData<ArrayList<User>>()

    fun setListFollower(username: String){
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if( response.isSuccessful){
                        listfollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })

    }
        fun getListfollower(): LiveData<ArrayList<User>>{
            return listfollower
        }
}