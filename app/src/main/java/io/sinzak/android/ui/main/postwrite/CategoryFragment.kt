package io.sinzak.android.ui.main.postwrite

import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteCategoryBinding
import io.sinzak.android.databinding.HolderMarketFilterBinding
import io.sinzak.android.system.dp
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class CategoryFragment : BaseFragment() {

    val viewModel by activityViewModels<CategoryViewModel>()


    private lateinit var bind : FragmentWriteCategoryBinding
    override fun getFragmentRoot(): View {
        bind = FragmentWriteCategoryBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            fg = this@CategoryFragment
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }


    fun inflateCategory(categories : List<String>, isSelect : List<String>) : Boolean{
        bind.cgCategory.apply {
            removeAllViews()

            categories.forEach {category ->
                HolderMarketFilterBinding.inflate(layoutInflater).apply{
                    val selected = isSelect.contains(category)
                    filter = category
                    select = selected
                    root.setOnClickListener {
                        viewModel.clickCategory(category,selected)
                    }
                    addView(root)
                }
            }
        }
        return false
    }
}