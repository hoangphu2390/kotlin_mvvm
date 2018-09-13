package com.basekotlin.base

import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife

/**
 * Created by HP on 07/09/2018.
 */
class BaseHolder<V>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        ButterKnife.bind(this, itemView)
        bindEvent()
    }

    fun bindData(data: Any?) {}
    fun bindEvent() {}
}