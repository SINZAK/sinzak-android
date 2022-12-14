package io.sinzak.android.model

import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseDialog
import io.sinzak.android.ui.login.LoginActivity
import io.sinzak.android.ui.main.market.artdetail.ArtistReportDialog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalUiModel @Inject constructor(@ApplicationContext val context : Context,
val navigation: Navigation
) {
    private lateinit var activity : BaseActivity<*>


    fun registerActivity(_activity : BaseActivity<*>){
        activity = _activity
    }


    fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()

    }



    fun openProductDetail(){

        navigation.changePage(Page.ART_DETAIL)

    }


    /**
     * Dialog Builder 반환 여기서
     */



    fun gotoLogin(){
        activity.startActivity(Intent(activity, LoginActivity::class.java))
    }








}