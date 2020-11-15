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

    override fun equals(other: Any?): Boolean {
        if (other is LiveEvent<*>) {
            return other.content == content
        }
        return false
    }

    override fun hashCode(): Int {
        return content?.hashCode() ?: 0
    }
}

class EventObserver<T>(private val onEventUnhandled: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.getIfNotHandled()?.let { value ->
            onEventUnhandled(value)
        }
    }
}