package com.basekotlin.define.component

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import org.greenrobot.eventbus.EventBus

/**
 * Created by HP on 07/09/2018.
 */
class EventBusComponent(subcriber: Any) : LifecycleObserver {

    var m_subcriber: Any? = null
    var m_eventBus: EventBus? = null

    init {
        this.m_subcriber = subcriber
        m_eventBus = EventBus.getDefault()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected fun onCreateComponent() {
        if (!m_eventBus!!.isRegistered(m_subcriber)) {
            m_eventBus!!.register(m_subcriber)
        }
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
        m_eventBus!!.unregister(m_subcriber)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onDestroyComponent() {
        if (m_eventBus != null) {
            m_eventBus = null
        }
    }
}