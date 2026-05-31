package com.solara.pofindr.data.model

import com.google.gson.annotations.SerializedName

data class PostOfficeResponse(

	@field:SerializedName("Status")
	val status: String,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("PostOffice")
	val postOffices: List<PostOffice>
)

