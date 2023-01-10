package io.sinzak.android.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import io.sinzak.android.model.GlobalUiModel
import io.sinzak.android.model.GlobalValueModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.App
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {


    @Inject lateinit var navigation: Navigation

    @Inject lateinit var uiModel : GlobalUiModel

    @Inject lateinit var signModel: SignModel

    val valueModel = App.globalValueModel

    fun showToast(msg : String){
        uiModel.showToast(msg)
    }

    fun <T> invokeStateFlow(state : StateFlow<T>, collect : (T)->Unit)
    {

        viewModelScope.launch {
            state.collect{
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

}