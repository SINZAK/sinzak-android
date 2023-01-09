package io.sinzak.android.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding>(private val layoutId : Int) : AppCompatActivity() {

    private lateinit var bind : T


    @Inject lateinit var softKey : SoftKeyModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this,layoutId)
        bind.lifecycleOwner = this
        LogDebug(javaClass.name,"ACTIVITY CREATED")
        onActivityCreate()
        softKey.registerActivity(this)
    }

    fun useBind(_bind : T.()->Unit)
    {
        _bind(bind)
    }

    override fun onResume() {
        super.onResume()
        softKey.registerActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun showToast(msg : String)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }



    fun <T> invokeStateFlow(state : StateFlow<T>, collect : (T)->Unit)
    {
        lifecycleScope.launch {
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

    private lateinit var launcherResponse : (Intent?)->Unit

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        LogInfo(javaClass.name,"[RESULT LAUNCHER] resultCode : ${it.resultCode}")
        if(it.resultCode == Activity.RESULT_OK){
            launcherResponse(it.data)
        }
    }


    fun gotoActivityForResult(intent : Intent, onResponse : (Intent?)->Unit)
    {
        launcherResponse = onResponse
        resultLauncher.launch(intent)
    }


    abstract fun onActivityCreate()
}