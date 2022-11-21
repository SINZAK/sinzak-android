package io.sinzak.android.ui.main.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter : ArtReferAdapter)
{
    view.adapter?:run{
        view.adapter = adapter
    }

    // adapter.setData()

    // adapter.setOnClickListener


}