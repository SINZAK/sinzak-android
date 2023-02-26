package io.sinzak.android.ui.main.search

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.remote.dataclass.history.HistoryData

@BindingAdapter("adapter","history","search","delete")
fun setHistoryAdapter(view : RecyclerView, adapter : HistoryAdapter, history : List<HistoryData>, search : HistoryAdapter.OnClick, delete : HistoryAdapter.OnClick)
{
    view.adapter?:run{
        view.adapter = adapter
    }

    adapter.reduce(search,delete,history)
}