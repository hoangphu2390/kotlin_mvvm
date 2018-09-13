package com.basekotlin.define.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Parcelable
import android.support.v4.content.LocalBroadcastManager
import com.basekotlin.data.local.NotificationLocal
import com.basekotlin.define.Constant
import com.basekotlin.define.Constant.INFO_NOTIFICATION
import com.basekotlin.define.Constant.PUSH_NOTIFICATION
import org.parceler.Parcels

/**
 * Created by HP on 07/09/2018.
 */
class NotificationService(m_context: Context, listenner: ShowNotificationListener) {

    var m_notificationReceiver: NotificationReceiver? = null
    lateinit var m_context: Context
    lateinit var m_listenner: ShowNotificationListener

    init {
        this.m_context = m_context
        this.m_listenner = listenner
    }

    fun registerBroadcastConnection() {
        if (m_notificationReceiver == null) {
            m_notificationReceiver = object : NotificationReceiver() {
                override fun handleNotification(notification: NotificationLocal) {
                    if (notification!!.type.equals(Constant.NOTIFICATION_SEND_ORDER_TO_ADMIN)) {
                        m_listenner.reloadAssignOrder(notification)
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(m_context).registerReceiver(m_notificationReceiver!!,
                IntentFilter(PUSH_NOTIFICATION))
    }


    fun unregisterBroadcastConnection() {
        if (m_notificationReceiver != null) {
            LocalBroadcastManager.getInstance(m_context).unregisterReceiver(m_notificationReceiver!!)
            m_notificationReceiver = null
        }
    }

    open class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val m_notification = Parcels.unwrap<NotificationLocal>(intent.getParcelableExtra<Parcelable>(INFO_NOTIFICATION))
            handleNotification(m_notification)
        }

        open fun handleNotification(notification: NotificationLocal) {}
    }

    interface ShowNotificationListener {
        fun reloadAssignOrder(notification: NotificationLocal)
    }

}