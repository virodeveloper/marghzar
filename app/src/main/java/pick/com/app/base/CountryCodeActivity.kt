package pick.com.app.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.addTextWatcher
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.isNetworkAvailable
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.country_code_listing_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import pick.com.app.base.model.CountryCodeModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.CountryCodeListingLayoutBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls


class CountryCodeActivity : BaseActivity() , UniverSalBindAdapter.ItemAdapterListener, onResponse {
    override fun onItemSelected(item: Any) {

        finish()

    }


    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        progress_bar.hide()
        if ((result as CountryCodeModel).data!=null) {

            recyclerView.layoutManager=LinearLayoutManager(this)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            recyclerView.adapter =
                adaper
            adaper. addAll((result as CountryCodeModel).data as ArrayList<Any>)
        }
    }

    override fun onError(error: String?) {
        progress_bar.hide()

        showMessage("unsucess",error!!)
    }


    lateinit var countryCodeListingLayoutBinding: CountryCodeListingLayoutBinding
   lateinit var adaper:UniverSalBindAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryCodeListingLayoutBinding=DataBindingUtil.setContentView(this, pick.com.app.R.layout.country_code_listing_layout)
        countryCodeListingLayoutBinding.activity=this
        val toolbarCustom =
            ToolbarCustom(ToolbarCustom.lefticon, "SELECT A COUNTRY", ToolbarCustom.NoIcon,ToolbarCustom.NoIcon)
        left_Icon.onClick { onBackPressed() }
        countryCodeListingLayoutBinding.toolbar=toolbarCustom
        CountryCodeModel.Data.activity=this
         adaper=UniverSalBindAdapter(pick.com.app.R.layout.custom_country_code,this)
        progress_bar.hide()
        if (isNetworkAvailable()) getAllCountryCode()


        edit_query.addTextWatcher { s, start, before, count ->
            adaper.filter.filter(s.toString());
        }


    }


    fun getAllCountryCode(){

        var jsonObject=JSONObject()

        jsonObject.put("name","dsfdsf")

        runOnUiThread {
            // Stuff that updates the UI

            progress_bar.hide()
            // call runnable here
            ApiServices<CountryCodeModel>().callApiinObject(
                Urls.GET_CountriesCode,
                this,
                jsonObject ,
                CountryCodeModel::class.java,
                true, this
            )
        }


    }


}
