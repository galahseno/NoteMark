package com.icdid.core.data.di

import com.icdid.core.data.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory().build() }
}