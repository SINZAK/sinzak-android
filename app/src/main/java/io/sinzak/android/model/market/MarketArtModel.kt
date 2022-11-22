package io.sinzak.android.model.market

import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MarketArtModel @Inject constructor() : BaseModel() {


    private val _stShowOnSale = MutableStateFlow(false)
    val stShowOnSale : StateFlow<Boolean> get() = _stShowOnSale


    fun onClickShowOnSale(status : Boolean)
    {
        _stShowOnSale.value = !status
    }


    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }
}