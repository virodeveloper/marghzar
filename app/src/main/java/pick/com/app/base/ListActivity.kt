package pick.com.app.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.country_code_listing_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import pick.com.app.R
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.varient.owner.activity.AddNewVehicleActivity
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ListActivity :BaseActivity(),UniverSalBindAdapter.ItemAdapterListener{
    override fun onItemSelected(model: Any) {
        AddNewVehicleActivity.onVehicleSpinner.getSpinnerSelectedItem(model,intent.extras.getString("url"))
        finish()
    }

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)

        if (intent.extras.getString("type_id")==null) {

            if (methodtype==Urls.BULK_AVAILABLE_LIST){

                var result=(result as VehicleModel.Data).data

                result.removeAll(AddNewVehicleActivity.activity.arrlistBultAvalibilit)
                adapter.addAll(result as ArrayList<Any>)
               /* for (item in (result as VehicleModel).data)
                {
                    var isavail=false

                        for (item1 in AddNewVehicleActivity.activity.arrlistBultAvalibilit){

                            if (item.avail_id==item1.avail_id)
                                isavail=true

                        }

                    if (!isavail)
                        adapter.add(item)

                }*/



            }else{
                adapter.addAll((result as VehicleModel.Data).data as ArrayList<Any>)

            }

        }
        else
        {


            for (item in (result as VehicleModel.Data).data)
            {
                if (item.type_id==intent.extras.getString("type_id"))
                adapter.addAll(item.subtypes as ArrayList<Any>)

            }

        }
    }
    lateinit var adapter:UniverSalBindAdapter

    lateinit var listActivityLayoutBinding: pick.com.app.databinding.ListActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listActivityLayoutBinding=DataBindingUtil.setContentView(this,R.layout.list_activity_layout)


        val toolbarCustom =
            ToolbarCustom(ToolbarCustom.lefticon, resources.getString(R.string.select_a_country), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        left_Icon.onClick { onBackPressed() }

        val title = when (intent.extras.getString("url")) {

            Urls.GET_MODELS -> resources.getString(R.string.select_model)
            Urls.GET_YEARS -> resources.getString(R.string.select_year)
            Urls.GET_VEHICLE_TYPES -> {

                if (intent.extras.getString("type_id") == null) resources.getString(R.string.select_vehicle_types_text) else resources.getString(R.string.select_vehiclesub_types_text)
            }

            Urls.GET_TRANSMISSION -> resources.getString(R.string.select_transmission)
            Urls.GET_FUEL_TYPE -> resources.getString(R.string.select_fuel_type)
            Urls.GET_SEATERS -> resources.getString(R.string.select_seat)
else ->""


        }

        toolbarCustom.title=title


        listActivityLayoutBinding.toolbar=toolbarCustom

        adapter=UniverSalBindAdapter(R.layout.custom_list,this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter

        var jsonObject= JSONObject()

        jsonObject.put("name","dsfdsf")

        ApiServices<VehicleModel.Data>().callApiinObject(url=intent.extras.getString("url"),onResponse=this,hashMap =jsonObject,class_=VehicleModel.Data::class.java,showprogressbar=true,activity=this)

    }

}
