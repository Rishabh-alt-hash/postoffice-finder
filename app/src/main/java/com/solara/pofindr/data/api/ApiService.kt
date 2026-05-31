package com.solara.pofindr.data.api

import com.solara.pofindr.data.model.PostOfficeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("postoffice/{city}")
    suspend fun getPostOffices(
        @Path("city")
        city : String
    ) : Response<PostOfficeResponse>
}