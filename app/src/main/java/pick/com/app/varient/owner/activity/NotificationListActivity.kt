package pick.com.app.varient.owner.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.show

import kotlinx.android.synthetic.main.notification_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class NotificationListActivity : BaseActivity() ,UniverSalBindAdapter.ItemAdapterListener{
    override fun onItemSelected(model: Any) {

    }


    lateinit var manageVehicleListingLayoutBinding: pick.com.app.databinding.ManageVehicleListingLayoutBinding

    companion object {

        lateinit var activity: NotificationListActivity
    }




    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        var result = result as VehicleModel




            when (methodtype) {

                Urls.VEHICLE_LISTING -> {

                    if (result.status == 1) {
                        rv_notification.show()
                        no_data_available.hide()
                        adpater = UniverSalBindAdapter(R.layout.custom_my_vehicle,this)
                        rv_notification.adapter = adpater
                        adpater.addAll(result.data as ArrayList<Any>)

                    }else {
                        no_data_available.text = result.description
                        no_data_available.show()
                        rv_notification.hide()

                        //showMessage (message=result.description)
                    }

                }


            }

        }



    lateinit var adpater: UniverSalBindAdapter


    override fun onResume() {
        super.onResume()
        ApiServices<VehicleModel>().callApi(
            Urls.VEHICLE_LISTING,
            this,
            VehicleModel().getVechileLsiting(activity),
            VehicleModel::class.java,
            true,
            this
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_activity)
        activity = this
        manageVehicleListingLayoutBinding = DataBindingUtil.setContentView(this, R.layout.manage_vehicle_listing_layout)
        val toolbarCustom =
            ToolbarCustom(
                ToolbarCustom.lefticon,
                getString(R.string.notification),
                ToolbarCustom.NoIcon,
                ToolbarCustom.NoIcon
            )
        left_Icon.onClick { onBackPressed() }

        lasticon.onClick { Redirection().goToAddVehicle(activity = this@NotificationListActivity) }
        manageVehicleListingLayoutBinding.toolbar = toolbarCustom

        rv_notification.layoutManager = LinearLayoutManager(this)


    }
}
