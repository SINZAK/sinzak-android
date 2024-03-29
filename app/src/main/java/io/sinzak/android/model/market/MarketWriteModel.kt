package io.sinzak.android.model.market

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.dataclass.response.market.ProductBuildResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogInfo
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketWriteModel @Inject constructor(@ApplicationContext val context : Context) : BaseModel(){


    var imgUris = listOf<String>()
    private var deleteUris = mutableListOf<String>()
    private var imgBitmaps = mutableListOf<Bitmap>()
    private var title = ""
    private var content = ""
    private var price = 0
    private var categoryText = ""
    private var canSuggestPrice = false
    private var pw = 0
    private var ph = 0
    private var pv = 0

    fun getDimension() : List<Int>{
        return listOf(pw,ph,pv)
    }

    private var productId = -1



    val isBuildMode = MutableStateFlow(false)

    val flagPrepareBuild = MutableStateFlow(false)
    val flagPrepareEdit = MutableStateFlow(false)


    val flagBuildSuccess = MutableStateFlow(false)


    val productType = MutableStateFlow(0) // 0 : Art, 1 : Work Buy 2 : Work Sell


    fun setProductType(type : Int){
        productType.value = type
    }

    /************************************************
     * Flag
     ***************************************/

    fun startBuild(){
        flagPrepareBuild.value = false

        imgUris = listOf()
        deleteUris = mutableListOf()
        isBuildMode.value = true

        flagPrepareBuild.value = true
    }

    fun getProductId() : Int{
        return productId
    }

    fun startEdit(type : Int, id : Int, product : MarketDetailResponse.Detail){
        productType.value = type
        flagPrepareEdit.value = false

        productId = id
        inputDimension(
            product.dWidth,
            product.dHeight,
            product.dVertical
        )
        imgUris = product.imgUrls?: listOf()
        setPrice(product.price)
        setSuggestEnable(product.priceSuggestEnable)
        setContent(product.content)
        setTitle(product.title)
        isBuildMode.value = false

        flagPrepareEdit.value = true
    }


    /************************************************
     * Image
     ***************************************/


    /**
     * 현재 imageUri를 저장하고, 이를 bitmap으로 불러옵니다.
     */
    fun setImgUri(u : List<String>){
        deleteUris = imgUris.filter{
            it !in u
        }.filter { it.contains("http") }.toMutableList()
        imgUris = u
        LogInfo(javaClass.name,"[MARKETWRITEMODEL] : URI Loaded $u")
        loadBitmaps()
    }


    /**
     * 불러온 local uri 을 bitmap 으로 불러옵니다.
     */
    private fun loadBitmaps(){
        imgBitmaps = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            imgUris.forEach { uri->
                if(!uri.contains("http"))
                    imgBitmaps.add(FileUtil.getBitmapFile(context, uri.toUri()))
            }


        }
    }

    /************************************************
     * Local Data Insert
     ***************************************/
    /**
     * 제목을 저장합니다.
     */
    fun setTitle(t : String){
        title = t
    }

    /**
     * 설명을 저장합니다.
     */
    fun setContent(c : String){
        content = c
    }

    /**
     * 가격을 저장합니다.
     */
    fun setPrice(p : Int){
        price = p
    }

    /**
     * 카테고리를 저장합니다.
     */
    fun setCategoryText(c : String){
        categoryText = c
    }

    /**
     * 가격제안 가능 여부를 저장합니다.
     */
    fun setSuggestEnable(s : Boolean){
        canSuggestPrice = s
    }

    /**
     * 상품 스펙을 저장합니다.
     */
    fun inputDimension(w : Int, h : Int, v : Int){
        pw = w
        pv = v
        ph = h
    }


    fun getPrice() : Int{
        return price
    }
    fun getSuggest() : Boolean{
        return canSuggestPrice
    }
    fun getTitle() : String{
        return title
    }
    fun getContent() : String{
        return content
    }


    fun doneInput(){
        if(productType.value == 0)
        {
            if(isBuildMode.value)
                buildProduct()
            else
                updateProduct()
        }
        else{
            if(isBuildMode.value)
                buildWork()
            else
                updateWork()
        }

    }

    /**********************************************************************************************************************
     * REQUEST ( MARKET PRODUCT )
     ***********************************************************************************************************************/

    /**
     * 정보를 조합하여 build api 를 요청합니다.
     */
    private fun buildProduct(){
        val request = ProductBuildRequest(
            title = title,
            category = categoryText,
            price = price,
            priceSuggest = canSuggestPrice,
            width = pw,
            height = ph,
            vertical = pv,
            content = content

        )


        CallImpl(API_BUILD_MARKET_PRODUCT,this,request).apply{
            remote.sendRequestApi(this)
        }



    }

    private fun updateProduct(){
        val request = ProductBuildRequest(
            title = title,
            price = price,
            priceSuggest = canSuggestPrice,
            width = pw,
            height = ph,
            vertical = pv,
            content = content

        )

        CallImpl(API_UPDATE_MARKET_PRODUCT,this,request, paramInt0 = productId).apply{
            remote.sendRequestApi(this)
        }
    }


    /**********************************************************************************************************************
     * REQUEST ( WORK PRODUCT )
     ***********************************************************************************************************************/

    private fun buildWork(){
        val request = ProductBuildRequest(
            title = title,
            category = categoryText,
            priceSuggest = true,
            content = content,
            employment = productType.value ==1
        )
        remote.sendRequestApi(
            CallImpl(
                API_BUILD_MARKET_WORK,
                this,
                request
            )
        )

    }




    private fun updateWork(){
        val request = ProductBuildRequest(
            title = title,
            priceSuggest = true,
            content = content
        )
        remote.sendRequestApi(
            CallImpl(
                API_UPDATE_MARKET_WORK,
                this,
                request,
                paramInt0 = productId
            )
        )


    }

    /**
     * 현재 가지고 있는 비트맵을 모두 업로드합니다.
     */
    private fun uploadImg(id : Int){

        val multiPart : MultipartBody.Part
        if (imgBitmaps.size > 1) {
            multiPart = FileUtil.getMultipart(context,"multipartFile",imgBitmaps[1])
            imgBitmaps.removeAt(1)
        }
        else {
            multiPart = FileUtil.getMultipart(context,"multipartFile",imgBitmaps[0])
            imgBitmaps.removeAt(0)
        }

        imgUris = listOf()

        if(productType.value == 0)
            CallImpl(API_PRODUCT_UPLOAD_IMG, this, paramInt0 = id, multipart = multiPart).apply{
                remote.sendRequestApi(this)
            }
        else
            CallImpl(API_WORK_UPLOAD_IMG, this, paramInt0 = id, multipart = multiPart).apply{
                remote.sendRequestApi(this)
            }
    }

    /**
     * 이미지를 삭제합니다.
     */

    private fun deleteImg(){

        val url = deleteUris[0]
        deleteUris.removeAt(0)

        if(productType.value == 0)
            CallImpl(API_PRODUCT_DELETE_IMG, this, paramStr0 = url, paramInt0 = productId).apply{
                remote.sendRequestApi(this)
            }
        else
            CallImpl(API_WORK_DELETE_IMG, this, paramStr0 = url, paramInt0 = productId).apply{
                remote.sendRequestApi(this)
            }

    }


    private fun checkImage(){
        if(deleteUris.isNotEmpty())
            deleteImg()
        else if(imgBitmaps.isNotEmpty())
            checkUploadImage()
        else
            flagBuildSuccess.value = true
    }

    private fun checkUploadImage(){
        if(imgBitmaps.isNotEmpty())
            uploadImg(productId)
        else
            flagBuildSuccess.value = true
    }




    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_BUILD_MARKET_PRODUCT->{
                if(body.success == true){
                    body as ProductBuildResponse
                    productId = body.id!!
                    checkUploadImage()
                }
            }

            API_UPDATE_MARKET_PRODUCT ->{
                if(body.success == true){
                    checkImage()
                }
            }

            API_BUILD_MARKET_WORK ->{
                if(body.success == true){
                    body as ProductBuildResponse
                    productId = body.id!!
                    checkUploadImage()
                }
            }

            API_UPDATE_MARKET_WORK ->{
                if(body.success == true){
                    checkImage()
                }
            }

            API_PRODUCT_UPLOAD_IMG, API_WORK_UPLOAD_IMG ->{
                if(body.success == true)
                    checkUploadImage()
            }


            API_PRODUCT_DELETE_IMG, API_WORK_DELETE_IMG ->{
                checkImage()
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }


}