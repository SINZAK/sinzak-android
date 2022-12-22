package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteImageBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
class ImageFragment : BaseFragment() {

    lateinit var bind : FragmentWriteImageBinding
    override fun getFragmentRoot(): View {
        bind = FragmentWriteImageBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean = false

    override fun onFragmentCreated() {
        bind.fg = this
        bind.lifecycleOwner = this
    }


    fun moveToInfo(){
        navigator.changePage(Page.NEW_POST_INFO)
    }

}