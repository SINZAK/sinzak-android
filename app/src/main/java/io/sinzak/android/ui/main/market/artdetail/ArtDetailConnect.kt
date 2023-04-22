package io.sinzak.android.ui.main.market.artdetail

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.sinzak.android.model.navigate.Navigation
import javax.inject.Inject


@ActivityScoped
class ArtDetailConnect @Inject constructor(
    @ActivityContext val context: Context,
    val navigation: Navigation
)