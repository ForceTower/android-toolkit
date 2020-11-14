package dev.forcetower.toolkit.lifecycle

import androidx.lifecycle.Observer

open class LiveEvent<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peek(): T = content
}

class EventObserver<T>(private val onEventUnhandled: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.getIfNotHandled()?.let { value ->
            onEventUnhandled(value)
        }
    }
}