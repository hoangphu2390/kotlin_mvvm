package com.basekotlin.define.component

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.basekotlin.define.service.ConnectionService

/**
 * Created by HP on 07/09/2018.
 */
class NetworkComponent(context: Context, tvConnection: TextView, frameContainer: FrameLayout,
                       viewConnection: View, listener: ConnectionService.ShowConnectionListener) : LifecycleObserver {

    var m_connectionService: ConnectionService? = null
    var m_tvConnection: TextView
    var m_frameContainer: FrameLayout
    var m_listener: ConnectionService.ShowConnectionListener
    var m_context: Context
    var m_viewConnection: View

    init {
        this.m_context = context
        this.m_tvConnection = tvConnection
        this.m_frameContainer = frameContainer
        this.m_viewConnection = viewConnection
        this.m_listener = listener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected fun onCreateComponent() {
        m_connectionService = ConnectionService(m_context, m_tvConnection, m_frameContainer, m_viewConnection, m_listener)
        m_connectionService!!.registerBroadcastConnection()
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
        if (m_connectionService == null) return
        m_connectionService!!.unregisterBroadcastConnection(m_context)
        m_connectionService = null
    }
}