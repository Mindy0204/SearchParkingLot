package com.mindyhsu.searchparkinglot.ext

import androidx.fragment.app.Fragment
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.factory.ViewModelFactory

/**
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as AppApplication).repository
    return ViewModelFactory(repository)
}
