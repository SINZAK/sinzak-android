package io.sinzak.android.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.enums.Page
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.login.LoginActivity
import io.sinzak.android.utils.FileUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalUiModel @Inject constructor(@ApplicationContext val context : Context,
val navigation: Navigation
) {
    private var activity : BaseActivity<*>? = null


    fun registerActivity(_activity : BaseActivity<*>){
        activity = _activity
    }

    fun getActivity() : AppCompatActivity?{
        return activity
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
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
    }


    fun loadImage(callback : (List<Uri>)->Unit){
        activity?.let{
            FileUtil.pickFromGallery(it,{
                callback(it)
            },true)
        }

    }






}