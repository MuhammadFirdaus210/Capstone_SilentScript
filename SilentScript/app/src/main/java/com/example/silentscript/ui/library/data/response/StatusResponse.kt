package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(

	@field:SerializedName("data")
	val data: DataStatus,

	@field:SerializedName("message")
	val message: String
)

data class DataStatus(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("points")
	val points: Int
)
