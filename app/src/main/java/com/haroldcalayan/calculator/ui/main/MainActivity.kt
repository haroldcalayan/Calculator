package com.haroldcalayan.calculator.ui.main

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.haroldcalayan.calculator.R
import com.haroldcalayan.calculator.base.BaseActivity
import com.haroldcalayan.calculator.databinding.ActivityMainBinding
import com.haroldcalayan.calculator.utils.Constants.BUTTON_TEXTS
import com.haroldcalayan.calculator.utils.Constants.SCREEN_LANDSCAPE_SPAN_COUNT
import com.haroldcalayan.calculator.utils.Constants.SCREEN_LANSCAPE_MAX_LINES
import com.haroldcalayan.calculator.utils.Constants.SCREEN_PORTRAIT_MAX_LINES
import com.haroldcalayan.calculator.utils.Constants.SCREEN_PORTRAIT_SPAN_COUNT

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    ButtonAdapter.ButtonAdapterListener {

    override val layoutId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private lateinit var buttonAdapter: ButtonAdapter
    private val layoutManager = GridLayoutManager(this, SCREEN_PORTRAIT_SPAN_COUNT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observe()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateViewsBasedOrientation(newConfig.orientation)
    }

    override fun initViews() {
        super.initViews()
        initButtons()
        updateViewsBasedOrientation(resources.configuration.orientation)
        binding.textviewDisplay.movementMethod = ScrollingMovementMethod()
    }

    override fun observe() {
        super.observe()
        viewModel.display.observe(this, {
            val userInput = viewModel.userEnteredValue
            if (userInput.isEmpty()) {
                binding.textviewDisplay.text = it
            } else {
                val highlightedTextCount = userInput.length.plus(4).plus(it.filter { char -> char == ',' }.count())
                val spannable = SpannableString(it)
                spannable.setSpan(ForegroundColorSpan(Color.BLACK), 0, highlightedTextCount, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding.textviewDisplay.text = spannable
            }
        })
    }

    override fun onButtonItemClick(position: Int, text: String) {
        viewModel.onButtonClick(position, text)
    }

    private fun initButtons() {
        buttonAdapter = ButtonAdapter(BUTTON_TEXTS, this)
        binding.recyclerviewButtons.adapter = buttonAdapter
        binding.recyclerviewButtons.layoutManager = layoutManager
    }

    private fun updateViewsBasedOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.spanCount = SCREEN_LANDSCAPE_SPAN_COUNT
            binding.textviewDisplay.maxLines = SCREEN_LANSCAPE_MAX_LINES
        } else {
            layoutManager.spanCount = SCREEN_PORTRAIT_SPAN_COUNT
            binding.textviewDisplay.maxLines = SCREEN_PORTRAIT_MAX_LINES
        }
        layoutManager.requestLayout()
        buttonAdapter.notifyDataSetChanged()
    }
}