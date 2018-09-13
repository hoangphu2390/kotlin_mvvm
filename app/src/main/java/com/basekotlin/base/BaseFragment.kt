package com.basekotlin.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import butterknife.ButterKnife
import com.basekotlin.R
import com.basekotlin.ui.main.MainActivity
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by HP on 07/09/2018.
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    @Inject
    protected lateinit var m_eventBus: EventBus

    var m_activity: BaseActivity<*, *>? = null
    var m_viewDataBinding: T? = null
    var m_viewModel: V? = null
    var m_rootView: View? = null
    var mainAT: MainActivity? = null

    var isInLeft: Boolean = false
    var isOutLeft: Boolean = false
    var isCurrentScreen: Boolean = false
    var isLoaded = false
    var isDead = false
    val m_object = Object()

    fun getLeftInAnimId(): Int {
        return R.anim.slide_in_left
    }

    fun getRightInAnimId(): Int {
        return R.anim.slide_in_right
    }

    fun getLeftOutAnimId(): Int {
        return R.anim.slide_out_left
    }

    fun getRightOutAnimId(): Int {
        return R.anim.slide_out_right
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainAT = context as MainActivity?
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.m_activity = activity
            activity!!.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (isCurrentScreen) {
            setNavigation()
        }
        var animation: Animation? = null
        if (enter) {
            val left = getLeftInAnimId()
            val right = getRightInAnimId()
            animation = AnimationUtils.loadAnimation(activity, if (isInLeft) left else right)
        } else {
            val left = getLeftOutAnimId()
            val right = getRightOutAnimId()
            animation = AnimationUtils.loadAnimation(activity, if (isOutLeft) left else right)
        }
        if (animation != null) {
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
        return animation
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            m_viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            m_rootView = m_viewDataBinding!!.root
            ButterKnife.bind(this, m_rootView!!)
        } catch (ex: Exception) {
        }

        return m_rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        synchronized(m_object) {
            isLoaded = true
            m_object.notifyAll()
        }
        super.onViewCreated(view, savedInstanceState)

        m_viewModel = getViewModel()
        lifecycle.addObserver(m_viewModel!!)
        m_viewDataBinding!!.setVariable(getBindingVariable(), m_viewModel)
        m_viewDataBinding!!.executePendingBindings()
        m_viewModel!!.onCreateViewModel()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDetach() {
        m_activity = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoaded = false
        mainAT = null
        if (m_viewModel != null) {
            lifecycle.removeObserver(m_viewModel!!)
            m_viewModel = null
        }
    }

    override fun onDestroyView() {
        isDead = true
        super.onDestroyView()
    }

    fun getBaseActivity(): BaseActivity<*, *>? {
        return m_activity
    }

    fun getViewDataBinding(): T? {
        return m_viewDataBinding
    }

    fun setNavigation() {
        if (mainAT != null) {
//            mainAT!!.setTitleScreen(getTitleScreen())
//            mainAT!!.setStateBottomBar(getTabBarBottom())
//            mainAT!!.setStateButtonBack(getStateButtonBack())
        }
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    /**
     * Override for set binding variable
     *
     * @return variable card_id
     */
    @IdRes
    abstract fun getBindingVariable(): Int

    protected fun reloadData() {}


    /**
     * @return layout resource card_id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

//    public abstract void performDependencyInjection();

    abstract fun getTitleScreen(): String

    abstract fun getStateButtonBack(): Boolean

    abstract fun getTabBarBottom(): Boolean
}