package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class GameResponse(

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("levelId")
	val levelId: Int,

	@field:SerializedName("image")
	val image: String
)
