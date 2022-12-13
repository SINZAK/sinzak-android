package io.sinzak.android.ui.base

import androidx.lifecycle.ViewModel
import io.sinzak.android.model.navigate.Navigation
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {


    @Inject lateinit var navigation: Navigation
}