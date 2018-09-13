package com.basekotlin.define.service

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.basekotlin.data.local.NotificationLocal
import com.basekotlin.define.Constant.INFO_NOTIFICATION
import com.basekotlin.define.Constant.PUSH_NOTIFICATION
import com.basekotlin.util.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import org.parceler.Parcels

/**
 * Created by HP on 07/09/2018.
 */
class FirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage == null) return
        var notification = NotificationLocal()
        try {
            notification.message = remoteMessage.notification!!.body
            var params = remoteMessage.data
            var jsonObject = JSONObject(params)

            if (jsonObject.has("type"))
                notification.type = jsonObject.getString("type")

            if (jsonObject.has("real_amount"))
                notification.real_amount = jsonObject.getString("real_amount")

            if (jsonObject.has("ship_fee"))
                notification.ship_fee = jsonObject.getString("ship_fee")

            if (jsonObject.has("estimated_time"))
                notification.estimated_time = jsonObject.getString("estimated_time")

            if (jsonObject.has("time"))
                notification.time = jsonObject.getString("time")

            if (jsonObject.has("driver_name"))
                notification.driver_name = jsonObject.getString("driver_name")

            if (jsonObject.has("driver_avatar"))
                notification.driver_avatar = jsonObject.getString("driver_avatar")

        } catch (e: Exception) {
            e.printStackTrace()
        }

        handleNotification(notification)
    }

    private fun handleNotification(notification: NotificationLocal) {
        val pushNotification = Intent(PUSH_NOTIFICATION)
        pushNotification.putExtra(INFO_NOTIFICATION, Parcels.wrap<Any>(notification))
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
        val notificationUtils = NotificationUtils(applicationContext)
        notificationUtils.playNotificationSound()
    }
}