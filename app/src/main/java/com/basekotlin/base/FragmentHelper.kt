package com.basekotlin.base

import android.support.v4.app.FragmentManager
import java.util.*

/**
 * Created by HP on 07/09/2018.
 */
class FragmentHelper(fragmentAction: FragmentAction) {

    lateinit var pageList: ArrayList<Stack<BaseFragment<*, *>>>
    protected var pageIndex: Int = 0
    protected var pageSize: Int = 0
    protected var layoutId: Int = 0
    protected var childPage: Int = -1

    protected lateinit var fragmentManager: FragmentManager
    protected lateinit var buildFragments: Array<BaseFragment<*, *>?>
    private var fragmentAction: FragmentAction? = null

    init {
        this.fragmentAction = fragmentAction
        this.layoutId = fragmentAction.frameLayoutId
        this.fragmentManager = fragmentAction.setFragmentManager()
        this.buildFragments = fragmentAction.initFragment()
        initFragments(buildFragments)
    }

    private fun initFragments(fragments: Array<BaseFragment<*, *>?>?) {
        this.pageList = ArrayList(fragments!!.size)
        pageSize = fragments.size

        for (fragment in fragments) {
            val stack = Stack<BaseFragment<*, *>>()
            stack.push(fragment)
            this.pageList.add(stack)
        }

        val fragment = pageList[pageIndex].peek()
        if (fragment.isAdded || fragment.isDetached) {
            this.showFragment(pageIndex)
        } else {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.add(layoutId, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    fun peek(): BaseFragment<*, *> {
        return pageList[pageIndex].peek()
    }

    fun pushFragment(fragment: BaseFragment<*, *>) {
        val hideFragment = pageList[pageIndex].peek()
        pageList[pageIndex].push(fragment)

        fragment.isCurrentScreen = true
        hideFragment.isCurrentScreen = true

        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(layoutId, fragment)
        transaction.hide(hideFragment)
        transaction.commitAllowingStateLoss()

    }

    fun popFragment(): Boolean {
        return popFragment(1)
    }

    fun popFragmentToRoot(): Boolean {
        val level = pageList[pageIndex].size - 1
        return popFragment(level)
    }

    fun popFragment(level: Int): Boolean {
        var level = level
        if (level <= 0) return false
        if (pageList[pageIndex].size <= level) return false
        val transaction = fragmentManager!!.beginTransaction()

        while (level >= 1) {
            val fragment = pageList[pageIndex].pop()
            fragment.isCurrentScreen = true
            transaction.remove(fragment)
            level--
        }
        val showFragment = pageList[pageIndex].peek()
        showFragment.isCurrentScreen = true
        transaction.show(showFragment)
        transaction.commitAllowingStateLoss()
        return true
    }

    fun <E : BaseFragment<*, *>> popToFragment(aClass: Class<E>): Boolean {
        val level = findIndexFragmentChild(aClass)
        return popFragment(level)
    }

    fun <E : BaseFragment<*, *>> findIndexFragmentChild(aClass: Class<E>): Int {
        val size = getCurrentPageSize()
        var level = 0
        for (i in size - 1 downTo 0) {
            val baseFragment = pageList[pageIndex][i]
            if (aClass.simpleName == baseFragment.javaClass.simpleName) {
                return level
            } else {
                level++
            }
        }
        return -1
    }

    fun showFragment(index: Int) {
        if (index == pageIndex) return
        val showFragment = pageList[index].peek()
        val hideFragment = pageList[pageIndex].peek()
        val transaction = fragmentManager!!.beginTransaction()

        if (pageIndex > index) {
            showFragment.isInLeft = true
            hideFragment.isOutLeft = false
        } else {
            showFragment.isInLeft = false
            hideFragment.isOutLeft = true
        }
        showFragment.isCurrentScreen = false
        hideFragment.isCurrentScreen = false

        // TODO: Khong refresh lai UI, ko refresh lai data
        if (showFragment.isDetached || showFragment.isAdded) {
            transaction.show(showFragment)
        } else {
            transaction.add(layoutId, showFragment)
        }
        transaction.hide(hideFragment)

        // TODO; Refresh lai UI, refresh lai data
        //        if(showFragment.isDetached() || showFragment.isAdded()){
        //            transaction.attach(showFragment);
        //        }else{
        //            transaction.add(layoutId, showFragment);
        //        }
        //        transaction.detach(hideFragment);

        transaction.commitAllowingStateLoss()
        pageIndex = index
    }

    fun getCurrentPageSize(): Int {
        return pageList[pageIndex].size
    }

    fun getPageSizeByIndex(index: Int): Int {
        return pageList[index].size
    }

    fun _getPageIndex(): Int {
        return pageIndex
    }

    interface FragmentAction {

        val frameLayoutId: Int

        fun initFragment(): Array<BaseFragment<*, *>?>

        fun setFragmentManager(): FragmentManager
    }
}