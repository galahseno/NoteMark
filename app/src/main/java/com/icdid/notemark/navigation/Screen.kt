package com.icdid.notemark.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth {
        @Serializable
        data object Landing

        @Serializable
        data object Login

        @Serializable
        data object Register
    }

    @Serializable
    data object Home {
        @Serializable
        data object AllNotes

        @Serializable
        data class NoteDetail(
            val isNewNote: Boolean = false,
            val noteId: String
        )

        @Serializable
        data object Settings
    }
}