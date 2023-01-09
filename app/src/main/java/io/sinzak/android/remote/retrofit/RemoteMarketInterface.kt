package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.dataclass.response.market.MarketProductResponse
import io.sinzak.android.remote.dataclass.response.market.ProductBuildResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteMarketInterface {
    @POST("/market/products")
    fun getMarketProducts(@HeaderMap header : HashMap<String,String>, @Query("page") page : Int, @Query("size") size : Int, @Query("align") align : String, @Query("category") category : String ) : Call<MarketProductResponse>

    @POST("/products/build")
    fun buildMarketProduct(@HeaderMap header: HashMap<String, String>, @Body body : ProductBuildRequest) : Call<ProductBuildResponse>

    @POST("/products/{id}")
    fun getMarketProductDetail(@HeaderMap headerMap: HashMap<String,String>, @Path("id") id : String) : Call<MarketDetailResponse>


    @Multipart
    @POST("/products/{id}/image")
    fun uploadProductImage(@HeaderMap headerMap: HashMap<String,String>,@Path("id") id : String, @Part parts : List<MultipartBody.Part>) : Call<CResponse>
}