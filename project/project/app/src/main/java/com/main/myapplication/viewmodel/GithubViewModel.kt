package com.main.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.main.myapplication.constant.Constant
import com.main.myapplication.repository.GithubRepository
import com.main.myapplication.utils.ResponseFollowers
import com.main.myapplication.utils.ResponseSearch
import com.main.myapplication.utils.ResponseUser

class GithubViewModel(private val githubRepository: GithubRepository) :
    ViewModel() {

    fun getSearchData(username: String) = liveData {
        emit(ResponseSearch.Loading())

        try {
            val data = githubRepository.getSearchResult(Constant.APITOKEN, username)

            emit(ResponseSearch.Success(data))
        } catch (e: Exception) {
            emit(ResponseSearch.Failed(e.message))
        }
    }

    fun getUserDetail(username: String) = liveData {
        emit(ResponseUser.Loading())

        try {
            val data = githubRepository.getUserDetail(Constant.APITOKEN, username)

            emit(ResponseUser.Success(data))
        } catch (e: Exception) {
            emit(ResponseUser.Failed(e.message))
        }
    }

    fun getUserFollowers(username: String) = liveData {
        emit(ResponseFollowers.Loading())

        try {
            val data = githubRepository.getUserFollowers(Constant.APITOKEN, username)

            emit(ResponseFollowers.Success(data))
        } catch (e: Exception) {
            emit(ResponseFollowers.Failed(e.message))
        }
    }

    fun getUserFollowing(username: String) = liveData {
        emit(ResponseFollowers.Loading())

        try {
            val data = githubRepository.getUserFollowing(Constant.APITOKEN, username)

            emit(ResponseFollowers.Success(data))
        } catch (e: Exception) {
            emit(ResponseFollowers.Failed(e.message))
        }
    }
}