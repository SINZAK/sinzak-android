package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteSpecBinding
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
class SpecFragment : BaseFragment() {

    lateinit var bind : FragmentWriteSpecBinding

    override fun getFragmentRoot(): View {
        bind = FragmentWriteSpecBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean = false

    override fun onFragmentCreated() {

    }
}