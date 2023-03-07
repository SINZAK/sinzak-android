package io.sinzak.android.ui.main

import androidx.activity.viewModels
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
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogError
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.chat.ChatConnect
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


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var signModel: SignModel

    @Inject
    lateinit var homeProductModel: HomeProductModel

    @Inject
    lateinit var chatConnect: ChatConnect

    private val viewModel: MainViewModel by viewModels()

    private val bottomViewModel: MainBottomViewModel by viewModels()

    private var fragment: BaseFragment? = null

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        lifecycleScope.launch {
            homeProductModel.recommendProducts.collect {
                LogDebug(javaClass.name, "수집수집수집")
            }

        }

        chatConnect.registerActivity(this)

        inflateBottomMenu()

        attachInsetsCallback()


        getFCMToken()
        signModel.checkToken()
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


}