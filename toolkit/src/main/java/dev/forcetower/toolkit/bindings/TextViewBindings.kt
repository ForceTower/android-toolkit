package dev.forcetower.toolkit.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.forcetower.toolkit.extensions.truncate

@BindingAdapter("textNumberWithK")
fun numberWithK(tv: TextView, value: Int) {
    val text = when {
        value < 1000 -> value.toString()
        value in 1000..999999 -> "${(value / 1000.0).truncate()}k"
        else -> "${(value / 1000000.0).truncate()}M"
    }
    tv.text = text
}