package dev.forcetower.toolkit.navigation.navigator

import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider


@Suppress("UNCHECKED_CAST")
class PermissiveNavigatorProvider : NavigatorProvider() {
    init {
        addNavigator(NavGraphNavigator(this))
    }

    private val destNavigator: Navigator<out NavDestination> = object : Navigator<NavDestination>() {
        override fun navigate(
            destination: NavDestination,
            args: Bundle?,
            navOptions: NavOptions?,
            navigatorExtras: Extras?
        ): NavDestination? {
            throw IllegalStateException("navigate is not supported")
        }

        override fun createDestination(): NavDestination {
            return NavDestination("permissive")
        }

        override fun popBackStack(): Boolean {
            throw IllegalStateException("popBackStack is not supported")
        }
    }

    override fun <T : Navigator<out NavDestination>> getNavigator(name: String): T {
        return try {
            return super.getNavigator(name)
        } catch (e: java.lang.IllegalStateException) {
            destNavigator as T
        }
    }
}