package pick.com.app.varient.user.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.vehicle_details.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.custom.GridDividerDecoration
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.pojo.VehicleSearchData
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class VehicleDetailsActivity : BaseActivity(){
    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        var result= result as VehicleSearchData.Data
    //    var amount=result.vehicles.payable_amount

        if (result.status==1){
            result.data.drop_location=vehicleSearchData.drop_location
           /* vehicleDetailsBinding.user=result.data
            vehicleDetailsBinding.invalidateAll()
*/
            var adapter=UniverSalBindAdapter(R.layout.custom_week_view_user)
            recylviewBulk.adapter=adapter
            adapter.addAll(result.data.vehicle_availabality as ArrayList<Any>)
            recylviewBulk.addItemDecoration(GridDividerDecoration(recylviewBulk.context))

            var adapterpolocy=UniverSalBindAdapter(R.layout.custom_user_vehicle_details_policies)
            plocyREcycleview.adapter=adapterpolocy
            plocyREcycleview.addItemDecoration(GridDividerDecoration(plocyREcycleview.context))

            adapterpolocy.addAll(result.data.policies as ArrayList<Any>)
            loadImage(imageCAr,result.data.upload_url+"vehicle_images/"+result.data.vehicle_image)
        }else{
            onError(result.message)
        }

    }


    fun manageVechile(view: View){

        manageFavVehicle(vehicleDetailsBinding.user!!.vehicle_id,object: onResponse {
            override fun <T : Any?> onSucess(result: T, methodtype: String?) {

                vehicleDetailsBinding.user!!.is_favourite=if (vehicleDetailsBinding.user!!.is_favourite==1) 0 else 1
                vehicleDetailsBinding.user!!.isfav=(vehicleDetailsBinding.user!!.is_favourite==1)
                vehicleDetailsBinding.invalidateAll()
            }

            override fun onError(error: String?) {

            }


        })
    }


    fun changeAddress(view: View){

    var   arralist=ArrayList<String>()

        for (iten in vehicleSearchData.drop_location){
            arralist.add(iten.drop_location_address!!)
        }

        MaterialDialog(this).show {
            listItems(items = arralist) { dialog, index, text ->
                // Invoked when the user taps an item

                vehicleDetailsBinding.user!!.drop_location_address=text
                vehicleDetailsBinding.invalidateAll()
            }
        }
    }

 lateinit   var   searchodel:SearchBookingModel
    lateinit var vehicleDetailsBinding:pick.com.app.databinding.VehicleDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vehicleDetailsBinding=DataBindingUtil.setContentView(this,R.layout.vehicle_details)
        vehicleDetailsBinding.activity = this

        val toolbarCustom =
            ToolbarCustom(ToolbarCustom.lefticon, resources.getString(R.string.vehicle_details), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        left_Icon.onClick { onBackPressed() }
        vehicleDetailsBinding.toolbar=toolbarCustom



         vehicleSearchData=intent.extras.get("model")  as VehicleSearchData.Data.Vehicles

        vehicleSearchData.isfav=(vehicleSearchData.is_favourite==1)
        vehicleDetailsBinding.user = vehicleSearchData

        searchodel=intent.extras.get("searchodel")  as SearchBookingModel
        vehicleDetailsBinding.searchodel=searchodel

        if (!searchodel.isdiferentcity)
        vehicleDetailsBinding.user!!.drop_location_address=vehicleSearchData.pick_adddress!!

        view_insurance.setOnClickListener {
            var url = vehicleSearchData.upload_url+"insurance_policies/"+vehicleSearchData.insurance_policy
            var i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            BaseActivity.activity.startActivity(i)
        }

        getVechileDEtails()
    }
lateinit var vehicleSearchData:VehicleSearchData.Data.Vehicles
   // lateinit var vehicleSearchDat:SearchBookingModel
    fun getVechileDEtails(){


        var hashmap=HashMap<String,Any>()
        hashmap.put("vehicle_id",vehicleSearchData.vehicle_id!!)


        ApiServices<VehicleSearchData.Data>().callApi(Urls.GET_VEHICLE_DETAILS,this,hashmap,VehicleSearchData.Data::class.java,true,this)
    }
}
