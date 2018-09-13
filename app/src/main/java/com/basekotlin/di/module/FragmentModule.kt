package com.basekotlin.di.module

import android.arch.lifecycle.ViewModel
import br.com.wellingtoncosta.amdk.di.keys.ViewModelKey
import com.basekotlin.ui.home.HomeViewModel
import com.basekotlin.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by HP on 13/09/2018.
 */

@Module
interface FragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}