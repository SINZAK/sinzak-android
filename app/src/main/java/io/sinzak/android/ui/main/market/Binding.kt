package io.sinzak.android.ui.main.market

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter : ArtsAdapter)
{
    view.adapter?:run{
        view.adapter = adapter
    }

    // adapter.setData()

    // adapter.setOnClickListener


}