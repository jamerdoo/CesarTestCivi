package com.cesar.cesarcivitatis.domain.repository

import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.domain.repository.InfoDataSource

class InfoRepository(private val infoDataSource: InfoDataSource) {

    suspend fun getInfo() : Resource<ArrayList<MyDataResponse>> = infoDataSource.getInfo()
}