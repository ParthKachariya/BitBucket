package com.main.myapplication.remote

import com.main.myapplication.constant.Constant
import com.main.myapplication.model.SearchResponseModel
import com.main.myapplication.model.UserDetailModel
import com.main.myapplication.model.UserFollowersModel
import retrofit2.http.*

interface GithubService {

    @GET(Constant.SEARCH_USER)
    suspend fun getSearchResult(
        @Header("Authorization") authorization: String,
        @Query("q") username: String
    ): SearchResponseModel

    @GET(Constant.USER_DETAIL)
    suspend fun getUserDetail(
        @Header("Authorization") authorization: String,
        @Path("username") username: String
    ): UserDetailModel

    @GET(Constant.USER_DETAIL_FOLLOWERS)
    suspend fun getUserFollowers(
        @Header("Authorization") authorization: String,
        @Path("username") username: String
    ): UserFollowersModel

    @GET(Constant.USER_DETAIL_FOLLOWING)
    suspend fun getUserFollowing(
        @Header("Authorization") authorization: String,
        @Path("username") username: String
    ): UserFollowersModel
}