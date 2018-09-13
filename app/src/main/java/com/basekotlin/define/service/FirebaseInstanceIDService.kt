package com.basekotlin.define.service

import android.content.Context
import com.basekotlin.define.Constant.PREF_KEY_NAME
import com.basekotlin.define.Constant.PREF_KEY_USER_DEVICE_TOKEN
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by HP on 07/09/2018.
 */
class FirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        var refreshedToken = FirebaseInstanceId.getInstance().token
        saveDeviceToken(refreshedToken)
    }

    fun saveDeviceToken(token: String?) {
        var context = applicationContext
        var prefs = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_KEY_USER_DEVICE_TOKEN, token).apply()
    }
}