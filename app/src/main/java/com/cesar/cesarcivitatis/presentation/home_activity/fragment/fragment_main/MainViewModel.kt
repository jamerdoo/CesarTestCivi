package com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.domain.repository.InfoDataSource
import com.cesar.cesarcivitatis.domain.repository.InfoRepository
import com.cesar.cesarcivitatis.domain.usercase.GetInfo
import com.cesar.cesarcivitatis.network.InfoDataSourceImpl
import com.cesar.cesarcivitatis.network.RetrofitProvider
import com.cesar.cesarcivitatis.utils.Logger
import com.cesar.cesarcivitatis.utils.convertToDate
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList


class MainViewModel : ViewModel(), Logger {

    override val nameClass: String get() = "--->"+javaClass.simpleName
    private var infoDataSource: InfoDataSource = InfoDataSourceImpl(RetrofitProvider())
    private var getInfo = GetInfo(InfoRepository(infoDataSource))

    var liveData = MutableLiveData<Resource<ArrayList<MyDataResponse>>>()

    fun getData(){

        liveData.value = Resource.Loading()

        getInfo.invoke(onResult = { it ->

            val filterList: MutableList<MyDataResponse> = arrayListOf()

            it.data?.forEach {
                if(!filterDate(convertToDate(it.created_at))){
                    filterList.add(it)
                }
            }

            liveData.value = Resource.Success( ArrayList(filterList))

        }, onError = {
            logD(it.localizedMessage ?: "")
            logD(it.message ?: "")
        })
    }

    private fun filterDate(date: Date?) : Boolean = DateTime(date).plusDays(5).isBefore(Date().time)

}