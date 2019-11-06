package pick.com.app.varient.owner.activity

import android.os.Bundle
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.show
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.manage_vehicle_listing_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.interfaces.OnVehicleAvalibilityChange
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ManageVehicleActivity : BaseActivity(), OnVehicleAvalibilityChange {


    override fun setVehicleAvalibility(avalibility: Boolean, object_: Any, view: SwitchCompat) {

        var object_ = object_ as VehicleModel.Data



        MaterialDialog(this).show {
            title(text = getString(pick.com.app.R.string.app_name))
            negativeButton(text = getString(R.string.No)) {

                view.isChecked = object_.is_available
            }
            cancelable(false)
            positiveButton(text =getString(R.string.yes)) {

                var hashMap =
                    HashMap<String, Any>()

                if (object_.is_vehicle_available == 1) {
                    object_.is_vehicle_available = 2
                    object_.is_available = false
                } else {
                    object_.is_vehicle_available = 1
                    object_.is_available = true
                }


                hashMap.put("user_id", SessionManager.getLoginModel(this@ManageVehicleActivity).data.user_id)
                hashMap.put("vehicle_id", object_.vehicle_id)
                hashMap.put("is_vehicle_available", object_.is_vehicle_available)
                ApiServices<VehicleModel>().callApi(
                    Urls.UPDATE_VEHICLE_AVAILIBITY,
                    this@ManageVehicleActivity,
                    hashMap,
                    VehicleModel::class.java,
                    true,
                    this@ManageVehicleActivity
                )

            }
            message(text = getString(R.string.Doyouwanttoavailabilityofthisvehicle))

        }


    }

    lateinit var manageVehicleListingLayoutBinding: pick.com.app.databinding.ManageVehicleListingLayoutBinding

    companion object {

        lateinit var activity: ManageVehicleActivity
    }


    fun deleteVEchile(vehicle_id: String) {


        MaterialDialog(this).show {
            title(text = getString(pick.com.app.R.string.app_name))
            negativeButton(text = getString(R.string.No))
            cancelable(false)
            positiveButton(text = getString(R.string.Yes)) {

                var hashMap =
                    HashMap<String, Any>()


                hashMap["user_id"] = SessionManager.getLoginModel(this@ManageVehicleActivity).data.user_id
                hashMap["vehicle_id"] = vehicle_id
                ApiServices<VehicleModel>().callApi(
                    Urls.DELETE_VEHICLE,
                    this@ManageVehicleActivity,
                    hashMap,
                    VehicleModel::class.java,
                    true,
                    this@ManageVehicleActivity
                )

            }
            message(text = getString(R.string.Doyouwanttodeletethisvehicle))

        }


    }


    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        var result = result as VehicleModel




            when (methodtype) {

                Urls.VEHICLE_LISTING -> {

                    if (result.status == 1) {
                        recycleview.show()
                        textViewNoDataFound.hide()
                        adpater = UniverSalBindAdapter(R.layout.custom_my_vehicle)
                        recycleview.adapter = adpater
                        adpater.addAll(result.data as ArrayList<Any>)

                    }else {
                        textViewNoDataFound.text = result.description
                        textViewNoDataFound.show()
                        recycleview.hide()

                        //showMessage (message=result.description)
                    }

                }
                Urls.UPDATE_VEHICLE_AVAILIBITY -> {

                    if (result.status == 0) {
                        onError(result.description)
                    }

                }
                Urls.DELETE_VEHICLE -> {

                    if (result.status == 1) {
                        onResume()
                    } else {
                        onError(result.description)
                    }
                }

            }

        }



    lateinit var adpater: UniverSalBindAdapter

    fun gotoEdit() {

        toast("HEllo")
    }

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
        setContentView(R.layout.manage_vehicle_listing_layout)
        activity = this
        manageVehicleListingLayoutBinding = DataBindingUtil.setContentView(this, R.layout.manage_vehicle_listing_layout)
        val toolbarCustom =
            ToolbarCustom(
                ToolbarCustom.lefticon,
                resources.getString(R.string.my_vehicle),
                ToolbarCustom.NoIcon,
                R.drawable.header_plus_icon
            )
        left_Icon.onClick { onBackPressed() }

        lasticon.onClick { Redirection().goToAddVehicle(activity = this@ManageVehicleActivity) }
        manageVehicleListingLayoutBinding.toolbar = toolbarCustom

        recycleview.layoutManager = LinearLayoutManager(this)


    }
}
