package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class KataResponse(

	@field:SerializedName("penjelasan")
	val penjelasan: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("video")
	val video: String
)
