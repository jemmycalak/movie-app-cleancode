package com.jemmycalak.favoritmovie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemmycalak.common.adapter.MovieAdapter
import com.jemmycalak.common.base.BaseFragment
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.utils.Constants.TAG_LISTMOVIE_FAVORIT_FRAGMENT
import com.jemmycalak.favoritmovie.R
import com.jemmycalak.favoritmovie.databinding.FragmentListFavoritMovieBinding
import com.jemmycalak.favoritmovie.viewmodel.FavoriteMovieViewModel
import com.jemmycalak.movie.fragment.ListMovieFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class ListFavoritMovieFragment : BaseFragment() {

    val vModel : FavoriteMovieViewModel by viewModel()
    lateinit var binding:FragmentListFavoritMovieBinding

    override fun getViewModel(): BaseViewModel = vModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentListFavoritMovieBinding.inflate(inflater, container, false).apply {
            viewModel = vModel
            binding = this
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.init()
        binding.toolbar.title = getString(R.string.label_favoritemovie)
        binding.recyclerView.adapter = MovieAdapter(vModel, TAG_LISTMOVIE_FAVORIT_FRAGMENT){ vModel.onDetailMovie(it) }

    }

}