package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auth (
    val status: Int,
    val message: String? = null,
    val name: String? = null,
    val token: String? = null,
    val error: String? = null,
    val detail: String? = null
) :Parcelable