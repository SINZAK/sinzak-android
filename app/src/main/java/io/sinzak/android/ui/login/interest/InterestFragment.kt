package io.sinzak.android.ui.login.interest

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentRegisterInterestBinding
import io.sinzak.android.databinding.HolderMarketFilterBinding
import io.sinzak.android.databinding.HolderWriteCategoryBinding
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
class InterestFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterInterestBinding

    val viewModel by activityViewModels<InterestViewModel>()


    override fun getFragmentRoot(): View {
        bind = FragmentRegisterInterestBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@InterestFragment
        }
    }

    fun inflateDesignChips(chosenChip : MutableList<String>) : Boolean{

        bind.cgDesign.removeAllViews()
        for (genre in viewModel.genreDesign) {
            HolderWriteCategoryBinding.inflate(layoutInflater).apply {
                filter = genre
                select = genre in chosenChip
                root.setOnClickListener {
                    viewModel.putChosenDesign(genre)
                }
                bind.cgDesign.addView(root)
            }
        }
        return true
    }

    fun inflatePureChips(chosenChip : MutableList<String>) : Boolean{

        bind.cgPure.removeAllViews()
        for (genre in viewModel.genrePure) {
            HolderWriteCategoryBinding.inflate(layoutInflater).apply {
                filter = genre
                select = genre in chosenChip
                root.setOnClickListener {
                    viewModel.putChosenPure(genre)
                }
                bind.cgDesign.addView(root)
            }
        }
        return true

    }


}