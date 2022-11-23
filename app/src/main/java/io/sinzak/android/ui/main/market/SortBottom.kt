package io.sinzak.android.ui.main.market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import io.sinzak.android.databinding.BtmMarketSortBinding
import io.sinzak.android.ui.base.BottomDialog
import io.sinzak.android.ui.main.market.viewmodel.SortViewModel
import javax.inject.Singleton

@AndroidEntryPoint
class SortBottom : BottomDialog() {

    private lateinit var bind : BtmMarketSortBinding

    private val viewModel : SortViewModel by activityViewModels()


    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmMarketSortBinding.inflate(inflater)
        bind.vm = viewModel
        bind.d = this
        bind.lifecycleOwner = viewLifecycleOwner

        return bind.root
    }


    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module{
        @Provides
        internal fun provideBottomView() : SortBottom{
            return SortBottom()
        }
    }

}