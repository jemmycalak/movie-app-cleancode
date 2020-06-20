package com.jemmycalak.movie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jemmycalak.common.adapter.CategoryMovieAdapter
import com.jemmycalak.movie.R
import com.jemmycalak.movie.databinding.FragmentCategoryMovieBinding
import com.jemmycalak.movie.viewmodel.CategoryMovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryMovieFragment : BottomSheetDialogFragment() {

    val vModel : CategoryMovieViewModel by viewModel()
    lateinit var binding: FragmentCategoryMovieBinding
    lateinit var onShorting: onCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onAttachFragments(parentFragment)
        return FragmentCategoryMovieBinding.inflate(inflater, container, false).apply {
            viewModel = vModel
            binding = this
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.setOnShowListener {
            val dialog = dialog as BottomSheetDialog?
            // androidx should use: com.google.android.material.R.id.design_bottom_sheet
            var bottomSheet = dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 0
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
        binding.recyclerView.adapter = CategoryMovieAdapter {
            onShorting.onShorting(it.id)
            dismiss()
        }
    }

    interface onCallback{
        fun onShorting(key:Int)
    }

    private fun onAttachFragments(parentFragment: Fragment?) {
        try {
            onShorting = parentFragment as onCallback
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement onCallback interface")
        }
    }
}