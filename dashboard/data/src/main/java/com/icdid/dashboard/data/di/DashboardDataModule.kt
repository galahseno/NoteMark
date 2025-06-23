package com.icdid.dashboard.data.di

import com.icdid.dashboard.data.NotesRepositoryImpl
import com.icdid.dashboard.data.local.RoomLocalDataSource
import com.icdid.dashboard.data.network.KtorRemoteDataSource
import com.icdid.dashboard.domain.LocalDataSource
import com.icdid.dashboard.domain.NotesRepository
import com.icdid.dashboard.domain.RemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dashboardDataModule = module {
    singleOf(::KtorRemoteDataSource) bind RemoteDataSource::class
    singleOf(::RoomLocalDataSource) bind LocalDataSource::class
    singleOf(::NotesRepositoryImpl) bind NotesRepository::class

}