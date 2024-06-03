package com.example.silentscript

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Abjad(
    val name: String,
) : Parcelable
