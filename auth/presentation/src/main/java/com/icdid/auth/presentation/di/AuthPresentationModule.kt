package com.icdid.auth.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.icdid.auth.presentation.login.LoginScreenViewModel

val authPresentationModule = module {
    viewModelOf(::LoginScreenViewModel)
}