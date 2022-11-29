package io.sinzak.android.ui.main.profile.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter: ProfileArtAdapter)
{
    view.adapter?: run {
        view.adapter = adapter
    }

    // adapter.setData()

    // adapter.setOnClickListener
}