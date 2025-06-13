package com.icdid.notemark

import android.app.Application
import com.icdid.auth.presentation.di.authPresentationModule
import com.icdid.notemark.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NoteMarkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteMarkApplication)
            androidLogger()

            modules(
                appModule,
                authPresentationModule,
            )
        }
    }
}