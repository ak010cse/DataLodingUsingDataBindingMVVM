package com.example.imageloadingmvvm.data.remote

import org.json.JSONObject
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getData() = apiService.getData()
}