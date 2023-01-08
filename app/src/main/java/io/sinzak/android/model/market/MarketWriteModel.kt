package io.sinzak.android.model.market

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.system.LogInfo
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    private lateinit var context : Context

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





    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }


}