package io.sinzak.android.ui.main.profile.art

import androidx.lifecycle.viewModelScope
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class ProfileArtViewModel : BaseViewModel() {

    /**
     * 완료된 아이템 리스트인가?
     */
    val isCompleteList = MutableStateFlow(false)

    lateinit var adapter: SaleWorkAdapter

    /**
     * 아이템 클릭
     */
    abstract fun onItemClick(product: Product)

    fun setIsComplete(status : Boolean)
    {
        isCompleteList.value = status
    }

    /**
     * 뒤로가기 클릭
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }

    init {
        isCompleteList.value = false
    }

    fun settingAdapter(list : StateFlow<MutableList<Product>>)
    {
        adapter.apply {
            list.onEach {
                invokeBooleanFlow(
                    isCompleteList,
                    { setArts(it.filter { !it.complete!! }) },
                    { setArts(it.filter { it.complete!! }) }
                )
            }.launchIn(viewModelScope)
        }
    }



}