package dev.forcetower.toolkit.bindings

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("textInputError")
fun textInputError(input: TextInputEditText, @StringRes error: Int?) {
    if (error == null) {
        input.error = null
    } else {
        input.error = input.context.getString(error)
        input.requestFocus()
    }
}