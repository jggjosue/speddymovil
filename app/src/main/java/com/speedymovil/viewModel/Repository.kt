package com.speedymovil.viewModel

import com.speedymovil.model.HitsListModel
import com.speedymovil.utils.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getHitsList(page : Int): Response<HitsListModel> {
        return RetrofitInstance.api.getMovies(page)
    }
}