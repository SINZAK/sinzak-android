package io.sinzak.android.ui.main

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityMainBinding
import io.sinzak.android.databinding.ViewMainBottomMenuBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Page.HOME
import io.sinzak.android.enums.Page.HOME_NOTIFICATION
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.HomeFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)
{

    @Inject
    lateinit var navigation: Navigation

    private val viewModel : MainViewModel by viewModels()

    private val bottomViewModel : MainBottomViewModel by viewModels()

    override fun onActivityCreate() {
        useBind {
            navigator = navigation
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        inflateBottomMenu()
    }



    private fun inflateBottomMenu(){


        useBind {
            DataBindingUtil.inflate<ViewMainBottomMenuBinding>(layoutInflater,R.layout.view_main_bottom_menu,null,false).apply{
                viewmodel = bottomViewModel
                lifecycleOwner = this@MainActivity
                flBottomMenu.addView(root)
            }
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