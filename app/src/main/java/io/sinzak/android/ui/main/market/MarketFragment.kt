package io.sinzak.android.ui.main.market

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.*
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.remote.dataclass.history.HistoryData
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.market.viewmodel.ArtsViewModel
import io.sinzak.android.ui.main.market.viewmodel.FilterViewModel
import io.sinzak.android.ui.main.market.viewmodel.MarketViewModel
import io.sinzak.android.ui.main.search.HistoryAdapter
import io.sinzak.android.ui.main.search.HistoryViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MarketFragment : BaseFragment() {

    private val artsViewModel : ArtsViewModel by activityViewModels()
    private val filterViewModel : FilterViewModel by activityViewModels()
    private val viewModel : MarketViewModel by activityViewModels()

    @Inject
    @HistoryViewModel.MarketHistory
    lateinit var historyViewModel : HistoryViewModel

    @Inject
    lateinit var productDetailModel: ProductDetailModel

    private lateinit var bind : FragmentMarketBinding

    @Inject
    lateinit var sortView : SortBottom

    override fun getFragmentRoot(): View {
        bind = FragmentMarketBinding.inflate(layoutInflater)
        bind.vm = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        bind.fg = this
        return bind.root
    }

    override fun navigateOnBackPressed() {
        if(viewModel.searchPageOn.value)
            viewModel.closeSearchPage()
    }

    override fun onFragmentCreated() {
        filterViewModel.loadCategory()
        viewModel.getMarketProductRemote(refresh = true)
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }


    /**************v
     *
     * VIEW INLFATING
     *
     ***************/

    private fun inflateChild(){
        inflateArts()
        inflateAppbar()
        inflateFilter()
        inflateSearchBar()

        inflateHistory()
    }

    private fun inflateAppbar(){
        DataBindingUtil.inflate<ViewMainTopAppbarBinding>(layoutInflater, R.layout.view_main_top_appbar,null,false).apply{
            lifecycleOwner = viewLifecycleOwner
            LogDebug(javaClass.name,"INFLATE APPBAR")
            bind.flAppbar.addView(root)
            marketVM = viewModel
        }
    }

    private fun inflateFilter(){
        ViewMarketFilterBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = filterViewModel
            bind.flFilter.addView(root)
        }
    }

    private fun inflateArts(){
        ViewMarketArtsBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = artsViewModel
            fg = this@MarketFragment
            bind.flArts.addView(root)
        }
    }

    private fun inflateSearchBar(){
        ViewMarketSearchbarBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            marketVM = viewModel
            bind.flSearchbar.addView(root)
        }
    }

    private fun inflateHistory(){
        ViewSearchHistoryBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = historyViewModel
            model = viewModel.marketHistory
            search = HistoryAdapter.OnClick { word -> viewModel.search(word) }
            delete = HistoryAdapter.OnClick { id -> viewModel.marketHistory.deleteHistory(id) }
            bind.flHistory.addView(root)
        }
    }

    fun showSortBottom()
    {
        sortView.show(requireActivity().supportFragmentManager,sortView.tag)
    }

}