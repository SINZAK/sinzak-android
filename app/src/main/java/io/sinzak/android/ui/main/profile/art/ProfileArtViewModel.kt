package io.sinzak.android.ui.main.profile.art

import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class ProfileArtViewModel : BaseViewModel() {

    val isCompleteList = MutableStateFlow(false)

    abstract val adapter : RecyclerView.Adapter<*>

    abstract fun onItemClick(product: Product)

    fun setIsComplete(status : Boolean)
    {
        isCompleteList.value = status
    }

    fun onBackPressed()
    {
        navigation.revealHistory()
    }

    init {
        isCompleteList.value = false
    }



}