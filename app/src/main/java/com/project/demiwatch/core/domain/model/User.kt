package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val id: String,
    val email: String,
    val password: String,
    val token: String,
    val teleNumber: String,
    val userStatus: String,
    val safeRadius: String,
    ): Parcelable
