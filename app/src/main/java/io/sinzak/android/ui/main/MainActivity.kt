package io.sinzak.android.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_FCM_TOKEN
import io.sinzak.android.databinding.ActivityMainBinding
import io.sinzak.android.databinding.ViewMainBottomMenuBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Page.*
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogError
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.chat.ChatFragment
import io.sinzak.android.ui.main.chat.ChatRoomFragment
import io.sinzak.android.ui.main.home.HomeFragment
import io.sinzak.android.ui.main.home.more.HomeMoreFragment
import io.sinzak.android.ui.main.home.notification.NotificationFragment
import io.sinzak.android.ui.main.market.MarketFragment
import io.sinzak.android.ui.main.market.artdetail.ArtDetailFragment
import io.sinzak.android.ui.main.market.artdetail.suggest.SuggestFragment
import io.sinzak.android.ui.main.outsourcing.OutsourcingFragment
import io.sinzak.android.ui.main.postwrite.fragment.*
import io.sinzak.android.ui.main.profile.ProfileFragment
import io.sinzak.android.ui.main.profile.ProfileOtherFragment
import io.sinzak.android.ui.main.profile.certification.CertificationFragment
import io.sinzak.android.ui.main.profile.certification.VerifyFragment
import io.sinzak.android.ui.main.profile.certification.WebmailFragment
import io.sinzak.android.ui.main.profile.edit.EditFragment
import io.sinzak.android.ui.main.profile.follow.FollowFragment
import io.sinzak.android.ui.main.profile.report.ReportSendFragment
import io.sinzak.android.ui.main.profile.report.ReportTypeFragment
import io.sinzak.android.ui.main.profile.art.RequestFragment
import io.sinzak.android.ui.main.profile.art.SaleFragment
import io.sinzak.android.ui.main.profile.art.WorkFragment
import io.sinzak.android.ui.main.profile.edit.EditInterestFragment
import io.sinzak.android.ui.main.profile.scrap.ScrapFragment
import io.sinzak.android.ui.main.profile.setting.BlockListFragment
import io.sinzak.android.ui.main.profile.setting.ResignFragment
import io.sinzak.android.ui.main.profile.setting.SettingFragment
import io.sinzak.android.utils.RootViewDeferringInsetsCallback
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.ui.main.chat.postroom.ChatPostFragment


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var signModel: SignModel

    private val viewModel: MainViewModel by viewModels()

    private val bottomViewModel: MainBottomViewModel by viewModels()

    private var fragment: BaseFragment? = null

    private val requiredPermissions = arrayListOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)


    private val REQUEST_CODE_PERMISSIONS = 101

    private lateinit var notGrantedPermissions : List<String>

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        lifecycleScope.launch {
            LogDebug(javaClass.name, "수집수집수집")

        }

        inflateBottomMenu()

        attachInsetsCallback()

        getFCMToken()

        signModel.checkToken()

        notGrantedPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGrantedPermissions.isNotEmpty()) {
            uiModel.permissionDialog(::acceptPermissions)
        }

        if (intent.action == "OPEN_CHAT"){
            viewModel.showChatThroughAlarm(intent.getStringExtra("uuid"))
        }


    }


    private fun attachInsetsCallback() {
        RootViewDeferringInsetsCallback(
            WindowInsetsCompat.Type.systemBars(),
            WindowInsetsCompat.Type.ime()
        ).apply {
            useBind {
                ViewCompat.setOnApplyWindowInsetsListener(root, this@apply)
                ViewCompat.setWindowInsetsAnimationCallback(root, this@apply)

            }
        }
    }


    private fun inflateBottomMenu() {


        useBind {
            DataBindingUtil.inflate<ViewMainBottomMenuBinding>(
                layoutInflater,
                R.layout.view_main_bottom_menu,
                null,
                false
            ).apply {
                activity = this@MainActivity
                viewmodel = bottomViewModel
                lifecycleOwner = this@MainActivity
                flBottomMenu.addView(root)
            }
        }
    }

    override fun onBackPressed() {

        if(viewModel.netStatus.onRemotePending.value)
            return

        fragment?.run {
            this.navigateOnBackPressed()
        } ?: run {
            super.onBackPressed()
        }
    }


    fun inflateFragment(page: Page): Boolean {
        fragment =
            when (page) {
                HOME -> {
                    navigation.clearHistory()
                    HomeFragment()
                }
                HOME_MORE -> {
                    HomeMoreFragment()
                }
                MARKET -> {
                    navigation.clearHistory()
                    MarketFragment()
                }
                HOME_NOTIFICATION ->
                    NotificationFragment()
                PROFILE -> {
                    navigation.clearHistory()
                    ProfileFragment()
                }
                OUTSOURCING -> {
                    navigation.clearHistory()
                    OutsourcingFragment()
                }
                CHAT -> {
                    navigation.clearHistory()
                    ChatFragment()
                }

                PROFILE_OTHER,
                PROFILE_SALE,
                PROFILE_WORK,
                PROFILE_EDIT,
                PROFILE_EDIT_INTEREST,
                PROFILE_SETTING,
                PROFILE_SETTING_RESIGN,
                PROFILE_SETTING_BLOCKLIST,
                PROFILE_CERTIFICATION,
                PROFILE_WEBMAIL,
                PROFILE_VERIFY,
                PROFILE_REPORT_TYPE,
                PROFILE_REPORT_SEND,
                PROFILE_SCRAP,
                PROFILE_REQUEST,
                PROFILE_FOLLOW->
                    inflateProfileFragments(page)


                NEW_POST, NEW_POST_IMAGE, NEW_POST_INFO, NEW_POST_SPEC, NEW_POST_WORKINFO ->
                    inflateWriteFragments(page)

                ART_DETAIL ->
                    ArtDetailFragment()

                ART_DETAIL_SUGGEST ->
                    SuggestFragment()

                CHAT_ROOM ->
                    ChatRoomFragment()

                CHAT_ROOM_FROM_POST ->
                    ChatPostFragment()
            }

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fc_main, it).commit()
            viewModel.setBottomMenuVisibility(it.showBottomBar())
            LogDebug(javaClass.name, "BOTTOM VISIBLITY = ${it.showBottomBar()}")
        }


        return true
    }


    private fun inflateWriteFragments(page: Page): BaseFragment {
        return when (page) {
            NEW_POST -> CategoryFragment()
            NEW_POST_IMAGE -> ImageFragment()
            NEW_POST_SPEC -> SpecFragment()
            NEW_POST_WORKINFO -> ArtWorkFragment()
            else -> ArtInfoFragment()
        }
    }


    private fun inflateProfileFragments(page: Page): BaseFragment {
        return when (page) {
            PROFILE_OTHER ->
                ProfileOtherFragment()
            PROFILE_SALE ->
                SaleFragment()
            PROFILE_WORK ->
                WorkFragment()
            PROFILE_EDIT ->
                EditFragment()
            PROFILE_EDIT_INTEREST ->
                EditInterestFragment()
            PROFILE_SETTING ->
                SettingFragment()
            PROFILE_SETTING_RESIGN ->
                ResignFragment()
            PROFILE_SETTING_BLOCKLIST ->
                BlockListFragment()
            PROFILE_CERTIFICATION ->
                CertificationFragment()
            PROFILE_WEBMAIL ->
                WebmailFragment()
            PROFILE_VERIFY ->
                VerifyFragment()
            PROFILE_REPORT_TYPE ->
                ReportTypeFragment()
            PROFILE_SCRAP ->
                ScrapFragment()
            PROFILE_REQUEST ->
                RequestFragment()
            PROFILE_FOLLOW ->
                FollowFragment()
            else ->
                ReportSendFragment()
        }
    }

    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                LogError(javaClass.name, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
            prefs.setString(CODE_FCM_TOKEN,token.toString())

            // Log and toast
            LogDebug(javaClass.name,"FCM Token is ${token}")
        })

        return token
    }

    private fun acceptPermissions(){
        ActivityCompat.requestPermissions(this,notGrantedPermissions.toTypedArray(), REQUEST_CODE_PERMISSIONS)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 요청이 승인된 경우, 기능을 실행합니다.
                    // ...
                } else {
                    // 권한 요청이 거부된 경우, 사용자에게 권한이 필요하다는 안내 메시지를 보여줍니다.
                    uiModel.showToast(viewModel.valueModel.getString(R.string.str_need_permission))
                }
                return
            }
            else -> {
                // 다른 요청 코드를 처리하는 경우, 부모 클래스의 함수를 호출합니다.
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

}