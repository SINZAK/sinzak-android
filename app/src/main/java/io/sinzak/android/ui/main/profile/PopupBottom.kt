package io.sinzak.android.ui.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import io.sinzak.android.databinding.BtmProfilePopupBinding
import io.sinzak.android.ui.base.BottomDialog
import javax.inject.Inject

@AndroidEntryPoint
class PopupBottom : BottomDialog() {

    private lateinit var bind: BtmProfilePopupBinding

    @Inject
    lateinit var blockView : BlockPopup

    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmProfilePopupBinding.inflate(inflater)
        bind.d = this
        bind.lifecycleOwner = viewLifecycleOwner

        return bind.root
    }

    fun showBlockPopup(){
        blockView.show(requireActivity().supportFragmentManager,blockView.tag)
    }

    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module{
        @Provides
        internal fun provideBottomView() : PopupBottom{
            return PopupBottom()
        }
    }
}