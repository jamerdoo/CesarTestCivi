package com.cesar.cesarcivitatis.network

import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.utils.Logger
import com.cesar.cesarcivitatis.utils.RestApi
import com.cesar.cesarcivitatis.network.apiservice.DataApi
import retrofit2.awaitResponse

class RetrofitProvider : Logger {

    override val nameClass: String get() = "--->" + javaClass.simpleName
    private val retrofit by lazy { RestApi.ServiceBuilder.buildService(DataApi::class.java) }

    suspend fun getData(): Resource<ArrayList<MyDataResponse>> {
        val data = retrofit.getData().awaitResponse()

        return if (data.isSuccessful) {
            Resource.Success(data.body()!!)
        } else {
            Resource.Error(Throwable(""))
        }
    }

}