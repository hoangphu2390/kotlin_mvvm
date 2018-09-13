package com.basekotlin.di.builder

import com.basekotlin.di.module.FragmentModule
import com.basekotlin.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by HP on 13/09/2018.
 */
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    internal abstract fun bindHomeFragment(): HomeFragment

}