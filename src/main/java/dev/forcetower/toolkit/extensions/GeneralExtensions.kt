package dev.forcetower.toolkit.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.lifecycle.MutableLiveData
import kotlin.math.floor
import kotlin.math.pow

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun Context.getPixelsFromDp(dp: Int): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)

fun Double.truncate(decimals: Int = 1): Double {
    val power = 10.0.pow(decimals.toDouble())
    return floor(this * power) / power
}

fun Context.resolveColorAttr(@AttrRes attribute: Int): Int {
    val typedValue = obtainStyledAttributes(intArrayOf(attribute))
    val color = typedValue.getColor(0, 0)
    typedValue.recycle()
    return color
}