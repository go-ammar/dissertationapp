package com.project.collabexpense.data.remote

import com.project.collabexpense.data.remote.dto.MyDataDto
import retrofit2.http.GET

interface ApiService {

    @GET("endpoint")
    suspend fun getData(): List<MyDataDto>

}