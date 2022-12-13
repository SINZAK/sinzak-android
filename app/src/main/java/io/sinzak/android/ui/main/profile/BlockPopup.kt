package io.sinzak.android.ui.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import io.sinzak.android.databinding.BtmBlockPopupBinding
import io.sinzak.android.ui.base.BottomDialog

@AndroidEntryPoint
class BlockPopup : BottomDialog() {

    private lateinit var bind: BtmBlockPopupBinding


    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmBlockPopupBinding.inflate(inflater)
        bind.d = this
        bind.lifecycleOwner = viewLifecycleOwner

        return bind.root
    }

    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module{
        @Provides
        internal fun provideBottomView() : BlockPopup{
            return BlockPopup()
        }
    }
}