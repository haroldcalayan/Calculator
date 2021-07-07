package com.haroldcalayan.calculator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldcalayan.calculator.base.BaseViewModel
import com.haroldcalayan.calculator.utils.Constants
import com.haroldcalayan.calculator.utils.Constants.BUTTON_DOT_INDEX
import com.haroldcalayan.calculator.utils.Constants.BUTTON_TEXTS
import com.haroldcalayan.calculator.utils.Constants.COUNTRY_CODE
import com.haroldcalayan.calculator.utils.Constants.LANGUAGE_CODE
import com.haroldcalayan.calculator.utils.NumberUtils
import java.util.*

class MainViewModel : BaseViewModel() {

    private val _display = MutableLiveData<String>()
    val display : LiveData<String> = _display
    var userEnteredValue = StringBuilder()

    init {
        _display.postValue(Constants.INITIAL_DISPLAY)
    }

    fun onButtonClick(position: Int, text: String) {
        when (position) {
            BUTTON_DOT_INDEX -> {
                val dot = BUTTON_TEXTS[BUTTON_DOT_INDEX]
                if (userEnteredValue.isEmpty()) userEnteredValue.append("0$dot")
                else if(!userEnteredValue.contains(dot)) userEnteredValue.append(dot)
            }
            Constants.BUTTON_X_INDEX -> {
                if (userEnteredValue.isNotEmpty()) {
                    val lastCharIndex = userEnteredValue.length - 1
                    userEnteredValue.deleteAt(lastCharIndex)
                }
            }
            else -> {
                val dot = BUTTON_TEXTS[BUTTON_DOT_INDEX]
                if(userEnteredValue.contains(dot)) {
                    val indexOfDot = userEnteredValue.indexOf(dot)
                    if (userEnteredValue.substring(indexOfDot).length <= 2) {
                        userEnteredValue.append(text)
                    }
                } else {
                    userEnteredValue.append(text)
                }
            }
        }

        _display.postValue(NumberUtils.formatCurrencyWithAmount(userEnteredValue.toString(), Locale(LANGUAGE_CODE, COUNTRY_CODE)))
    }
}