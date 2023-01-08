package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteCategoryBinding
import io.sinzak.android.databinding.HolderMarketFilterBinding
import io.sinzak.android.databinding.HolderWriteCategoryBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.postwrite.viewmodels.CategoryViewModel

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

        viewModel.model.provideContext(requireContext())
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    fun moveToImagePicker(){
        navigator.changePage(Page.NEW_POST_IMAGE)
    }


    fun inflateCategory(categories : List<String>, isSelect : List<String>) : Boolean{
        bind.cgCategory.apply {
            removeAllViews()

            categories.forEach {category ->
                HolderWriteCategoryBinding.inflate(layoutInflater).apply{
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