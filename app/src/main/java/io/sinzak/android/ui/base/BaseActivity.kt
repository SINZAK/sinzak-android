package io.sinzak.android.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val layoutId : Int) : AppCompatActivity() {

    private lateinit var bind : T

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        bind = DataBindingUtil.setContentView(this,layoutId)

        onActivityCreate()
    }

    fun useBind(_bind : T.()->Unit)
    {
        _bind(bind)
    }


    fun showToast(msg : String)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


    abstract fun onActivityCreate()
}