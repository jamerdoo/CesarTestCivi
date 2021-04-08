package com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cesar.cesarcivitatis.R
import com.cesar.cesarcivitatis.databinding.ItemsViewBinding
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.utils.Consts
import com.cesar.cesarcivitatis.utils.inflate
import com.cesar.cesarcivitatis.utils.loadUrl
import kotlin.properties.Delegates

class DataAdapter (listItems: List<MyDataResponse> = emptyList(), private val listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>(){

    var listItems: List<MyDataResponse> by Delegates.observable(listItems){ _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.inflate(
                R.layout.items_view
            ), listener
        )


    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    interface OnItemClickListener {
        fun onClickItem(item: MyDataResponse, TAG: String)
    }

    class ViewHolder(private val view: View, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(view){

        private val binding = ItemsViewBinding.bind(view)

        fun bind (item: MyDataResponse){
            with(binding){
                tvName.text= item.company
                tvTittle.text= item.title
                tvType.text= item.type
                tvDate.text= item.created_at
                tvLocation.text= item.location
                imageView.loadUrl(item.company_logo)

                itemView.setOnClickListener{
                  listener.onClickItem(item, Consts.Adapters.ALL)
                }
            }
        }
    }
}