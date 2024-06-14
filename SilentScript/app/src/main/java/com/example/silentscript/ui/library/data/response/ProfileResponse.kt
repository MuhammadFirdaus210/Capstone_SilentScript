package com.example.silentscript.ui.library.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("score")
	val score: Int,

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("email")
	val email: String
)

data class CreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
