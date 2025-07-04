package com.icdid.dashboard.presentation.di

import com.icdid.dashboard.presentation.all_notes.AllNotesViewModel
import com.icdid.dashboard.presentation.note_detail.NoteDetailViewModel
import com.icdid.dashboard.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardPresentationModule = module {
    viewModelOf(::AllNotesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::NoteDetailViewModel)
}