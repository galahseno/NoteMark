package com.icdid.notemark

import android.app.Application
import com.icdid.auth.data.di.authDataModule
import com.icdid.auth.presentation.di.authPresentationModule
import com.icdid.core.data.di.coreDataModule
import com.icdid.dashboard.presentation.di.dashboardPresentationModule
import com.icdid.notemark.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NoteMarkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@NoteMarkApplication)
            androidLogger()

            modules(
                appModule,
                authPresentationModule,
                authDataModule,
                coreDataModule,
                dashboardPresentationModule
            )
        }
    }
}