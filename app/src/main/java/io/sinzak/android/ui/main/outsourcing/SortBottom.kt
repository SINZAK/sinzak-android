package io.sinzak.android.ui.main.outsourcing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.sinzak.android.databinding.BtmSortBinding
import io.sinzak.android.enums.Sort
import io.sinzak.android.ui.base.BottomDialog

class SortBottom : BottomDialog() {

    private lateinit var bind : BtmSortBinding


    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmSortBinding.inflate(inflater)
        bind.lifecycleOwner = viewLifecycleOwner



        bind.initValue = initValue
        bind.onClick = object : OnClickListener{
            override fun onClick(sort: Sort) {
                onClickSort(sort)
                dismiss()
            }

        }
        return bind.root
    }

    private var initValue : Sort = Sort.BY_RECENT
    private lateinit var onClickSort : (Sort)->Unit

    fun show(fm : FragmentManager, tag : String?, initValue : Sort, onClickSort : (Sort)->Unit){

        this.initValue = initValue
        this.onClickSort = onClickSort
        show(fm, tag)
    }


    interface OnClickListener{
        fun onClick(sort : Sort)
    }

}