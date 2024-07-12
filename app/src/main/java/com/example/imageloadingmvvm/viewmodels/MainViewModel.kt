package com.example.imageloadingmvvm.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imageloadingmvvm.data.DataResponseItem
import com.example.imageloadingmvvm.data.Repository
import com.example.imageloadingmvvm.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository,application: Application):BaseViewModel(application) {
    private val _response: MutableLiveData<NetWorkResult<List<DataResponseItem>>> = MutableLiveData()
    val response: LiveData<NetWorkResult<List<DataResponseItem>>> = _response

    fun getDataList() = viewModelScope.launch {
        repository.getDataList(context).collect { values ->
            _response.value = values
        }
    }
}