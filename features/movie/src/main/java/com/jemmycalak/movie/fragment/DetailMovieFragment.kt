package com.jemmycalak.movie.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jemmycalak.common.base.BaseFragment
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.movie.adapter.ReviewAdapter
import com.jemmycalak.movie.databinding.FragmentDetailMovieBinding
import com.jemmycalak.movie.viewmodel.DetailMovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieFragment : BaseFragment() {

    val args: DetailMovieFragmentArgs by navArgs()

    val vModel: DetailMovieViewModel by viewModel()
    lateinit var binding: FragmentDetailMovieBinding

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
        initUI()
        setObserver()
        vModel.getModel(args.model.id)
    }

    private fun setObserver() {
        vModel.trailerMovieData.observe(this, Observer {
            it.data?.results?.first()?.key?.let { key->
                initVideoTrailer(key)
            }
        })
    }

    private fun initUI() {
        with(binding) {
            toolbar.apply {
                init()
                title = args.model.title
            }
            btnPlay.apply {
                setOnClickListener {
                    webview.visibility = View.VISIBLE
                    visibility = View.GONE
                    image.visibility = View.GONE
                }
            }
            recyclerViewReview.adapter = ReviewAdapter()
        }
    }

    private fun initVideoTrailer(videoId: String) {
        with(binding) {
            webview.apply {
                val videoStr = getHTMLData(videoId, args.model.title)
                val newwebViewClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        if(newProgress == 100){
                            implementClickEvent()
                        }
                    }
                }
                webChromeClient = newwebViewClient
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                loadData(videoStr, "text/html", "utf-8")
            }
        }
    }
    private fun implementClickEvent() {
        val x = 250 // X coordinate of the click
        val y = 250 // Y coordinate of the click

        val eventDown = MotionEvent.obtain(
            SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
            MotionEvent.ACTION_DOWN, x.toFloat(), y.toFloat(), 0
        )

        val eventUp = MotionEvent.obtain(
            SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
            MotionEvent.ACTION_UP, x.toFloat(), y.toFloat(), 0
        )

        // Dispatch the events
        binding.webview.dispatchTouchEvent(eventDown)
        binding.webview.dispatchTouchEvent(eventUp)

        eventDown.recycle()
        eventUp.recycle()
    }

    private fun getHTMLData(
        videoId: String,
        titleVideo: String?
    ): String {
        return "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" title=\"$titleVideo\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
    }

}