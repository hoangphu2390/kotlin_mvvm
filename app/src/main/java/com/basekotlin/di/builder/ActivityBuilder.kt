package com.basekotlin.di.builder

import com.basekotlin.di.module.ActivityModule
import com.basekotlin.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by HP on 07/09/2018.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    internal abstract fun bindMainActivity(): MainActivity

}