package io.sinzak.android.ui.main.market

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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