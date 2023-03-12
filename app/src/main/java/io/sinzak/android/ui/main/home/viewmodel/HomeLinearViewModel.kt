package io.sinzak.android.ui.main.home.viewmodel

import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.ui.base.BaseViewModel


abstract class HomeLinearViewModel : BaseViewModel() {

    abstract val adapter : RecyclerView.Adapter<*>
    abstract val title : String
    abstract val hMargin : Float
}