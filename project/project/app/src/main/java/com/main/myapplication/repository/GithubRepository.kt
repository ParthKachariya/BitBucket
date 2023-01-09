package com.main.myapplication.repository

import com.main.myapplication.remote.GithubService

class GithubRepository(private val githubService: GithubService) {

    suspend fun getSearchResult(token: String, username: String) =
        githubService.getSearchResult(token, username)

    suspend fun getUserDetail(token: String, username: String) =
        githubService.getUserDetail(token, username)

    suspend fun getUserFollowers(token: String, username: String) =
        githubService.getUserFollowers(token, username)

    suspend fun getUserFollowing(token: String, username: String) =
        githubService.getUserFollowing(token, username)
}