package com.example.silentscript.ui.library

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val name: String,
) : Parcelable

