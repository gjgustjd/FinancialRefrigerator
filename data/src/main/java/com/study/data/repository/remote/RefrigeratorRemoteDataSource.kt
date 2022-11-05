package com.study.data.repository.remote

import com.study.domain.model.WebLinkItem
import com.study.domain.model.agricultureAPI.SeasonlyAgricultureIAPItem
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RefrigeratorRemoteDataSource {
    suspend fun getCrawlWebLinkItemFlow(keyword: String,endNum: Int, coroutineNum: Int): Flow<WebLinkItem>
    fun getAgriculturalProductData(product_name:String): Single<Response<SeasonlyAgricultureIAPItem>>
}