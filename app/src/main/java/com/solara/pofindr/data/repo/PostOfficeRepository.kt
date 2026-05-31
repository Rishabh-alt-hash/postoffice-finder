package com.solara.pofindr.data.repo

import androidx.lifecycle.LiveData
import com.solara.pofindr.data.api.ApiService
import com.solara.pofindr.data.dao.PostOfficeDao
import com.solara.pofindr.data.model.PostOffice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PostOfficeRepository(
    private val postOfficeDao: PostOfficeDao,
    private val postOfficeApiService: ApiService
) {

    val allPostOffices: LiveData<List<PostOffice>> = postOfficeDao.getAllPostOffices()

    suspend fun fetchPostOfficesFormApi(city: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = postOfficeApiService.getPostOffices(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        postOfficeDao.insertAll(it.postOffices)
                    }
                } else {
                    // error response case
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}