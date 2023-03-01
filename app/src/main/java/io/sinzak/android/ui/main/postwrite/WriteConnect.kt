package io.sinzak.android.ui.main.postwrite

import android.net.Uri
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.enums.Page
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.FileUtil
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class WriteConnect @Inject constructor(
    val navigation: Navigation
) {
    private lateinit var activity : BaseActivity<*>

    fun registerActivity(a: BaseActivity<*>){
        activity = a
    }

    fun gotoDetailAfterBuild(){
        navigation.removeHistory(Page.NEW_POST_IMAGE)
        navigation.removeHistory(Page.NEW_POST_INFO)
        navigation.removeHistory(Page.NEW_POST)
        navigation.removeHistory(Page.ART_DETAIL)
        navigation.changePage(Page.ART_DETAIL)
        navigation.removeHistory(Page.NEW_POST_SPEC)
        navigation.removeHistory(Page.NEW_POST_WORKINFO)
    }



    fun loadImage(callback : (List<Uri>)->Unit){
        FileUtil.pickFromGallery(activity,{
            callback(it)
        },true)
    }
}