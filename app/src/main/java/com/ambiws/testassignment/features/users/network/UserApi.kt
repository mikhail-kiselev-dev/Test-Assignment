package com.ambiws.testassignment.features.users.network

import com.ambiws.testassignment.core.utils.Constants.API_PATH
import com.ambiws.testassignment.features.users.data.response.UserResponse
import retrofit2.http.GET

private const val USERS = "users"

interface UserApi {

    @GET(API_PATH + USERS)
    suspend fun getUsers(): List<UserResponse>
}
