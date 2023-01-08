package io.sinzak.android.model.market

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import io.sinzak.android.constants.API_BUILD_MARKET_PRODUCT
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogInfo
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketWriteModel @Inject constructor() : BaseModel(){


    private var imgUris = listOf<Uri>()
    private var imgBitmaps = mutableListOf<Bitmap>()
    private var title = ""
    private var content = ""
    private var price = 0
    private var categoryText = ""
    private var canSuggestPrice = false

    private lateinit var context : Context


    val flagBuildSuccess = MutableStateFlow(false)

    fun provideContext(c : Context){
        context = c
    }


    fun setImgUri(u : List<Uri>){
        imgUris = u
        loadBitmaps()
    }

    fun loadBitmaps(){
        imgBitmaps = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            imgUris.forEach { uri->
                imgBitmaps.add(FileUtil.getBitmapFile(context,uri))
            }

            LogInfo(javaClass.name,"[MarketWriteModel] bitmap Loaded : $imgBitmaps")
        }
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



    fun buildProduct(){
        val request = ProductBuildRequest(
            title = title,
            category = categoryText,
            price = price,
            priceSuggest = canSuggestPrice,
            width = 0,
            height = 0,
            vertical = 0,//todo
            content = content

        )

        val requestBodies = imgBitmaps.map{
            FileUtil.getMultipart(context,it)
        }

        CallImpl(API_BUILD_MARKET_PRODUCT,this,request, multipartList = requestBodies).apply{
            remote.sendRequestApi(this)
        }



    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_BUILD_MARKET_PRODUCT->{
                if(body.success == true){
                    flagBuildSuccess.value = true
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }


}