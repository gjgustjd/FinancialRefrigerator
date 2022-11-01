package com.study.data.repository.remote

import com.study.domain.model.WebLinkItem
import kotlinx.coroutines.flow.Flow

interface RefrigeratorRemoteDataSource {
    suspend fun getCrawlWebLinkItemFlow(keyword: String,endNum: Int, coroutineNum: Int): Flow<WebLinkItem>
    suspend fun getAgriculturalProductData(product_name:String)
}