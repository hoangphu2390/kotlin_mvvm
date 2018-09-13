package com.basekotlin.define

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.basekotlin.BuildConfig
import com.basekotlin.di.AppInjector
import com.helpinhand_admin.api.Dependencies
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by HP on 07/09/2018.
 */
class MyApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var m_activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        s_instance = this
        Dependencies.init()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector(this)
    }

    companion object {
        lateinit var s_instance: MyApplication
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return m_activityDispatchingAndroidInjector
    }
}