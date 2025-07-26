package com.icdid.notemark.di

import com.icdid.notemark.MainViewModel
import com.icdid.notemark.NoteMarkApplication
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)

    single<CoroutineScope> {
        (androidApplication() as NoteMarkApplication).applicationScope
    }
}