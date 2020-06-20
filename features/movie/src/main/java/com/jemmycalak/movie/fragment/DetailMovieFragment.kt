package com.jemmycalak.movie.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jemmycalak.common.base.BaseFragment
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.movie.BuildConfig
import com.jemmycalak.movie.R
import com.jemmycalak.movie.adapter.ReviewAdapter
import com.jemmycalak.movie.databinding.FragmentDetailMovieBinding
import com.jemmycalak.movie.viewmodel.DetailMovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieFragment : BaseFragment() {

    val args : DetailMovieFragmentArgs by navArgs()

    val vModel : DetailMovieViewModel by viewModel()
    lateinit var binding:FragmentDetailMovieBinding

    override fun getViewModel(): BaseViewModel = vModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailMovieBinding.inflate(inflater, container, false).apply {
            viewModel = vModel
            binding = this
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.init()
        binding.toolbar.title = args.model.title
        binding.recyclerViewReview.adapter = ReviewAdapter()
        vModel.getModel(args.model.id)
    }

}