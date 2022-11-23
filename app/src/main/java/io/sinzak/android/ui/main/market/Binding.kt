package io.sinzak.android.ui.main.market

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.enums.Sort
import io.sinzak.android.enums.Sort.*
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter
import io.sinzak.android.ui.main.market.adapter.FilterAdapter

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter : ArtsAdapter)
{
    view.adapter?:run{
        view.adapter = adapter
    }

    // adapter.setData()

    // adapter.setOnClickListener
}

@BindingAdapter("adapter","filterChosen","filter","listener")
fun setAdapter(view : RecyclerView, adapter : FilterAdapter, chosenList : MutableList<String>, list : MutableList<String>, listener : (String?, Boolean)->Unit)
{
    view.adapter?:run{
        view.adapter = adapter
    }

    adapter.reduce(chosenList,list,listener)
}


@BindingAdapter("sortText")
fun setSortText(view : TextView, sort : Sort)
{
    view.setText(
        when(sort)
        {
            BY_REFER -> R.string.str_sort_by_refer
            BY_FAME -> R.string.str_sort_by_fame
            BY_RECENT -> R.string.str_sort_by_recent
            BY_HIGHPRICE -> R.string.str_sort_by_highprice
            BY_LOWPRICE -> R.string.str_sort_by_lowprice
        }
    )
}