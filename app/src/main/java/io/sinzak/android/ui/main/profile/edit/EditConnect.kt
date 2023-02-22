package io.sinzak.android.ui.main.profile.edit

import android.net.Uri
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.databinding.FragmentEditProfileBinding
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.FileUtil
import javax.inject.Inject

@ActivityRetainedScoped
class EditConnect @Inject constructor() {

    private lateinit var activity : BaseActivity<*>
    lateinit var bind : FragmentEditProfileBinding

    fun registerActivity(a: BaseActivity<*> , b : FragmentEditProfileBinding){
        activity = a
        bind = b
    }

    fun loadImage(callback : (Uri)->Unit){
        FileUtil.pickFromGallery(activity,{
            callback(it[0])
        },false)
    }
}