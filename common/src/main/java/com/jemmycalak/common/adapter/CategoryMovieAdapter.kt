package com.jemmycalak.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jemmycalak.common.databinding.ItemCategoryMovieBinding
import com.jemmycalak.model.CategoryMovie

class CategoryMovieAdapter(private val listener : (CategoryMovie) -> Unit) : ListAdapter<CategoryMovie, CategoryMovieAdapter.ViewHolder>(CategoryMovieDiff()) {

    inner class ViewHolder(val bind:ItemCategoryMovieBinding):RecyclerView.ViewHolder(bind.root) {
        fun bind(
            item: CategoryMovie,
            listener: (CategoryMovie) -> Unit,
            position: Int
        ) {
            bind.model = item
            bind.root.setOnClickListener { listener(item) }
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCategoryMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener, position)
    }
}

class CategoryMovieDiff: DiffUtil.ItemCallback<CategoryMovie>(){
    override fun areItemsTheSame(oldItem: CategoryMovie, newItem: CategoryMovie): Boolean = (oldItem.name == newItem.name)
    override fun areContentsTheSame(oldItem: CategoryMovie, newItem: CategoryMovie): Boolean = (oldItem == newItem)
}