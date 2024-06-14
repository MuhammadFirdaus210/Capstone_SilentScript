package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class HurufResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("penjelasan")
	val penjelasan: String,

	@field:SerializedName("id")
	val id: String
)
