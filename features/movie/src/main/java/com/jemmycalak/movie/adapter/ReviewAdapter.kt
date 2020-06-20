package com.jemmycalak.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jemmycalak.model.ResultReview
import com.jemmycalak.movie.databinding.ItemMovieReviewBinding

class ReviewAdapter : ListAdapter<ResultReview, ReviewAdapter.ViewHolder>(DiffReview()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMovieReviewBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val bind:ItemMovieReviewBinding):RecyclerView.ViewHolder(bind.root){
        fun bind(item: ResultReview) {
            bind.model = item
            bind.executePendingBindings()
        }
    }
}

class DiffReview():DiffUtil.ItemCallback<ResultReview>(){
    override fun areItemsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean =
        (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean =
        (oldItem == newItem)
}