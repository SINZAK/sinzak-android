package io.sinzak.android.model

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.R
import io.sinzak.android.system.App
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GlobalValueModel @Inject constructor(@ApplicationContext val context: Context) {

    val univMap : Map<String, String> get() =  context.resources.getStringArray(R.array.univ_name).let{univ ->
        val mail = context.resources.getStringArray(R.array.univ_mail)
        val map = mutableMapOf<String,String>()
        for(i in univ.indices){
            map[univ[i]] = mail[i]
        }
        map
    }

    val categoryMarket = context.resources.getStringArray(R.array.category_market).toList()

    val categoryMarketDraw = listOf(
        R.drawable.ic_category_normal,
        R.drawable.ic_category_orient,
        R.drawable.ic_category_carv,
        R.drawable.ic_category_rawdraw,
        R.drawable.ic_category_arch,
        R.drawable.ic_category_other
    ).map{
        context.getDrawable(it)
    }



    val categoryWork = context.resources.getStringArray(R.array.category_work).toList()




    fun getString(resourceId : Int)
        = context.getString(resourceId)



}