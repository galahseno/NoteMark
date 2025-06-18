package com.icdid.auth.data.di

import com.icdid.auth.data.EmailPatternValidator
import com.icdid.auth.data.AuthRepositoryImpl
import com.icdid.auth.domain.AuthRepository
import com.icdid.auth.domain.PatternValidator
import com.icdid.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}