package com.halowidy.githubusers.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    fun addToFav(favoriteUser: FavoriteUser)

    @Query("SELECT* FROM favorite_user")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user where favorite_user.id = :id")
    fun checkUser(id: Int) : Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id ")
    fun removeFromFav(id : Int): Int
}