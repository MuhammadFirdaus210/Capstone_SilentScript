package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class LibraryResponse(

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("id")
	val id: String
)