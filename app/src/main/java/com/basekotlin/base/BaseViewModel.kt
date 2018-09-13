package com.basekotlin.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.basekotlin.api.ServerAPI
import com.helpinhand_admin.api.Dependencies

/**
 * Created by HP on 07/09/2018.
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    lateinit var m_serverAPI: ServerAPI
    protected lateinit var s_activity: BaseActivity<*, *>

    fun setActivity(activity: BaseActivity<*, *>) {
        this.s_activity = activity
    }

    protected fun setLoadingProgress(progressState: Boolean) {
        if (progressState)
            s_activity.showProgressBar()
        else
            s_activity.hideProgressBar()
    }

    fun getServerAPI(): ServerAPI {
        return m_serverAPI
    }


    //TODO ********************* DEFINE LIFE CYCLE AWARE CONTROL CYCLE OF VIEW-MODEL ***************

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateViewModel() {
        m_serverAPI = Dependencies.serverAPI!!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyViewModel() {
    }
}