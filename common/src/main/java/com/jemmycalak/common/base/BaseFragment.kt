package com.jemmycalak.common.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.jemmycalak.common.R
import com.jemmycalak.common.utils.showSnackbar
import com.jemmycalak.navigation.NavigationCommand

abstract class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        super.onCreate(savedInstanceState)
        //setupBackListener()
        setupObserver(getViewModel())
    }

    open fun setupObserver(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(
                        command.directions,
                        getExtras()
                    )
                    is NavigationCommand.Back -> findNavController().navigateUp()

                }
            }
        })
        viewModel.errorHandler.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                showSnackbar(
                    it.errorResponse?.message ?: "Terjadi kesalahan pada sistem",
                    Snackbar.LENGTH_LONG
                )
            }
        })
    }

    fun Toolbar.init() {
        this.setupWithNavController(findNavController())
        this.setNavigationOnClickListener {
            onBackEvent()
        }
        this.setNavigationIcon(R.drawable.ic_arrow_left)
    }

    private fun setupBackListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackEvent()
                }
            })
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    abstract fun getViewModel(): BaseViewModel

    open fun onBackEvent() {
        findNavController().navigateUp()
    }
}