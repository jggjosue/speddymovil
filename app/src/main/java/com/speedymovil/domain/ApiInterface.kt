package com.speedymovil.domain

import com.speedymovil.model.HitsListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search_by_date?query=android?")
    suspend fun getMovies(@Query("page")page: Int):Response<HitsListModel>
}