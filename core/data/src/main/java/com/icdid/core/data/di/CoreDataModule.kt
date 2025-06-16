package com.icdid.core.data.di

import com.icdid.core.data.HttpClientFactory
import com.icdid.core.data.NoteMarkAuthRepositoryImpl
import com.icdid.core.domain.NoteMarkAuthRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory().build() }
    singleOf(::NoteMarkAuthRepositoryImpl) bind NoteMarkAuthRepository::class
}