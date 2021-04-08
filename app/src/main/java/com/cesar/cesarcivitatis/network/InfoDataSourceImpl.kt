package com.cesar.cesarcivitatis.network

import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.domain.repository.InfoDataSource
import com.cesar.cesarcivitatis.network.RetrofitProvider


class InfoDataSourceImpl(private val retrofitProvider: RetrofitProvider) : InfoDataSource {

    override suspend fun getInfo(): Resource<ArrayList<MyDataResponse>> {
        return retrofitProvider.getData()
    }
}