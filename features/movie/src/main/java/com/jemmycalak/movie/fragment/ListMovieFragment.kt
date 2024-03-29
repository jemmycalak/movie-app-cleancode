package com.jemmycalak.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemmycalak.common.adapter.MovieAdapter
import com.jemmycalak.common.base.BaseFragment
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.utils.Constants.TAG_LISTMOVIE_FRAGMENT
import com.jemmycalak.movie.R
import com.jemmycalak.movie.databinding.FragmentListMovieBinding
import com.jemmycalak.movie.viewmodel.ListMovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel


interface Callback {
    fun onCategory()
}

class ListMovieFragment : BaseFragment(), Callback, CategoryMovieFragment.onCallback {

    val vModel: ListMovieViewModel by viewModel()
    lateinit var binding: FragmentListMovieBinding
    override fun getViewModel(): BaseViewModel = vModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentListMovieBinding.inflate(inflater, container, false).apply {
            binding = this
            callBack = this@ListMovieFragment
            viewModel = vModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (context as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        binding.recyclerView.apply {
            adapter = MovieAdapter(vModel, TAG_LISTMOVIE_FRAGMENT) { vModel.onDetailMovie(it) }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                    val sizeData = (recyclerView.adapter as MovieAdapter).currentList.size
                    val isLoading = vModel.loaderListMovie.value ?: false
                    val isLoadMoreExist = !isLoading &&
                            vModel.isAvailableLoadMore &&
                            linearLayoutManager != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == sizeData - 1
                    if (isLoadMoreExist) {
                        vModel.getListMovie(
                            filter = vModel.filterId,
                            pages = vModel.page+1,
                            shouldFetch = true
                        )
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorit -> vModel.onFavoritMovie()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCategory() {
        CategoryMovieFragment().show(childFragmentManager, "")
    }

    override fun onShorting(key: Int) {
        vModel.getListMovie(key, 1, true, true)
    }
}