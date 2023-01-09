package com.main.myapplication.utils

import com.main.myapplication.model.SearchResponseModel
import com.main.myapplication.model.UserDetailModel
import com.main.myapplication.model.UserFollowersModel

sealed class ResponseSearch<T>(
    val msg: String? = null,
    val data: SearchResponseModel? = null,
) {

    class Loading : ResponseSearch<Nothing>()

    class Success<T>(data: SearchResponseModel?) : ResponseSearch<T>(data = data)

    class Failed(msg: String?) : ResponseSearch<Nothing>(msg = msg)

    class UnAuthorized : ResponseSearch<Nothing>()
}

sealed class ResponseUser<T>(
    val msg: String? = null,
    val data: UserDetailModel? = null,
) {

    class Loading : ResponseUser<Nothing>()

    class Success<T>(data: UserDetailModel?) : ResponseUser<T>(data = data)

    class Failed(msg: String?) : ResponseUser<Nothing>(msg = msg)

    class UnAuthorized : ResponseUser<Nothing>()
}

sealed class ResponseFollowers<T>(
    val msg: String? = null,
    val data: UserFollowersModel? = null,
) {

    class Loading : ResponseFollowers<Nothing>()

    class Success<T>(data: UserFollowersModel?) : ResponseFollowers<T>(data = data)

    class Failed(msg: String?) : ResponseFollowers<Nothing>(msg = msg)

    class UnAuthorized : ResponseFollowers<Nothing>()
}

