package com.jemmycalak.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.databinding.ItemMovieBinding
import com.jemmycalak.model.Result

class MovieAdapter(private val viewModel: BaseViewModel,
                   private val tag:String,
                   private val listener : (Result) -> Unit )
    : ListAdapter<Result, MovieAdapter.ViewHolder>(ResultDiff())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item, tag, listener)
    }

    class ViewHolder(val bind:ItemMovieBinding):RecyclerView.ViewHolder(bind.root){
        fun bind(
            viewModel: BaseViewModel,
            model: Result,
            tag : String,
            listener: (Result) -> Unit
        ){
            bind.model = model
            bind.tag =tag
            bind.viewModel = viewModel
            bind.executePendingBindings()
            bind.root.setOnClickListener{ listener(model) }
        }
    }
}

class ResultDiff:DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = (oldItem == newItem)
}