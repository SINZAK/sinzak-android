package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.response.market.MarketProductResponse
import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteMarketInterface {
    @POST("/market/products")
    fun getMarketProducts(@HeaderMap header : HashMap<String,String>, @Query("page") page : Int, @Query("size") size : Int, @Query("align") align : String, @Query("category") category : String ) : Call<MarketProductResponse>
}