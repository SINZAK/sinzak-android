package io.sinzak.android.model.market

import android.net.Uri
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketWriteModel @Inject constructor() : BaseModel(){


    private var imgUris = listOf<Uri>()
    private var title = ""
    private var content = ""
    private var price = 0
    private var categoryText = ""


    fun setImgUri(u : List<Uri>){
        imgUris = u
    }

    fun setTitle(t : String){
        title = t
    }

    fun setContent(c : String){
        content = c
    }

    fun setPrice(p : Int){
        price = p
    }

    fun setCategoryText(c : String){
        categoryText = c
    }





    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }


}