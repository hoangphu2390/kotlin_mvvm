package com.basekotlin.define.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.basekotlin.define.Constant.ACTION_CONNECTION
import com.basekotlin.define.Constant.ACTION_WIFI_CONECTION
import com.basekotlin.util.NetworkUtils

/**
 * Created by HP on 07/09/2018.
 */
class ConnectionService(m_context: Context, tvConnection: TextView, frameContainer: FrameLayout,
                        viewConnection: View, m_listenner: ShowConnectionListener) {

    var m_connectionReceiver: ConnectionReceiver? = null
    var m_context: Context
    var m_listenner: ShowConnectionListener
    var tvConnection: TextView
    var frameContainer: FrameLayout
    var viewConnection: View

    interface ShowConnectionListener {
        fun onShowConnection(isConnection: Boolean)
    }

    init {
        this.m_context = m_context
        this.tvConnection = tvConnection
        this.frameContainer = frameContainer
        this.viewConnection = viewConnection
        this.m_listenner = m_listenner
    }

    fun registerBroadcastConnection() {
        if (m_connectionReceiver == null) {
            m_connectionReceiver = object : ConnectionReceiver() {
                override fun handleConnection(statusConnection: Int) {
                    if (statusConnection == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                        setupUI(false)
                    } else {
                        setupUI(true)
                    }
                }
            }
        }
        var intentFilter = IntentFilter(ACTION_WIFI_CONECTION)
        intentFilter.addAction(ACTION_CONNECTION)
        m_context!!.registerReceiver(m_connectionReceiver, intentFilter)
    }


    fun unregisterBroadcastConnection(context: Context) {
        if (m_connectionReceiver != null) {
            context.unregisterReceiver(m_connectionReceiver)
            m_connectionReceiver = null
        }
    }

    open class ConnectionReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var statusConnection = NetworkUtils.getConnectivityStatusString(context)
            handleConnection(statusConnection)
        }

        open fun handleConnection(statusConnection: Int) {}
    }

    private fun setupUI(isConnected: Boolean) {
        if (tvConnection != null)
            tvConnection.visibility = if (isConnected) View.GONE else View.VISIBLE
        if (frameContainer != null)
            frameContainer.visibility = if (isConnected) View.VISIBLE else View.GONE
        if (viewConnection != null)
            viewConnection.visibility = if (isConnected) View.GONE else View.VISIBLE
        m_listenner!!.onShowConnection(if (isConnected) true else false)
    }
}