package com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.cesar.cesarcivitatis.R
import com.cesar.cesarcivitatis.databinding.MainFragmentBinding
import com.cesar.cesarcivitatis.domain.Resource
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_details.DetailsFragment
import com.cesar.cesarcivitatis.utils.Logger
import com.cesar.cesarcivitatis.utils.printToast
import com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_main.adapter.DataAdapter
import com.cesar.cesarcivitatis.utils.Consts
import com.google.gson.Gson
import java.util.*

class MainFragment : Fragment(), Logger, DataAdapter.OnItemClickListener {

    override val nameClass: String get() = "--->"+javaClass.simpleName
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private val initAdapter =
        DataAdapter(
            emptyList(),
            this
        )

    private var itemList: MutableList<MyDataResponse> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        with(binding){
            recycler.adapter = initAdapter
            logD(Calendar.getInstance().time.toString())
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showProgress(true)
                }
                is Resource.Success -> {
                    showProgress(false)
                    logD(resource.data?.size.toString())
                    updateDataList(resource.data ?: emptyList())
                }
                is Resource.Error -> {
                    showProgress(false)
                    showSnackBarFailed(getString(R.string.handle_error))
                }
            }
        })
        viewModel.getData()
    }

    private fun updateDataList(list: List<MyDataResponse>) {
        this.itemList = list as MutableList<MyDataResponse>
        initAdapter.listItems= itemList
    }

    private fun showProgress(control: Boolean) {binding.progress.visibility= (if (control) View.VISIBLE else View.GONE)}

    private fun showSnackBarFailed(message: String) {
        requireActivity().printToast(message)
    }

    override fun onClickItem(item: MyDataResponse, TAG: String) {
        when(TAG){
            Consts.Adapters.ALL->{
                val args = Bundle()
                args.putString(Consts.Arg.ITEM_DATA,Gson().toJson(item))
                val action: NavDirections = MainFragmentDirections.actionMainFragmentToDetailsFragment(Gson().toJson(item))
                Navigation.findNavController(requireView())
                    .navigate(action)
            }
        }
    }
}