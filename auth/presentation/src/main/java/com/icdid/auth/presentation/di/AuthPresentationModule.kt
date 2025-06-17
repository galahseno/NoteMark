package com.icdid.auth.presentation.di

import com.icdid.auth.presentation.login.LoginViewModel
import com.icdid.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}