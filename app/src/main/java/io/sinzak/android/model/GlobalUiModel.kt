package io.sinzak.android.model

import android.content.Context
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseDialog
import io.sinzak.android.ui.main.market.artdetail.ArtistReportDialog
import javax.inject.Inject

class GlobalUiModel @Inject constructor(val context : Context) {


    @Inject lateinit var navigation : Navigation
    @Inject lateinit var detailModel : MarketProductModel

    fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()

    }



    fun openProductDetail(product : Product){

        navigation.changePage(Page.ART_DETAIL)
        detailModel.loadProduct(product.id!!)
    }


    /**
     * Dialog Builder 반환 여기서
     */











    @Module
    @InstallIn(SingletonComponent::class)
    internal object Provider{
        @Provides
        fun provideModel(@ApplicationContext context : Context) : GlobalUiModel{
            return GlobalUiModel(context)
        }
    }
}