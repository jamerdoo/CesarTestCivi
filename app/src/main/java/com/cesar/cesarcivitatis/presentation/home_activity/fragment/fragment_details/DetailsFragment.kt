package com.cesar.cesarcivitatis.presentation.home_activity.fragment.fragment_details

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.cesar.cesarcivitatis.MyApplication
import com.cesar.cesarcivitatis.R
import com.cesar.cesarcivitatis.databinding.FragmentDetailsBinding
import com.cesar.cesarcivitatis.domain.entity.MyDataResponse
import com.cesar.cesarcivitatis.utils.WebViewDialog
import com.cesar.cesarcivitatis.utils.loadUrl
import com.google.gson.Gson


class DetailsFragment : Fragment() {


    private var json : String=""
    private var data : MyDataResponse?=null
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleData()
        initComponents()
    }

    private fun getBundleData() {
        val bundle= this.arguments
        if(bundle!=null) {
            json = args.itemData
            data= Gson().fromJson(json,MyDataResponse::class.java)
        }else{
            //Handle Error
            return
        }
    }

    private fun initComponents() {
        if(data!=null) {
            with(binding) {
                tvTittle.text= getString(R.string.tittle).plus(": ").plus(data?.title)
                tvName.text= getString(R.string.name).plus(": ").plus(data?.company)
                tvLocation.text= getString(R.string.location).plus(": ").plus(data?.location)
                tvDate.text=getString(R.string.date).plus(": ").plus(data?.created_at)
                tvUrl.text=getString(R.string.url).plus(": ").plus(data?.company_url)
                tvType.text=getString(R.string.type).plus(": ").plus(data?.type)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDetails.text= getString(R.string.location).plus(": ").plus(Html.fromHtml(data?.description, Html.FROM_HTML_MODE_LEGACY))
                }else{
                    tvDetails.text= getString(R.string.location).plus(": ").plus(Html.fromHtml(data?.description))
                }

                ivLogo.loadUrl(data?.company_logo)

                tvUrl.setOnClickListener{
                    val webViewDialog = WebViewDialog()
                    webViewDialog.setUrl(data?.url)
                    showDialogFragment(webViewDialog)
                }
            }
        }
    }

    private fun showDialogFragment(dialogFragment: DialogFragment) {
        val aux= requireActivity().supportFragmentManager
        dialogFragment.show(aux,"Dialog")
    }
}