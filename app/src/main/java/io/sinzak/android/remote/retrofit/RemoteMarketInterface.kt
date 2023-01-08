package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.dataclass.response.market.MarketProductResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteMarketInterface {
    @POST("/market/products")
    fun getMarketProducts(@HeaderMap header : HashMap<String,String>, @Query("page") page : Int, @Query("size") size : Int, @Query("align") align : String, @Query("category") category : String ) : Call<MarketProductResponse>



    @Multipart
    @POST("/products/build")
    fun buildMarketProduct(@HeaderMap header: HashMap<String,String>,  @Part body : MultipartBody.Part, @Part multipart : List<MultipartBody.Part>) : Call<CResponse>
}