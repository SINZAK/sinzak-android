package io.sinzak.android.ui.main

import androidx.activity.viewModels
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityMainBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Page.HOME
import io.sinzak.android.enums.Page.HOME_NOTIFICATION
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)
{

    @Inject
    lateinit var navigation: Navigation

    private val viewModel : MainViewModel by viewModels()

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            viewmodel = viewModel
        }
    }



    override fun onBackPressed() {
        supportFragmentManager.fragments[0]?.run {
            this as BaseFragment
            this.navigateOnBackPressed()
        }?:run{
            super.onBackPressed()
        }
    }

    fun inflateFragment(page: Page) : Boolean
    {

        val fragment : BaseFragment =
        when(page)
        {
            HOME ->
                HomeFragment()
            HOME_NOTIFICATION ->
                TODO()
        }

        viewModel.setBottomMenuVisibility(fragment.showBottomBar())
        supportFragmentManager.beginTransaction().add(R.id.fc_main,fragment)
        return true
    }


}