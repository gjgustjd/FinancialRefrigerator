package com.study.data.model.agricultureAPI

data class APIContent(
    val endRow: Int,
    val result: ProduceResult,
    val row: List<ProduceItem>,
    val startRow: Int,
    val totalCnt: Int
)