package io.sinzak.android.ui.main.profile.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter: SaleWorkAdapter)
{
    view.adapter?: run {
        view.adapter = adapter
    }
}