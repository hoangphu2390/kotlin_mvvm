package com.basekotlin.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.basekotlin.define.MyApplication
import com.basekotlin.di.module.AppModule
import com.basekotlin.ui.main.MainActivity
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

/**
 * Created by HP on 07/09/2018.
 */
class AppInjector(application: MyApplication) {

    init {
        initLifeCycle(application)
    }

    fun initLifeCycle(application: MyApplication) {
        DaggerAppComponent.builder().application(application)
                .appModule(AppModule(application.getApplicationContext()))
                .build().inject(application)

        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun handleActivity(activity: Activity) {
        AndroidInjection.inject(activity)
        if (activity is MainActivity) {
            (activity as FragmentActivity).supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(fm: FragmentManager?, fragment: Fragment?,
                                                               savedInstanceState: Bundle?) {
                                    if (fragment is Injectable) {
                                        AndroidSupportInjection.inject(fragment)
                                    }
                                }
                            }, true)
        }
    }
}