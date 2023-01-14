package io.sinzak.android.ui.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import io.sinzak.android.model.GlobalUiModel
import io.sinzak.android.model.GlobalValueModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.system.App
import io.sinzak.android.system.LogDebug
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {


    @Inject lateinit var navigation: Navigation

    @Inject lateinit var uiModel : GlobalUiModel

    @Inject lateinit var signModel: SignModel

    @Inject lateinit var profileModel: ProfileModel

    val valueModel = App.globalValueModel

    fun showToast(msg : String){
        uiModel.showToast(msg)
    }

    fun <T> invokeStateFlow(state : StateFlow<T>, collect : (T)->Unit)
    {

        viewModelScope.launch {

            state.collect{
                it?.let{
                    LogDebug(it::class.java.name,"$it")
                }

                collect(it)
            }
        }
    }

    fun invokeBooleanFlow(state : StateFlow<Boolean>, onFalse : ()->Unit = {}, onTrue : ()->Unit)
    {
        invokeStateFlow(state){
            if(it)
                onTrue()
            else
                onFalse()
        }
    }

    protected fun requireExtra(onCollected : (Bundle)->Unit){
        invokeStateFlow(navigation.bundleInserted){
            navigation.getBundleData(this::class)?.let(onCollected)
        }
    }

}