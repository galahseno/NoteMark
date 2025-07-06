package com.icdid.notemark.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth {
        @Serializable
        data class Landing(
            val isSkipped: Boolean = false
        )

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