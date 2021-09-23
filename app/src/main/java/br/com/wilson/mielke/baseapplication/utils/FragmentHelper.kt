package br.com.wilson.mielke.baseapplication.utils

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentHelper(
    @IdRes
    private val containerId: Int? = null,
    private val fragmentManager: FragmentManager) {

    @AnimRes
    @AnimatorRes
    var enterAnim = android.R.anim.fade_in

    @AnimRes
    @AnimatorRes
    var exitAnim = android.R.anim.fade_out

    @AnimRes
    @AnimatorRes
    var popEnterAnim = android.R.anim.fade_in

    @AnimRes
    @AnimatorRes
    var popExitAnim = android.R.anim.fade_out

    private val androidx.fragment.app.Fragment.name: String
        get() = this::class.java.simpleName

    fun add(fragment: Fragment, clearBackStack: Boolean = true) {
        withContainerId {
            val tag = fragment.name

            if (clearBackStack) {
                clear()
            }

            fragmentManager.beginTransaction()
                .applyAnimations()
                .add(it, fragment, tag)
                .addToBackStack(tag)
                .commit()
        }
    }

    fun pop(isLastFragmentBlock: (() -> Unit)? = null) {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            isLastFragmentBlock?.invoke()
        }
    }

    fun popTo(fragment: Fragment) {
        val destinationFragment = fragmentManager.findFragmentByTag(fragment.name)
        destinationFragment?.let {
            fragmentManager.popBackStack(it.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun popTo(index: Int) {
        if (fragmentManager.backStackEntryCount > index) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(index).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    fun popToFirst() {
        popTo(1)
    }

    fun replace(newFragment: Fragment) {
        withContainerId {
            fragmentManager.beginTransaction()
                .applyAnimations()
                .replace(it, newFragment, newFragment.name)
                .commit()
        }
    }


    fun replace(newFragment: Fragment, enterAnim: Int, exitAnim: Int) {
        withContainerId {
            fragmentManager.beginTransaction()
                .applyAnimations(enterAnim, exitAnim)
                .replace(it, newFragment, newFragment.name)
                .commit()
        }
    }

    fun remove(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .applyAnimations()
            .remove(fragment)
            .commit()
    }

    fun clear() {
        fragmentManager.apply {
            fragments.map {
                beginTransaction()
                    .remove(it)
                    .commit()
            }

            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun withContainerId(block: (Int) -> Unit) {
        containerId?.let {
            block(it)
        } ?: throw IllegalArgumentException("containerId cannot be null in order to perform this")
    }

    private fun FragmentTransaction.applyAnimations(): FragmentTransaction {
        setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        return this
    }

    private fun FragmentTransaction.applyAnimations(enterAnim: Int, exitAnim: Int): FragmentTransaction {
        setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        return this
    }

}

inline fun FragmentActivity.fragmentTransaction(@IdRes containerId: Int? = null, block: FragmentHelper.() -> Unit) {
    val fragmentHelper = FragmentHelper(containerId, supportFragmentManager)
    fragmentHelper.block()
}

inline fun Fragment.fragmentTransaction(@IdRes containerId: Int? = null, block: FragmentHelper.() -> Unit) {
    activity?.apply {
        fragmentTransaction(containerId, block)
    }
}