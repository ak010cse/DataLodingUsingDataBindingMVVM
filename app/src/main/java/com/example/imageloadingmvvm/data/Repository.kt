package com.example.imageloadingmvvm.data

import android.content.Context
import com.example.imageloadingmvvm.data.remote.RemoteDataSource
import com.example.imageloadingmvvm.data.remote.toResultFlow
import com.example.imageloadingmvvm.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getDataList(context: Context): Flow<NetWorkResult<List<DataResponseItem>>> {
        return toResultFlow(context){
            remoteDataSource.getData()
        }
    }
}