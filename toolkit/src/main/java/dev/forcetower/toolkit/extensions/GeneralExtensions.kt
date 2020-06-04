package dev.forcetower.toolkit.extensions

import android.content.Context
import android.util.TypedValue
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun Context.getPixelsFromDp(dp: Int): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)