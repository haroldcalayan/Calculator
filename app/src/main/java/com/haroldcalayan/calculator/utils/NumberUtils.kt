package com.haroldcalayan.calculator.utils

import com.haroldcalayan.calculator.utils.Constants.CURRENCY
import java.text.NumberFormat
import java.util.*

object NumberUtils {

    fun formatCurrencyWithAmount(value: String, locale: Locale) : String {
        if (value.isNullOrEmpty()) return Constants.INITIAL_DISPLAY
        val cleanValue = value.replace(",", "")
        val formatter = NumberFormat.getCurrencyInstance(Locale.ENGLISH)
        formatter.currency = Currency.getInstance(locale)
        val formattedNumber = formatter.format(cleanValue.toBigDecimal())
        return formattedNumber.replace(CURRENCY, "$CURRENCY ")
    }
}