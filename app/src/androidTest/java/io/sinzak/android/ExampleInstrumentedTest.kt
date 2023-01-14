package io.sinzak.android

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.main.home.viewmodel.HomeViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext




        assertEquals("io.sinzak.android", appContext.packageName)





    }
}