package com.basekotlin.define

import com.basekotlin.R

/**
 * Created by HP on 07/09/2018.
 */
object Constant {

    // KEY GENERAL APPLICATION
    var SERVER_ADDRESS = "https://api-hih.hdev.kennjdemo.com/api/"
    var KEY_STRIPE = "pk_test_HjLQMvp6u40kUh0kDKzKE8lH"
    var ACCESS_TOKEN: String? = null
    var MAX_SELECT_IMAGE = 4
    var REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    // KEY NETWORK
    var ACTION_WIFI_CONECTION = "android.net.wifi.WIFI_STATE_CHANGED"
    var ACTION_CONNECTION = "android.net.conn.CONNECTIVITY_CHANGE"

    // KEY SHARE PREFERENCE
    var PREF_KEY_NAME = "PrefName"
    var PREF_KEY_USER_DEVICE_TOKEN = "PREF_KEY_USER_DEVICE_TOKEN"

    // KEY BROADCAST RECEIVER
    var PUSH_NOTIFICATION = "pushNotification"
    var INFO_NOTIFICATION = "info_notification"
    var INFO_DRIVER = "info_driver"

    // KEY REQUEST CODE
    var REQUEST_CODE_VIDEO_CAPTURE = 101
    var USER_AVATAR_CIRCLE_CROP = 30
    var REQUEST_PERMISSION_SETTING = 2
    var REQUEST_CODE_PLACE_AUTO_COMPLETE = 1
    var REQUEST_CODE_LOAD_IMAGE_FROM_GALERY = 2
    var REQUEST_CODE_LOAD_IMAGE_FROM_CAMERA = 100


    // KEY NOTIFICATION UTILS
    var NOTIFICATION_ID = 100
    var NOTIFICATION_ID_BIG_IMAGE : Int = 101

    // TIMER
    var TIME_SECONDS_SPLASH_SCREEN = 2000

    // TAB NAME IN NOTIFICATION HISTORY
    var TAB_ORDER_ASSIGN = "ASSIGN"
    var TAB_ORDER_WAITING = "WAITING"
    var TAB_ORDER_STATUS = "STATUS"
    var TAB_APPROVAL = "APPROVAL"
    var TAB_CREATE = "CREATE"
    var NUMBER_TAB_ORDER = 2


    var RESIZE_WIDTH_ICON_ADDRESS = 100
    var RESIZE_HEIGHT_ICON_ADDRESS = 100
    var RESIZE_WIDTH_ICON_TRACKING = 120
    var RESIZE_HEIGHT_ICON_TRACKING = 120

    // KEY LOGIN LOGOUT
    var LOGIN = "loginApp"
    var LOGOUT = "logout"

    var TYPE_DRIVER = "driver"
    var TYPE_ORGANIZATION = "organization"
    var TYPE_PERSONAL = "personal"

    var NOTIFICATION_SEND_ORDER_TO_ADMIN = "send_order_to_admin"


    //TAB_NUMBER
    val TAB_ORDER_LIST = 0
    val TAB_SIGN_UP = 1
    val TAB_MAP = 2
    val TAB_NOTIFICATION = 3
    val TAB_SETTINGS = 4
}
