package com.cesar.cesarcivitatis.domain.repository

import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse


interface InfoDataSource {

    suspend fun getInfo(): Resource<ArrayList<MyDataResponse>>
}