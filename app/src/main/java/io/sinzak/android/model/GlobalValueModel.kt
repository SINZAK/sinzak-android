package io.sinzak.android.model

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.R
import javax.inject.Inject

class GlobalValueModel @Inject constructor(val context : Context) {


    val univMap : Map<String, String> = context.resources.getStringArray(R.array.univ_name).let{univ ->
        val mail = context.resources.getStringArray(R.array.univ_mail)
        val map = mutableMapOf<String,String>()
        for(i in univ.indices){
            map[univ[i]] = mail[i]
        }
        map
    }

    val categoryMarket = context.resources.getStringArray(R.array.category_market).toList()

    val categoryWork = context.resources.getStringArray(R.array.category_work).toList()



    @Module
    @InstallIn(SingletonComponent::class)
    internal object Provider{
        @Provides
        fun provideModel(@ApplicationContext context : Context) : GlobalValueModel{
            return GlobalValueModel(context)
        }
    }
}