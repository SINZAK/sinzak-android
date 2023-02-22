package io.sinzak.android.ui.login.interest

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentRegisterInterestBinding
import io.sinzak.android.databinding.HolderWriteCategoryBinding
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
open class InterestFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterInterestBinding

    open val viewModel by activityViewModels<InterestViewModel>()


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
        }

        inflateDesignChips(viewModel.chosenDesignChip.value)
        inflatePureChips(viewModel.chosenPureChip.value)
    }

    private fun inflateDesignChips(chosenChip : MutableList<String>) : Boolean{

        bind.cgDesign.removeAllViews()
        for (genre in viewModel.genreDesign) {
            HolderWriteCategoryBinding.inflate(layoutInflater).apply {
                var isSelect = genre in chosenChip
                filter = genre
                select = isSelect
                root.setOnClickListener {
                    if(viewModel.putChosenDesign(genre)) {
                        isSelect = !isSelect
                        select = isSelect
                    }
                }
                bind.cgDesign.addView(root)
            }
        }
        return true
    }

    private fun inflatePureChips(chosenChip : MutableList<String>) : Boolean{

        bind.cgPure.removeAllViews()
        for (genre in viewModel.genrePure) {
            HolderWriteCategoryBinding.inflate(layoutInflater).apply {
                filter = genre
                var isSelect = genre in chosenChip
                select = isSelect
                root.setOnClickListener {
                    if(viewModel.putChosenPure(genre)) {
                        isSelect = !isSelect
                        select = isSelect
                    }
                }
                bind.cgPure.addView(root)
            }
        }
        return true

    }


}