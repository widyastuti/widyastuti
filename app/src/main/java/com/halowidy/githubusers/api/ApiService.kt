package com.halowidy.githubusers.api
import com.halowidy.githubusers.User
import com.halowidy.githubusers.UserResponse
import com.halowidy.githubusers.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
