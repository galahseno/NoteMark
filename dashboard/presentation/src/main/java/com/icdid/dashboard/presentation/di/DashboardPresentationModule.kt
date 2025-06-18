package com.icdid.dashboard.presentation.di

import com.icdid.dashboard.presentation.all_notes.AllNotesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardPresentationModule = module {
    viewModelOf(::AllNotesViewModel)
}