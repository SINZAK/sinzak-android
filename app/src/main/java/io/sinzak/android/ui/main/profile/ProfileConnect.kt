package io.sinzak.android.ui.main.profile

import android.content.Context
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ProfileConnect @Inject constructor()  {

    lateinit var context: Context

}