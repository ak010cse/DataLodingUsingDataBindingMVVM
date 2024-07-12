package com.example.imageloadingmvvm.data.remote

import com.example.imageloadingmvvm.data.DataResponseItem
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("photos")
    suspend fun getData(): Response<List<DataResponseItem>>
}