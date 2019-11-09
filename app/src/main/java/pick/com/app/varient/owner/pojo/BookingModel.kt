package pick.com.app.varient.owner.pojo

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.k4kotlin.core.*
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.base.BaseFragment
import pick.com.app.base.CommonActivity
import pick.com.app.interfaces.onItemClick
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.owner.activity.ActivityBooking
import pick.com.app.varient.user.ui.fragment.FavoriteVehicleFragment
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BookingModel : Serializable {


    fun showMessage(view: View, mesage: String) {

        Snackbar.make(view, mesage, Snackbar.LENGTH_SHORT).show()

    }

    fun AddAsseriesVilidation(view: View, arrayList: ArrayList<Data.BeforePickupDetail.AsseriesData>): Boolean {

        for (item in arrayList) {
            when {
                item.name.trim() == "" -> {


                    showMessage(view, view.context.getString(R.string.please_enter_accessories_name))

                    return false
                }
                item.qty.trim() == "" -> {
                    showMessage(view, view.context.getString(R.string.please_enter_accessories_qty))


                    return false
                }
                item.price.trim() == "" -> {
                    showMessage(view, view.context.getString(R.string.please_enter_accessories_price))


                    return false
                }
            }
        }

        return true
    }


    fun manageAsseriesVilidation(view: View, arrayList: ArrayList<Data.BeforePickupDetail.AsseriesData>): Boolean {

        for (item in arrayList) {
            when {

                item.qty == null || item.qty.trim() =="" -> {
                    showMessage(view, view.context.getString(R.string.please_enter_accessories_qty))


                    return false
                }

            }
        }

        return true
    }


    fun checkUpBeforeValidation(view: View, data: Data): Boolean {

        when {

            data.fuel_amount == "" -> {

                showMessage(view, view.context.getString(R.string.please_enter_fule_amt))
                return false
            }

            data.kilometer == "" -> {

                showMessage(view, view.context.getString(R.string.please_enter_km))

                return false
            }


        }
        return true
    }


    fun checkUpAfterValidation(view: View, data: Data): Boolean {

        when {

            data.afterfuel_amount == "" -> {

                showMessage(view, view.context.getString(R.string.please_enter_after_check_up_fuel_amt))
                return false
            }

            data.afterkilometer == "" -> {

                showMessage(view, view.context.getString(R.string.please_enter_after_check_up_km))

                return false
            }


        }
        return true
    }

    var status = 0
    private var message: String? = null
    var description = ""
    private var data: List<Data>? = null


    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): List<Data>? {
        return data
    }

    fun setData(data: List<Data>) {
        this.data = data
    }


    class Data : BaseObservable(), Serializable {
        /**
         * booking_id : 11
         * vehicle_image : 1551263023XXimg-20190227-wa0007.jpg
         * vehicle_model : null
         * user_data : {"name":"naseem akhtar","profile_pic":"1551336186XXimage-1994.jpg","contact_number":"9001638910"}
         * from_date_time : 0
         * to_date_time : 0
         * pick_address : A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India
         * drop_address : Kjgtujjj
         * is_door_delivery : 0
         * is_different_city_drop : 2
         * one_day_free_kilometer : 0
         * payable_amount : 22
         * per_kilometer_charge : 0
         */


        var seater: String? = null
        var is_favourite=1
        var isfav=false
        var vehicle_for=0

        // var seconds: String? = null
        var vehicle_type: String? = null
        var vehicle_subtype: String? = null
        var transmission: String? = null
        var fuel_type: String? = null
        /*  var days: String? = null
          var hours: String? = null
          var minutes: String? = null*/


        var isdetailsshow = false


        fun isExtended(data: Data): Boolean {

            return (data.is_extended == 0)

        }

        fun getFullDAte(miliSec: String):String{

            val simple = SimpleDateFormat("dd MMM HH:mm a")


            val result = Date(miliSec.toLong()*1000L)

            var newate = simple.format(result)
            return  newate


        }

        fun getFormatedDAte(miliSec: String, type: String): String {
            val simple = SimpleDateFormat("dd MMM hh:mm a")
            if (miliSec == "0") return ""

            val result = Date(miliSec.toLong()*1000L)

            var newate = simple.format(result)
            return when (type) {
                "DAY" -> newate.split(" ")[0]
                "Month" -> newate.split(" ")[1]

                else -> newate.split(" ")[2] +" "+ newate.split(" ")[3]
            }

        }


        var show_accept_reject = false
        var itemclick: onItemClick? = null

        var upload_url = ""


        fun getshow_accept_reject(data: Data): Boolean {

            return data.booking_status == 0
        }


        @Bindable
        var show_cancel_booking = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.show_cancel_booking)

                }
            }

        @Bindable
        var show_license_details = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.show_license_details)

                }
            }

        @Bindable
        var days: String = ""
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.days)

                }
            }


        @Bindable
        var hours: String = ""
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.hours)

                }
            }


        @Bindable
        var minutes: String = ""
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.minutes)

                }
            }


        @Bindable
        var seconds: String = ""
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.seconds)

                }
            }


        fun getStatusById(status_id: Int): String {
            when (status_id) {
                1 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.confirm)
                }
                2 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.checkup_before_requested)
                }
                3 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.pick_up)
                }
                4 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.trip_completion_request_by_user)
                }
                5 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.check_after_request_submitted)
                }
                6 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.dropped)
                }
                7 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.cancelled_by_user)
                }
                8 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.cancelled_by_vehicle_owner)
                }
                11 -> {
                    return ActivityBooking.activity.getString(R.string.extended_booking)
                }
            }
            return ""
        }

        fun getButtonText(status_id: Int): String {
            when (status_id) {
                1 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.submit)
                }
                4 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.submit)
                }
                6 -> {
                    return ActivityBooking.activity.getString(pick.com.app.R.string.add_rating)
                }
            }
            return ""
        }


        fun getStatusByIdUser(status_id: Int): String {
            when (status_id) {
                9 -> {
                    return ActivityBooking.activity.getString(R.string.booking_not_as_payment_not_received)
                }
                0 -> {
                    return ActivityBooking.activity.getString(R.string.booked)
                }
                1 -> {
                    return ActivityBooking.activity.getString(R.string.confirm_by_vehicle_owner)
                }
                2 -> {
                    return ActivityBooking.activity.getString(R.string.checkup_before_requested)
                }
                3 -> {
                    return ActivityBooking.activity.getString(R.string.pickedup)
                }
                4 -> {
                    return ActivityBooking.activity.getString(R.string.trip_completion_request)
                }
                5 -> {
                    return ActivityBooking.activity.getString(R.string.check_up_after_request)
                }
                6 -> {
                    return ActivityBooking.activity.getString(R.string.dropped)
                }
                7 -> {
                    return ActivityBooking.activity.getString(R.string.cancelled_by_user)
                }
                8 -> {
                    return ActivityBooking.activity.getString(R.string.cancelled_by_vehicle_owner)
                }
                10 -> {
                    return ActivityBooking.activity.getString(R.string.rejected_by_vehicle_owner)
                }
                11 -> {
                    return ActivityBooking.activity.getString(R.string.extended_booking)
                }
            }
            return ""
        }


        fun isDifferentCity(different:String):Boolean{

            return  different=="1"

        }
        fun getButtonTextUser(status_id: Int): String {
            when (status_id) {
                9 -> {
                    return ActivityBooking.activity.getString(R.string.pay_now)
                }
                1 -> {
                    return ActivityBooking.activity.getString(R.string.download_pdf)
                }
                2 -> {
                    return ActivityBooking.activity.getString(R.string.approved_and_pick_up)
                }
                3 -> {
                    return ActivityBooking.activity.getString(R.string.extended_booking)
                }
                5 -> {
                    return ActivityBooking.activity.getString(R.string.dropped)
                }
                6 -> {
                    return ActivityBooking.activity.getString(R.string.add_rating)
                }
            }
            return ""
        }

        fun getCancelButtonText(status_id: Int): String {
            when (status_id) {
                0 -> {
                    return ActivityBooking.activity.getString(R.string.cancel_booking)
                }
                1 -> {
                    return ActivityBooking.activity.getString(R.string.cancel_booking)
                }
                3 -> {
                    return ActivityBooking.activity.getString(R.string.complete_job)
                }
                5 -> {
                    return ActivityBooking.activity.getString(R.string.report)
                }
                6 -> {
                    return ActivityBooking.activity.getString(R.string.rebook)
                }

                11 -> {
                    return ActivityBooking.activity.getString(R.string.complete_job)
                }

            }
            return ""
        }


        @Bindable
        var show_submit_button = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.show_submit_button)

                }
            }
        @Bindable
        var show_share_button = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.show_share_button)

                }
            }

        companion object {

            @JvmStatic
            @BindingAdapter("manageExtended")
            fun manageExtended(view: View, model: BookingModel.Data) {

                view.visibility=if (model.extended_date_time=="0") View.GONE else View.VISIBLE

            }




                @JvmStatic
            @BindingAdapter("setRecyclerView")
            fun setRecyclerView(recyclerView: RecyclerView, model: BookingModel.Data) {


                recyclerView.adapter = UniverSalBindAdapter(pick.com.app.R.layout.custom_image_booking_details).apply {

                    add(model)
                    add(model)
                    add(model)
                    add(model)
                    add(model)
                    add(model)
                    add(model)
                }

            }

            @JvmStatic
            @BindingAdapter("setFav")
            fun setFav(view: ImageView, model:Data) {



                if (model.is_favourite == 1)
                    view.setImageResource(R.drawable.profile_fav_icon)
                else
                    view.setImageResource(R.drawable.fav_icon)


                view.onClick {

                    (BaseFragment.fragment as FavoriteVehicleFragment).manageVechile(model,this)

                }

            }


            @JvmStatic
            @BindingAdapter("bookNow")
            fun bookNow(v: View, model: BookingModel.Data) {
                v.setOnClickListener {
                    var bundel= Bundle()
                    bundel.putString("vehicle_id",model.vehicle_id)
                    Redirection().goToHome(false,  (BaseFragment.fragment.activity)!!, bundel)


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
                            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(pick.com.app.R.drawable.placeholder).error(
                                pick.com.app.R.drawable.placeholder


                            )
                        )
                        .load(url)
                        .into(profile_image)


            }


            @JvmStatic
            @BindingAdapter("acceptReject", "status")
            fun acceptReject(view: View, data: Data, status: String) {

                view.onClick {
                    // super.getMessage()
                    //BookingModel().message

                    data.itemclick!!.getItemClick(data, status)

                }
            }

            @JvmStatic
            @BindingAdapter("setMAxLineEllipse")
            fun setMAxLineEllipse(view: TextView, data: Data) {

                if (!data.isdetailsshow) {
                    view.maxLines = 3
                    view.ellipsize = TextUtils.TruncateAt.END
                }
            }


            @JvmStatic
            @BindingAdapter("manageLayout", "layoutname")
            fun manageLayout(recyclerView: LinearLayout, model: BookingModel.Data, layoutname: String) {

                Log.e("", "")


                when {
                    layoutname.equals("owner_new_booking_request") && model.booking_status == 0 -> {
                        recyclerView.show()
                    }
                    layoutname.equals("check_up_before_fill_form") && model.booking_status == 1 -> {
                        model.show_cancel_booking = true
                        model.show_license_details = true
                        recyclerView.show()

                    }
                    layoutname.equals("check_up_before_view_user") && model.booking_status == 4 || layoutname.equals("check_up_before_view_user") && model.booking_status == 2 ||
                            layoutname.equals("check_up_before_view_user") && model.booking_status == 5 || layoutname.equals("check_up_before_view_user") && model.booking_status == 11-> {
                        model.show_license_details = true
                        recyclerView.show()

                    }
                    layoutname.equals("check_up_before_view_user") && model.booking_status == 3 || layoutname.equals("check_up_before_view_user") && model.booking_status == 6 -> {
                        model.show_license_details = true
                        recyclerView.show()
                    }
                    layoutname.equals("check_up_after_fill_form") && model.booking_status == 4 -> {
                        model.show_license_details = true
                        recyclerView.show()
                    }

                    layoutname.equals("check_up_after_view") && model.booking_status == 5 || layoutname.equals("check_up_after_view") && model.booking_status == 6 -> {
                        model.show_license_details = true
                        recyclerView.show()
                    }

                    layoutname.equals("amount_layout_booking_details") && model.booking_status == 6 || layoutname.equals(
                        "amount_layout_booking_details"
                    ) && model.booking_status == 5 -> {
                        model.show_license_details = true
                        recyclerView.show()
                    }

                    layoutname.equals("cancel_submit_btn") && model.booking_status == 1 || layoutname.equals("cancel_submit_btn") && model.booking_status == 4
                            || layoutname.equals("cancel_submit_btn") && model.booking_status == 6 -> {
                        model.show_license_details = true
                        recyclerView.show()
                    }

                    else -> {
                        recyclerView.hide()
                    }
                }


            }


            @BindingAdapter("setTime")
            @JvmStatic
            fun setTime(view: View, booingModel: Data) {

                if (booingModel.booking_status!=3) {
                    view.hide()
                    return
                }

                if (booingModel.seconds == "") {
                    val minutes = booingModel.to_date_time
                    val milliseconds = minutes.toLong()-booingModel.timer
                    var countDownTimer = object : CountDownTimer(milliseconds!!.toLong()*1000L, 500) {

                        override fun onTick(millisUntilFinished: Long) {

                            var time = String.format(
                                "%02d:%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toDays(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        millisUntilFinished
                                    )
                                )
                            )




                            var different =millisUntilFinished


                            System.out.println("different : " + different);

                            var secondsInMilli = 1000;
                            var minutesInMilli = secondsInMilli * 60;
                            var hoursInMilli = minutesInMilli * 60;
                            var daysInMilli = hoursInMilli * 24;

                            var elapsedDays = different / daysInMilli;
                            different = different % daysInMilli;

                            var elapsedHours = different / hoursInMilli;
                            different = different % hoursInMilli;

                            var elapsedMinutes = different / minutesInMilli;
                            different = different % minutesInMilli;

                            var elapsedSeconds = different / secondsInMilli;

                            System.out.printf(
                                "%02d  days, %02d hours, %02d minutes, %02d seconds%n",
                                elapsedDays,
                                elapsedHours, elapsedMinutes, elapsedSeconds);


                            var tisssme = String.format(
                                "%02d:%02d:%02d:%02d",
                                elapsedDays,
                                elapsedHours, elapsedMinutes, elapsedSeconds
                            )


Log.e("time",tisssme)


                            booingModel.days =tisssme.split(":")[0]
                            booingModel.hours = tisssme.split(":")[1]
                            booingModel.minutes = tisssme.split(":")[2]
                            booingModel.seconds = tisssme.split(":")[3]


                        }

                        override fun onFinish() {
                            //view.hide()
                        }
                    }

                    countDownTimer.start()
                }


            }

            @JvmStatic
            @BindingAdapter("manageLayoutUser", "layoutname")
            fun manageLayoutUser(recyclerView: LinearLayout, model: BookingModel.Data, layoutname: String) {

                Log.e("", "")


                when {
                    layoutname.equals("checkup_before_view_user") && model.booking_status == 2 || layoutname.equals("checkup_before_view_user") && model.booking_status == 3
                            || layoutname.equals("checkup_before_view_user") && model.booking_status == 4
                            || layoutname.equals("checkup_before_view_user") && model.booking_status == 5
                            || layoutname.equals("checkup_before_view_user") && model.booking_status == 6 || layoutname.equals("checkup_before_view_user") && model.booking_status == 11 -> {
                        recyclerView.show()

                    }
                    layoutname.equals("checkup_after_view_user") && model.booking_status == 5 || layoutname.equals("checkup_after_view_user") && model.booking_status == 6 -> {
                        recyclerView.show()

                    }
                    layoutname.equals("cancel_submit_btn") && model.booking_status == 9 || layoutname.equals("cancel_submit_btn") && model.booking_status == 0
                            || layoutname.equals("cancel_submit_btn") && model.booking_status == 1 || layoutname.equals(
                        "cancel_submit_btn"
                    ) && model.booking_status == 2
                            || layoutname.equals("cancel_submit_btn") && model.booking_status == 3 || layoutname.equals(
                        "cancel_submit_btn"
                    ) && model.booking_status == 5
                            || layoutname.equals("cancel_submit_btn") && model.booking_status == 6 || layoutname.equals(
                        "cancel_submit_btn"
                    ) && model.booking_status == 4 || layoutname.equals("cancel_submit_btn") && model.booking_status == 11 -> {
                        if (model.booking_status == 0 || model.booking_status == 11) {
                            model.show_submit_button = false
                            model.show_cancel_booking = true
                        } else if (model.booking_status == 1 || model.booking_status == 3 || model.booking_status == 5) {
                            model.show_submit_button = true
                            model.show_cancel_booking = true
                        } else if (model.booking_status == 6) {
                            model.show_submit_button = true
                            model.show_cancel_booking = true
                            model.show_share_button = true
                        } else if (model.booking_status == 4) {
                            model.show_submit_button = false
                            model.show_cancel_booking = false
                        } else {
                            model.show_submit_button = true
                        }
                        recyclerView.show()
                    }
                    else -> {
                        recyclerView.hide()
                    }
                }
            }


        }

        var fav_id : String? = null
        var user_id : String? = null
        var vehicle_id : String? = null
        var vehicle_name : String? = null
        var vehicle_type_name : String? = null

        var vehicle_subtype_name: String? = null
        var vehicle_transmission: String? = null
        var vehicle_transmission_title: String? = null
        var seater_id: String? = null



        var booking_id: String? = null
        var fuel_amount = ""
        var afterfuel_amount = ""
        var aftervechileDemageCharge = ""

        var kilometer = ""
        var afterkilometer = ""

        var vehicle_image: String? = null
        var vehicle_model = ""
        var vehicle_year = ""
        var user_data: UserData? = null
        var from_date_time = "0"
        var to_date_time = "0"
        var pick_address: String? = null
        var drop_address: String? = null
        var is_door_delivery: String? = null
        var is_different_city_drop: String? = null
        var one_day_free_kilometer: String? = null
        var payable_amount = ""
        var per_kilometer_charge: String? = null


        var model_id: String? = null

        var extended_date_time="0"
        var is_extended = 0
        var free_kilometer: String? = null

        var pickup_address: String? = null

        var total_amount: String? = null
        var refundable_amount: String? = null
        var booking_status = 0
        var timer: Int = 0
        var extra_kilometer_charges: String? = null
        var fuel_charges: String? = null
        var car_accessories_charges: String? = null
        var vehicle_damage_charge: String? = null
        var cancellation_charge: String? = null
        var late_return_charge: String? = null
        var extended_charges: String? = null
        var booking_detail_pdf: String? = null
        var extra_payable_amount: String? = null
        var before_pickup_detail = BeforePickupDetail()
        var after_pickup_detail: AfterPickupDetail? = null


        class BeforePickupDetail : Serializable {
            /**
             * fuel_amount : 50
             * kilometer : 120
             * vehicle_image : 1551263023XXimg-20190227-wa0007.jpg
             * accessories_details : []
             */


            var fuel_amount: String? = null
            var booking_id: String? = null
            var kilometer: String? = null
            var vehicle_image: String? = null
            var accessories = ArrayList<AsseriesData>()
            var vehicle_images = ArrayList<VehicleImages>()

            class AsseriesData(var addapter: UniverSalBindAdapter) : BaseObservable(), Serializable {

                var position = 0
                var quantity = ""
                var value = ""
                var isavail = false

                @Bindable
                var name = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.name)

                        }
                    }


                @Bindable
                var qty = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.qty)

                        }
                    }


                @Bindable
                var price = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.price)

                        }
                    }


                companion object {


                    @JvmStatic
                    @BindingAdapter("addAccessories")
                    fun addAccessories(view: View, model: AsseriesData) {


                        if (model.addapter.arrraylist.size - 1 == model.position) {
                            view.show()
                        } else {
                            view.invisible()
                        }

                        view.setOnClickListener {

                            CommonActivity.activity.hideKeyboard()
                            if (model.addapter.arrraylist.size < 20)
                                model.addapter.addWithNotify(AsseriesData(model.addapter))
                            else
                                view.context.toast(it.context.getString(R.string.drop_location_max_twenty))

                        }


                    }

                    @JvmStatic
                    @BindingAdapter("removeAccessories")
                    fun removeAccessories(view: View, model: AsseriesData) {


                        if (model.position == model.addapter.arrraylist.size - 1) {
                            view.show()
                        } else {
                            view.show()
                        }

                        if (model.addapter.arrraylist.size == 1) {
                            view.hide()
                        }

                        view.onClick {
                            CommonActivity.activity.hideKeyboard()
                            model.addapter.removeItemWithnotify(model)
                        }
                    }

                }
            }


            class VehicleImages : Serializable {

                var image_id = ""
                var user_id = ""
                var type = ""
                var car_image = ""
                var booking_id = ""
            }

        }

        class AfterPickupDetail : Serializable {
            /**
             * fuel_amount : 50
             * kilometer : 120
             * vehicle_image : 1551263023XXimg-20190227-wa0007.jpg
             * accessories_details : []
             */

            var fuel_amount: String? = null
            var kilometer: String? = null
            var vehicle_image: String? = null
            var accessories = ArrayList<AsseriesData>()
            var vehicle_images = ArrayList<VehicleImages>()

            class AsseriesData(var addapter: UniverSalBindAdapter) : BaseObservable(), Serializable {

                var position = 0
                var quantity = ""
                var value = ""
                var isavail = false

                @Bindable
                var name = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.name)

                        }
                    }


                @Bindable
                var qty = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.qty)

                        }
                    }


                @Bindable
                var price = ""
                    set(value) {
                        if (field != value) {

                            field = value
                            notifyPropertyChanged(BR.price)

                        }
                    }


                companion object {


                    @JvmStatic
                    @BindingAdapter("addAccessories")
                    fun addAccessories(view: View, model: AsseriesData) {


                        if (model.addapter.arrraylist.size - 1 == model.position) {
                            view.show()
                        } else {
                            view.invisible()
                        }

                        view.setOnClickListener {

                            CommonActivity.activity.hideKeyboard()
                            if (model.addapter.arrraylist.size < 20)
                                model.addapter.addWithNotify(AsseriesData(model.addapter))
                            else
                                view.context.toast(it.context.getString(R.string.drop_location_max_twenty))

                        }


                    }

                    @JvmStatic
                    @BindingAdapter("removeAccessories")
                    fun removeAccessories(view: View, model: AsseriesData) {


                        if (model.position == model.addapter.arrraylist.size - 1) {
                            view.show()
                        } else {
                            view.show()
                        }

                        if (model.addapter.arrraylist.size == 1) {
                            view.hide()
                        }

                        view.onClick {
                            CommonActivity.activity.hideKeyboard()
                            model.addapter.removeItemWithnotify(model)
                        }
                    }

                }
            }


            class VehicleImages : Serializable {

                var image_id = ""
                var user_id = ""
                var type = ""
                var car_image = ""
                var booking_id = ""
            }
            // var accessories_details: List<*>? = null
        }

        class UserData : Serializable {

            /*  "name": "naseem akhtar",
                "contact_number": "9001638910",
                "profile_pic": "1551336186XXimage-1994.jpg",
                "license_image": "1551440054XXmagazine-unlock-05-2.3.1271-_735ac02f00fd8e10c39337b2d52babee.jpg",
                "dl_number": "bgxdf"*/
            var name: String? = null
            var profile_pic: String? = null
            var contact_number: String? = null
            var dl_number: String? = null
            var license_image: String? = null
        }
    }

}
