package com.icdid.notemark

import android.app.Application
import com.icdid.auth.data.di.authDataModule
import com.icdid.auth.presentation.di.authPresentationModule
import com.icdid.core.data.di.coreDataModule
import com.icdid.core.presentation.utils.NetworkMonitor
import com.icdid.dashboard.data.di.dashboardDataModule
import com.icdid.dashboard.presentation.di.dashboardPresentationModule
import com.icdid.notemark.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class NoteMarkApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@NoteMarkApplication)
            androidLogger()
            workManagerFactory()
            modules(
                appModule,
                authPresentationModule,
                authDataModule,
                coreDataModule,
                dashboardPresentationModule,
                dashboardDataModule
            )

            get<NetworkMonitor>().startMonitoring()
        }
    }
}