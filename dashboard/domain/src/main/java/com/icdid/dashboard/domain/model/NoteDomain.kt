package com.icdid.dashboard.domain.model

data class NoteDomain(
    val id: String = "",
    val title: String = "New Note",
    val content: String = "",
    val createdAt: String = "",
    val lastEditedAt: String = "",
)