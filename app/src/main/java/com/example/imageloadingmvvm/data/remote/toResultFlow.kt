package com.example.imageloadingmvvm.data.remote

import android.content.Context
import com.example.imageloadingmvvm.utils.Constants.Companion.API_FAILED_CODE
import com.example.imageloadingmvvm.utils.Constants.Companion.API_INTERNET_CODE
import com.example.imageloadingmvvm.utils.Constants.Companion.API_INTERNET_MESSAGE
import com.example.imageloadingmvvm.utils.NetWorkResult
import com.example.imageloadingmvvm.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import kotlin.reflect.KMutableProperty

inline fun <reified T> toResultFlow(context: Context, crossinline call: suspend () -> Response<T>?): Flow<NetWorkResult<T>> {
    return flow {
        val isInternetConnected = Utils.hasInternetConnection(context)

        if (isInternetConnected){
            emit(NetWorkResult.Loading(true))
            val  c = call()
            c?.let { response ->
                try {
                    if (c.isSuccessful && c.body() != null){
                        c.body()?.let {
                            emit(NetWorkResult.Success(it))
                        }
                    }else{
                        val model = setResponseStatus<T>(T::class.java.getDeclaredConstructor().newInstance(),API_FAILED_CODE,response.message())
                        emit(NetWorkResult.Error(model, response.message()))

                    }

                }catch (e : Exception){
                    val model = setResponseStatus<T>(T::class.java.getDeclaredConstructor().newInstance(),API_FAILED_CODE,response.message())
                    emit(NetWorkResult.Error(model, response.message()))
                }
            }
        }else{
            try {
                val model = setResponseStatus<T>(T::class.java.getDeclaredConstructor().newInstance(),
                    API_INTERNET_CODE, API_INTERNET_MESSAGE)
                emit(NetWorkResult.Error(model, API_INTERNET_MESSAGE))
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }.flowOn(Dispatchers.IO)
}
inline fun <reified T> setResponseStatus(instance: T?, errorCode: String?, message: String?):T? {
    return try {
        instance?.let {
            val properties = it::class.members
            for (property in properties) {
                if (property is KMutableProperty<*>) {
                    when (property.name) {
                        "ErrorCode" -> property.setter.call(instance, errorCode)
                        "Message" -> property.setter.call(instance, message)
                    }
                }
            }
        }
        instance
    } catch (e: Exception) {
        null
    }
}
