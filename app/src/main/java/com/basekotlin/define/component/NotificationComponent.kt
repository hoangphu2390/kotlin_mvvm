package com.basekotlin.define.component

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import com.basekotlin.define.service.NotificationService
import com.basekotlin.define.service.NotificationService.ShowNotificationListener

/**
 * Created by HP on 07/09/2018.
 */
class NotificationComponent(context: Context, listener: ShowNotificationListener) : LifecycleObserver {

    var m_context: Context
    var m_listener: ShowNotificationListener
    var m_notificationService: NotificationService? = null

    init {
        this.m_context = context
        this.m_listener = listener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected fun onCreateComponent() {
        m_notificationService = NotificationService(m_context, m_listener)
        m_notificationService!!.registerBroadcastConnection()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected fun onStartComponent() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected fun onResumeComponent() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected fun onPauseComponent() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected fun onStopComponent() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onDestroyComponent() {
        if (m_notificationService == null) return
        m_notificationService!!.unregisterBroadcastConnection()
        m_notificationService = null
    }
}