package com.cesar.cesarcivitatis.domain.usercase


import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.domain.repository.InfoRepository

class GetInfo(private val infoRepository: InfoRepository) :
    BaseUseCase<Resource<ArrayList<MyDataResponse>>, Any>() {

    override suspend fun run(params: Any?): Resource<ArrayList<MyDataResponse>>{
        return infoRepository.getInfo()
    }
}