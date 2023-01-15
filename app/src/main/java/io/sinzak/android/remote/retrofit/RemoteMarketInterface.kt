package io.sinzak.android.remote.retrofit

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.dataclass.request.market.ProductLikeRequest
import io.sinzak.android.remote.dataclass.request.market.ProductSuggestRequest
import io.sinzak.android.remote.dataclass.response.market.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteMarketInterface {
    @POST("api/products")
    fun getMarketProducts(@HeaderMap header : HashMap<String,String>, @Query("page") page : Int, @Query("size") size : Int, @Query("align") align : String, @Query("category") category : String ) : Call<MarketProductResponse>

    @POST("api/products/build")
    fun buildMarketProduct(@HeaderMap header: HashMap<String, String>, @Body body : ProductBuildRequest) : Call<ProductBuildResponse>

    @POST("api/products/{id}/edit")
    fun editMarketProduct(@HeaderMap header: HashMap<String, String>, @Path("id") id : Int, @Body body : ProductBuildRequest) : Call<ProductBuildResponse>



    @POST("api/products/{id}")
    fun getMarketProductDetail(@HeaderMap headerMap: HashMap<String,String>, @Path("id") id : Int) : Call<MarketDetailResponse>


    @Multipart
    @POST("api/products/{id}/image")
    fun uploadProductImage(@HeaderMap headerMap: HashMap<String,String>,@Path("id") id : Int, @Part parts : List<MultipartBody.Part>) : Call<CResponse>


    @POST("api/products/{id}/deleteimage")
    fun deleteProductImage(@HeaderMap headerMap: HashMap<String,String>,@Path("id") id : Int, @Body body : JsonObject) : Call<CResponse>

    @POST("api/products/{id}/delete")
    fun deleteProduct(@HeaderMap headerMap: HashMap<String,String>,@Path("id") id : Int) : Call<CResponse>




    @POST("api/home/products")
    fun getHomeProducts(@HeaderMap headerMap : HashMap<String,String>) : Call<MarketHomeResponse>

    @POST("api/home/following")
    fun getHomeFollowing(@HeaderMap headerMap : HashMap<String,String>) : Call<HomeMoreResponse>

    @POST("api/home/recommend")
    fun getHomeRefer(@HeaderMap headerMap : HashMap<String,String>) : Call<HomeMoreResponse>



    @POST("api/products/likes")
    fun postProductLikes(@HeaderMap headerMap: HashMap<String, String>, @Body body : ProductLikeRequest) : Call<CResponse>

    @POST("api/products/wish")
    fun postProductWish(@HeaderMap headerMap: HashMap<String, String>, @Body body : ProductLikeRequest) : Call<CResponse>

    @POST("api/products/suggest")
    fun postProductSuggest(@HeaderMap headerMap: HashMap<String, String>, @Body body : ProductSuggestRequest) : Call<CResponse>
}