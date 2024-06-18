package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("badge")
	val badge: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("points")
	val points: Int
)

data class CreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
