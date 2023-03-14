package io.sinzak.android.ui.main.outsourcing

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.*
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.outsourcing.viewmodel.ArtistViewModel
import io.sinzak.android.ui.main.outsourcing.viewmodel.OutsourcingViewModel
import io.sinzak.android.ui.main.search.HistoryAdapter
import io.sinzak.android.ui.main.search.HistoryViewModel
import javax.inject.Inject


@AndroidEntryPoint
class OutsourcingFragment : BaseFragment(){

    @Inject
    @HistoryViewModel.WorksHistory
    lateinit var hModel : HistoryViewModel

    private lateinit var bind : FragmentOutsourcingBinding

    private val filterViewModel : FilterViewModel by activityViewModels()
    private val viewModel : OutsourcingViewModel by activityViewModels()
    private val artistViewModel : ArtistViewModel by activityViewModels()


    override fun getFragmentRoot(): View {
        bind = FragmentOutsourcingBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        if (viewModel.searchOn.value){
            viewModel.closeSearchPage()
            return
        }
        viewModel.backPressedToExitApp {
            activity?.finish()
        }
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        inflateFilter()
        inflateArtist()
        inflateSearch()
        inflateHistory()

    }


    private fun inflateFilter(){
        ViewOutsourcingFilterBinding.inflate(layoutInflater).apply{

            vm = filterViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flFilter.addView(root)
        }
    }
    private fun inflateArtist(){
        ViewOutsourcingArtistBinding.inflate(layoutInflater).apply{
            vm = artistViewModel
            oVm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flOutsourcing.addView(root)
        }
    }

    private fun inflateSearch(){
        ViewOutsourcingSearchbarBinding.inflate(layoutInflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.fgAppbarSearch.addView(root)
        }
    }

    private fun inflateHistory(){
        ViewSearchHistoryBinding.inflate(layoutInflater).apply{
            vm = hModel
            model = viewModel.historyModel
            search = HistoryAdapter.OnClick {
                viewModel.search(it)
            }
            delete = HistoryAdapter.OnClick { id -> viewModel.historyModel.deleteHistory(id) }
            lifecycleOwner = viewLifecycleOwner
            bind.fgHistory.addView(root)
        }
    }
}