package io.sinzak.android.ui.login

import android.content.Context
import android.content.Intent
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.main.MainActivity
import io.sinzak.android.utils.LinkUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterConnect @Inject constructor(val navigation : RegisterNavigation) {

    private lateinit var _context : Context
    val context : Context get() = _context

    private lateinit var _activity : BaseActivity<*>

    fun registerActivityContext(activityContext : Context, activity: BaseActivity<*>){
        _context = activityContext
        _activity = activity
    }





    /**
     * Step 1 : 약관 페이지 -> 이름 페이지
     */
    fun gotoDisplayNamePage(){
        navigation.changePage(RegisterPage.PAGE_NAME)
    }

    /**
     * Step  2 : 이름 페이지 -> 카테고리 페이지
     */
    fun gotoCategoryPage(){
        navigation.changePage(RegisterPage.PAGE_INTEREST)
    }

    /**
     * Step 3 : 카테고리 페이지 -> 대학교 페이지
     */

    fun gotoUnivPage(){
        navigation.changePage(RegisterPage.PAGE_UNIVERSITY)
    }

    /**
     * Step 4 : 대학교 페이지 -> 인증 페이지
     */

    fun gotoCertPage(){
        navigation.changePage(RegisterPage.PAGE_UNIVERSITY_CERT)
        navigation.clearHistory()
    }


    /**
     * 가입 완료 페이지
     */
    fun gotoWelcome(){
        navigation.changePage(RegisterPage.Welcome)
        navigation.clearHistory()
    }


    fun finishPage(){
        context.startActivity(
            Intent(context,MainActivity::class.java).apply{
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        )
    }

    fun connectLink(url : String){
        LinkUtil.goToLink(_activity,url)
    }













}