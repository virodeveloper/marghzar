package pick.com.app.varient.owner.pojo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.k4kotlin.core.invisible
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.show
import com.obsez.android.lib.filechooser.ChooserDialog
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.uitility.custom.GridDividerDecoration
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.activity.AddNewVehicleActivity
import pick.com.app.varient.owner.activity.ManageVehicleActivity
import pick.com.app.webservices.Urls
import java.io.File
import java.io.Serializable


class VehicleModel {

    /*{"status":"1","message":"Success","description":"Vehicle_successfully Added."}*/

    var status = 0
    var message = ""
    var description = ""
    fun getVechileLsiting(view: Activity): HashMap<String, Any> {

        val hashMap = HashMap<String, Any>()
        hashMap["user_id"] = SessionManager.getLoginModel(view).data.user_id
        return hashMap
    }


    var data = ArrayList<Data>()
    var dataclass = Data()

    var upload_url = ""

    class Data : BaseObservable(), Serializable {

        companion object {

            @JvmStatic
            @BindingAdapter("gotoEdit")
            fun gotoEdit(view: View, model: Data) {

                view.onClick {

                    var bundle = Bundle()

                    bundle.putSerializable("model", model)
                    Redirection().goToAddVehicle(activity = view.context, bundle = bundle)

                }

            }


            @JvmStatic
            @BindingAdapter("viewPolocy")
            fun viewPolocy(view: View, model: Data) {

                view.onClick {

                    var url = model.upload_url+"insurance_policies/"+model.insurance_policy;
                    var i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url);
                    BaseActivity.activity.startActivity(i)
                }

            }


            @JvmStatic
            @BindingAdapter("gotoDelete")
            fun gotoDelete(view: View, model: Data) {

                view.onClick {

                    ManageVehicleActivity.activity.deleteVEchile(model.vehicle_id)
                }

            }


            @JvmStatic
            @BindingAdapter("onchangeSwithc")
            fun onchangeSwithc(view: SwitchCompat, model: Data) {

                if (model.is_vehicle_available == 1) {

                    model.is_available = true
                }
                view.isChecked = model.is_available

                view.onClick {


                    ManageVehicleActivity.activity.setVehicleAvalibility(model.is_available, model, view)


                }

            }


            @JvmStatic
            @BindingAdapter("loadImageData")
            fun loadImageData(profile_image: ImageView, url: String) {
                Log.e("url", url)
                if (url != "")
                    Glide.with(profile_image.context)
                        .asBitmap()
                        .apply(
                            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.placeholder).error(
                                R.drawable.placeholder


                            )
                        )
                        .load(url)
                        .into(profile_image)


            }


            @JvmStatic
            @BindingAdapter("setRecyclerView")
            fun setRecyclerView(recyclerView: RecyclerView, model: Data) {


                recyclerView.addItemDecoration(GridDividerDecoration(recyclerView.context))
                recyclerView.adapter = UniverSalBindAdapter(R.layout.custom_week_layout).apply {

                    addAll(model.vehicle_availabality as ArrayList<Any>)

                }

            }
        }


        var position = 0
        var isOwner_Company = false

        var vehicle_brand = ""
        var vehicle_type = ""
        var vehicle_subtype = ""
        var subtype_id = ""
        var upload_url = ""


        var vehicle_image = ""


        var vehicle_imagefile = File("")


        var seater = ""
        var is_vehicle_available = 1


        @Bindable
        var is_available = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.is_available)

                }
            }


        var years = ""
        var vehicle_year = ""
        var fuel_type = ""
        var model_id = ""
        var model_name = ""
        var vehicletype = ""
        var type_id = ""

        var vehicle_charges_one_day = ""

        var one_day_free_kilometer = ""
        var per_kilometer_charge = ""
        var user_id = ""
        var seater_id = ""
        var vehicle_year_id = ""
        var fuel_type_id = ""
        var vehicle_type_id = ""
        var vehicle_name_id = ""
        var vehicle_name = ""
        var vehicle_transmission = ""
        var brand_id = ""
        var vehicle_id = ""
        var vehicle_subtype_name = ""
        var vehicle_type_name = ""
        var vehicle_transmission_title = ""


        fun getvehicle_for(model: VehicleModel.Data): Int {

            return if (model.dayWise) 0
            else 1


        }

        fun setdayWise(model: VehicleModel.Data): Boolean {

            return model.vehicle_for == 0


        }


        var data = ArrayList<Seaters>()


        class Seaters : Serializable {

            companion object {
                @JvmStatic
                @BindingAdapter("setRecyclerView")
                fun setRecyclerView(recyclerView: RecyclerView, model: Seaters) {


                    recyclerView.adapter = UniverSalBindAdapter(R.layout.row_layout_subfilter).apply {

                        addAll(model.subtypes as ArrayList<Any>)

                    }

                }
            }

            override fun equals(obj: Any?): Boolean {

                try {
                    val licenceDetail = obj as Seaters?
                    return avail_id == licenceDetail!!.avail_id
                } catch (e: Exception) {
                    return false
                }

            }

            var id = ""
            var brand_id = ""
            var position = 0
            var seater = ""
            var fuel_type_id = ""
            var type_id = ""
            var year = ""
            var fuel_type = ""
            var type_title = ""
            var transmission_id = ""
            var transmission_title = ""
            var subtypes = ArrayList<SubType>()
            var avail_title = ""
            var avail_id = ""
            var model_name = ""
            var model_id = ""


            class SubType : Serializable {
                var subtype_id = ""
                var subtype_title = ""
                var isitemSelected = false
                override fun toString(): String {
                    return subtype_title
                }
            }


            override fun toString(): String {
                return seater + fuel_type + type_title + transmission_title + year + avail_title + model_name
            }


        }


        var vehicle_availabality = ArrayList<BulkAvailability>()

        class BulkAvailability : Serializable, BaseObservable() {
            var position = 0
            var noofdays = 0
            fun callBulkAvalibility(bulkAvailability: BulkAvailability) {

                AddNewVehicleActivity.activity.gotoListACtivity(Urls.BULK_AVAILABLE_LIST, bulkAvailability)
            }


            fun getPerdayPrice(model: BulkAvailability): Int {

                return (model.avail_price!!.toDouble() / model.noofdays).toInt()


            }

            fun getPriceToDouble(price: String): String {

                return ((price.toDouble()).toInt()).toString()
            }


            companion object {

                var bulksize = 1
                lateinit var univerSalBindAdapter: UniverSalBindAdapter
                @JvmStatic
                @BindingAdapter("addVehicle")
                fun addVehicle(view: ImageView, model: BulkAvailability) {


                    if (univerSalBindAdapter.arrraylist.size - 1 == model.position && bulksize != univerSalBindAdapter.arrraylist.size) {
                        view.show()
                    } else {
                        view.invisible()
                    }
                    view.onClick {

                        univerSalBindAdapter.addWithNotify(BulkAvailability())
                    }

                }

                @JvmStatic
                @BindingAdapter("removeVehicle")
                fun removeVehicle(view: ImageView, model: BulkAvailability) {


                    if (model.position == univerSalBindAdapter.arrraylist.size - 1) {
                        view.show()
                    } else {
                        view.show()
                    }

                    if (univerSalBindAdapter.arrraylist.size == 1) {
                        view.invisible()
                    }

                    view.onClick { univerSalBindAdapter.removeItemWithnotify(model) }

                }


            }

            fun getHint(postion: Int): String {

                return when (postion) {

                    0, 1, 2 -> {
                        "${postion + 1} Week"
                    }

                    else -> {
                        "${postion - 2} Month"
                    }

                }

            }


            @Bindable
            var avail_id = ""
                set(value) {
                    if (field != value) {

                        field = value
                        notifyPropertyChanged(BR.avail_id)

                    }
                }


            @Bindable
            var avail_name = ""
                set(value) {
                    if (field != value) {

                        field = value
                        notifyPropertyChanged(BR.avail_name)

                    }
                }


            @Bindable
            var avail_price = ""
                set(value) {
                    if (field != value) {

                        field = value
                        notifyPropertyChanged(BR.avail_price)

                    }
                }
        }


        var insurance_policy = ""


        var insurance_policyFile = File("")


        var isLisancePreview: Boolean = false


        fun getInsurancePolocy(view: View, model: VehicleModel) {
            ChooserDialog().with(view.context)
                .withFilter(false, false, "jpg", "jpeg", "png", "pdf", "doc", "docx")
                .withStartFile(Environment.getExternalStorageDirectory().absolutePath)

                .withChosenListener { path, _ ->

                    model.dataclass.insurance_policyFile = File(path)
                }
                .build()
                .show()

        }


        var vehicle_for = 0


        var dayWise = false


    }


    fun gethashMApAddVEhicle(view: View, model: VehicleModel.Data): HashMap<String, Any> {


        return HashMap<String, Any>().apply {


            put("type", 0)
            put("vehicle_brand", model.brand_id)
            put("vehicle_id", model.vehicle_id)
            put("user_id", SessionManager.getLoginModel(view.context).data.user_id)
            put("seater_id", model.seater_id)
            put("vehicle_year", model.vehicle_year)
            put("vehicle_type", model.vehicle_type)
            put("vehicle_subtype", model.vehicle_subtype)
            put("vehicle_model", model.model_id)
            put("vehicle_transmission", model.vehicle_transmission)
            put("fuel_type", model.fuel_type_id)
            put("vehicle_for", model.getvehicle_for(model))
            put("vehicle_charges_one_day", model.vehicle_charges_one_day)
            if (!model.dayWise)
                put("one_day_free_kilometer", model.one_day_free_kilometer)
            if (!model.dayWise)
                put("per_kilometer_charge", model.per_kilometer_charge)
            put("insurance_policy", model.insurance_policyFile)
            put("vehicle_image", model.vehicle_imagefile)


            for (item in model.vehicle_availabality) {

                put("availabality[${item.position}][name]", item.avail_id)
                put("availabality[${item.position}][price]", item.avail_price)
            }

        }


    }

    fun filterVehicle(vehicleModel: VehicleModel): HashMap<String, Any> {


        return HashMap<String, Any>().apply {
            put("location_address", "")
            put("location_latitude", "")
            put("location_longitude", "")
            put("start_date_time", "")
            put("end_date_time", "")
            put("is_different_city", "")
            put("drop_location", "")
            put("is_delivery_door", "")
            put("sort_id", "")
            put("no_if_item", "")


        }
    }


    fun showMessage(view: View, mesage: String) {

        Snackbar.make(view, mesage, Snackbar.LENGTH_SHORT).show()

    }


    fun bulkVilidation(view: View, arrayList: ArrayList<Data.BulkAvailability>): Boolean {

        for (item in arrayList) {
            if (item.avail_name == "") {


                item.avail_name="none"
//                showMessage(view, view.resources.getString(R.string.please_select_Discount_type))
//
//                return false
            } else if (item.avail_price == "") {
//                showMessage(view, view.resources.getString(R.string.please_select_Discount_price))
//
//                return false
                item.avail_price="none"
            }
        }

        return true
    }


    fun validation(view: View, vehicleModel: VehicleModel.Data, type: String): Boolean {

        return when {
            vehicleModel.model_id == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_enter_model_name))
                false
            }
            vehicleModel.seater == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_seat))
                false
            }
            vehicleModel.vehicle_year == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_year))
                false
            }
            vehicleModel.fuel_type_id == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_fuel_type))
                false
            }
            vehicleModel.vehicle_transmission == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_transmission))
                false
            }
            vehicleModel.vehicle_type == "" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_vehicle_type))
                false
            }

            vehicleModel.vehicle_subtype == "" -> {
                showMessage(view, "Please select vehicle sub-type")
                false
            }

            vehicleModel.insurance_policyFile.name == "" && type == "add_vehicle" && SessionManager.getLoginModel(view.context).data.user_type == "oi" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_insurance_policy))
                false
            }
            vehicleModel.vehicle_charges_one_day == "" && vehicleModel.dayWise -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_enter_charges_for_day))
                false
            }
            vehicleModel.one_day_free_kilometer == "" && !vehicleModel.dayWise -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_enter_one_day_free_km))
                false
            }
            vehicleModel.per_kilometer_charge == "" && !vehicleModel.dayWise -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_enter_one_km_charge))
                false
            }
            vehicleModel.vehicle_imagefile.path == "" && type == "add_vehicle" -> {
                showMessage(view, view.resources.getString(pick.com.app.R.string.please_select_vehicle_image))
                false
            }
            else -> true
        }

    }


}
