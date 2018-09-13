package com.basekotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import com.basekotlin.util.ProgressBarHandler
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by HP on 07/09/2018.
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : FragmentActivity(), BaseFragment.Callback {

    @Inject
    lateinit var m_eventBus: EventBus

    lateinit var m_progressBarHandler: ProgressBarHandler

    lateinit var m_viewDataBinding: T
    lateinit var m_viewModel: V


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    fun performDataBinding() {
        m_viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        m_viewModel = if (m_viewModel == null) getViewModel() else m_viewModel
        lifecycle.addObserver(m_viewModel)
        m_viewDataBinding.setVariable(getBindingVariable(), m_viewModel)
        m_viewDataBinding.executePendingBindings()
        m_viewModel.setActivity(this)
        m_viewModel.onCreateViewModel()
    }

    fun showProgressBar() {
        if (isFinishing) return
        m_progressBarHandler.showProgress()
    }

    fun hideProgressBar() {
        if (isFinishing) return
        m_progressBarHandler.hideProgress()
    }

    fun getViewDataBinding(): T {
        return m_viewDataBinding
    }

    abstract fun getViewModel(): V

    @IdRes
    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onFragmentAttached() {}

    override fun onFragmentDetached(tag: String) {}
}