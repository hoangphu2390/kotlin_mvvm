package com.basekotlin.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*


class BaseAdapter<V, VH : BaseHolder<*>>(protected var m_inflater: LayoutInflater,
                                         protected var m_dataSource: MutableList<V>) : RecyclerView.Adapter<VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(parent, viewType)
    }

    var dataSource: List<V>
        get() = m_dataSource
        set(m_dataSource) = try {
            this.m_dataSource = ArrayList(m_dataSource)
            notifyDataSetChanged()
        } catch (e: IllegalStateException) {
        }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(m_dataSource[position])
    }

    override fun getItemCount(): Int {
        return m_dataSource.size
    }

    fun appendItem(item: V) {
        if (this.m_dataSource.isEmpty()) {
            this.m_dataSource = ArrayList()
        }
        this.m_dataSource.add(item)
        notifyItemInserted(itemCount)
    }

    fun removeAtPosition(position: Int) {
        if (this.m_dataSource.size > position) {
            m_dataSource.removeAt(position)
            notifyItemRangeRemoved(position, 1)
        }
    }

    fun appendItems(items: List<V>) {
        if (m_dataSource.isEmpty()) {
            dataSource = items
        } else {
            val positionStart = itemCount - 1
            m_dataSource.addAll(items)
            notifyItemRangeInserted(positionStart, items.size)
        }
    }

    fun addItemAtFirst(item: V) {
        if (this.m_dataSource.isEmpty()) {
            this.m_dataSource = ArrayList()
        }
        this.m_dataSource.add(0, item)
        notifyItemInserted(0)
    }

    fun addAtFirstAndRemoveEnd(item: V) {
        if (this.m_dataSource.isEmpty()) {
            this.m_dataSource = ArrayList()
        }
        this.m_dataSource.add(0, item)
        this.m_dataSource.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount - 1)
        notifyItemInserted(0)
    }
}


