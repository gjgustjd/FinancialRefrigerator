package com.study.data.repository.remote

import com.study.domain.model.WebLinkItem
import com.study.domain.model.agricultureAPI.SeasonlyAgricultureIAPItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RefrigeratorRemoteDataSource {
    suspend fun getCrawlWebLinkItemFlow(keyword: String,endNum: Int, coroutineNum: Int): Flow<WebLinkItem>
    suspend fun getAgriculturalProductData(product_name:String):Response<SeasonlyAgricultureIAPItem>
}