package io.sinzak.android.model

import android.content.Context
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class GlobalUiModel @Inject constructor(val context : Context) {


    fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()

    }




    @Module
    @InstallIn(SingletonComponent::class)
    internal object Provider{
        @Provides
        fun provideModel(@ApplicationContext context : Context) : GlobalUiModel{
            return GlobalUiModel(context)
        }
    }
}