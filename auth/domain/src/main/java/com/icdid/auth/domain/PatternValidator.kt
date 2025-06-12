package com.icdid.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}