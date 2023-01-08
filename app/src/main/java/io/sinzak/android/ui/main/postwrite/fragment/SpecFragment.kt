package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteSpecBinding
import io.sinzak.android.databinding.ViewWriteCanvasBinding
import io.sinzak.android.databinding.ViewWriteDimensionBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.postwrite.viewmodels.SpecViewModel


@AndroidEntryPoint
class SpecFragment : BaseFragment() {

    lateinit var bind : FragmentWriteSpecBinding

    val viewModel by activityViewModels<SpecViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentWriteSpecBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean = false

    override fun onFragmentCreated() {
        bind.lifecycleOwner = viewLifecycleOwner
        bind.fg = this
        bind.vm = viewModel

        inflateCanvas()
        inflateDimension()


        invokeBooleanFlow(viewModel.model.flagBuildSuccess){
            showToast("작성 성공")
            navigator.changePage(Page.MARKET)
            viewModel.model.flagBuildSuccess.value = false
        }

    }


    fun inflateCanvas(){
        ViewWriteCanvasBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner



            bind.flCanvas.addView(root)
        }
    }

    fun inflateDimension(){
        ViewWriteDimensionBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            fg = this@SpecFragment


            bind.flDimension.addView(root)

        }
    }

}