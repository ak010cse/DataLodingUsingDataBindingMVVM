package com.example.imageloadingmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.imageloadingmvvm.R
import com.example.imageloadingmvvm.data.ApiResultHandler
import com.example.imageloadingmvvm.data.DataResponseItem
import com.example.imageloadingmvvm.databinding.ActivityMainBinding
import com.example.imageloadingmvvm.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var photosListAdapter: PhotosListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mainBinding.lifecycleOwner = this

        init()
        getData()
        observePostData()
    }
    private fun init() {
        try {
            photosListAdapter = PhotosListAdapter()
            mainBinding.list.apply { adapter= photosListAdapter }
            mainBinding.swipeRefreshLayout.setOnRefreshListener { getData() }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun observePostData() {
        try {
            mainViewModel.response.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<List<DataResponseItem>>(this@MainActivity,
                    onLoading = {
                        mainBinding.progress.visibility = View.VISIBLE
                    },
                    onSuccess = { data ->
                        mainBinding.progress.visibility = View.GONE
                        data?.let { photosListAdapter.setPosts(it) }
                        mainBinding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        mainBinding.progress.visibility = View.GONE
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun getData() {
        mainViewModel.getDataList()
    }
}