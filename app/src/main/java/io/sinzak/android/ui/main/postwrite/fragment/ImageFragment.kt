package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteImageBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.postwrite.viewmodels.ImageViewModel
import io.sinzak.android.utils.FileUtil


@AndroidEntryPoint
class ImageFragment : BaseFragment() {

    lateinit var bind : FragmentWriteImageBinding

    val viewModel by activityViewModels<ImageViewModel>()

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
        bind.vm = viewModel
        bind.lifecycleOwner = this
    }


    fun moveToInfo(){
        navigator.changePage(Page.NEW_POST_INFO)
    }


    fun loadImage(){
        FileUtil.pickFromGallery(requireActivity() as BaseActivity<*>){
            viewModel.insertImg(it[0])
        }
    }

}