package com.example.imageloadingmvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadingmvvm.R
import com.example.imageloadingmvvm.data.DataResponseItem
import com.example.imageloadingmvvm.databinding.PostListItemBinding
import com.squareup.picasso.Picasso

class PhotosListAdapter () : RecyclerView.Adapter<PhotosListAdapter.DataViewHolder>() {

    private lateinit var binding: PostListItemBinding
    var postList = arrayListOf<DataResponseItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = postList[position]
        holder.bind(dataModel)
    }

    override fun getItemCount(): Int = postList.size

    inner class DataViewHolder(private val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: DataResponseItem) {
            try {
                binding.post = post
                binding.executePendingBindings()
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    companion object{

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun bindUrlImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(view)
            } else {
                view.setImageBitmap(null)
            }
        }
    }

    fun setPosts(data: List<DataResponseItem>) {
        try {
            postList.clear()
            if (data.isNotEmpty()) {
                postList.addAll(data)
            }
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}