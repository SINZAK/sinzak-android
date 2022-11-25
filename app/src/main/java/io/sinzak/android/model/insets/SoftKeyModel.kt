package io.sinzak.android.model.insets

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.sinzak.android.ui.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoftKeyModel @Inject constructor() {

    lateinit var activity : BaseActivity<*>
    lateinit var imm : InputMethodManager
    fun registerActivity(activity: BaseActivity<*>){
        this.activity = activity
        imm = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    }



    fun hideKeyboard(){
        activity.currentFocus?.let{
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }
    }



}