package com.cesar.cesarcivitatis.network.apiservice

import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import retrofit2.Call
import retrofit2.http.*

interface DataApi {

    @Headers("Content-Type: application/json")
    @GET("positions.json")
    fun getData(): Call<ArrayList<MyDataResponse>>
}