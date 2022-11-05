package com.study.data.repository.remote.api

import com.study.domain.model.agricultureAPI.SeasonlyAgricultureIAPItem
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AgriculturalProductApi {
    companion object {
        const val PATH = "json/Grid_20171128000000000572_1"
    }

    @GET("$PATH/1/1")
    fun getProductItemData(
        @Query("PRDLST_NM") name: String
    ): Single<Response<SeasonlyAgricultureIAPItem>>
}