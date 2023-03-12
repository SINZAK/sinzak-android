package io.sinzak.android.ui.main.profile.art

import androidx.lifecycle.viewModelScope
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import kotlinx.coroutines.flow.*
import javax.inject.Inject

abstract class ProfileArtViewModel : BaseViewModel() {

    /**
     * 완료된 아이템 리스트인가?
     */
    val completeList = MutableStateFlow(false)

    /**
     * 없어요 이미지를 보여줄것인가?
     */
    val showNothing = MutableStateFlow(false)
    /**
     * 아이템 클릭
     */
    abstract fun onItemClick(product: Product)

    abstract fun onEndTrade(id: Int,callback: ()->Unit)

    fun setIsComplete(status : Boolean)
    {
        completeList.value = status
    }

    /**
     * 뒤로가기 클릭
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }

    init {
        completeList.value = false
    }

    fun settingAdapter(adapter : SaleWorkAdapter ,list : StateFlow<MutableList<Product>>)
    {
        adapter.apply {
            list.onEach {
                invokeBooleanFlow(
                    completeList,
                    {
                        val filterList = it.filter { !it.complete!! }
                        setArts(filterList)
                        showNothing.value = filterList.isEmpty()
                    },
                    {
                        val filterList = it.filter { it.complete!! }
                        setArts(filterList)
                        showNothing.value = filterList.isEmpty()
                    }
                )
            }.launchIn(viewModelScope)
        }
    }

    companion object {
        const val PRODUCT = true
        const val WORK = false
    }


}