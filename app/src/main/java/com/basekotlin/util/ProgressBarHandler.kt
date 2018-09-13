package com.basekotlin.util

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.basekotlin.R

/**
 * Created by HP on 07/09/2018.
 */
class ProgressBarHandler {

    lateinit var mDialog: AlertDialog

    fun ProgressBarHandler(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context, R.style.ThemePopupProgress)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.layout_progress_loading, null)
        dialogBuilder.setView(dialogView)
        mDialog = dialogBuilder.create()
        mDialog.setCancelable(false)
    }

    fun showProgress() {
        if (!mDialog.isShowing)
            mDialog.show()
    }

    fun hideProgress() {
        if (mDialog.isShowing)
            mDialog.dismiss()
    }
}