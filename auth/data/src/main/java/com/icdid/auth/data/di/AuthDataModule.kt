package com.icdid.auth.data.di

import com.icdid.auth.data.EmailPatternValidator
import com.icdid.auth.domain.PatternValidator
import com.icdid.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}