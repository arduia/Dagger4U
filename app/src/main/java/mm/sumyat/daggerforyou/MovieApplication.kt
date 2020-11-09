package mm.sumyat.daggerforyou

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi
import mm.sumyat.daggerforyou.di.base.AppInjector
import timber.log.Timber
import javax.inject.Inject

@InternalCoroutinesApi
class MovieApplication : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}