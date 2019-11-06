package pick.com.app.varient.user.pojo

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.varient.owner.activity.ActivityBooking
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BookingModel : BaseObservable(), Serializable {




    /**
     * status : 1
     * message : Success
     * data : [{"booking_id":"11","vehicle_image":"1551263023XXimg-20190227-wa0007.jpg","vehicle_model":null,"user_data":{"name":"naseem akhtar","profile_pic":"1551336186XXimage-1994.jpg","contact_number":"9001638910"},"from_date_time":"0","to_date_time":"0","pick_address":"A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India","drop_address":"Kjgtujjj","is_door_delivery":"0","is_different_city_drop":"2","one_day_free_kilometer":"0","payable_amount":22,"per_kilometer_charge":"0"},{"booking_id":"12","vehicle_image":"1551263023XXimg-20190227-wa0007.jpg","vehicle_model":null,"user_data":{"name":"naseem akhtar","profile_pic":"1551336186XXimage-1994.jpg","contact_number":"9001638910"},"from_date_time":"1551420240000","to_date_time":"1551420240000","pick_address":"A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India","drop_address":"Uhghjh","is_door_delivery":"0","is_different_city_drop":"2","one_day_free_kilometer":"0","payable_amount":22,"per_kilometer_charge":"0"},{"booking_id":"14","vehicle_image":"1551263023XXimg-20190227-wa0007.jpg","vehicle_model":null,"user_data":{"name":"naseem akhtar","profile_pic":"1551336186XXimage-1994.jpg","contact_number":"9001638910"},"from_date_time":"1551700620000","to_date_time":"1551700620000","pick_address":"A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India","drop_address":"Fbjxxjdjfjfkfmdnx","is_door_delivery":"0","is_different_city_drop":"2","one_day_free_kilometer":"0","payable_amount":22,"per_kilometer_charge":"0"}]
     */

    private var status: String? = null
    private var message: String? = null
    private var data: List<Data>? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

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

    @Bindable
    var show_recyler_view= false
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.show_recyler_view)

            }
        }

    @Bindable
    var show_text_view= false
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.show_text_view)

            }
        }

    class Data : BaseObservable(), Serializable{
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

        fun getFormatedDAte(miliSec:String ,type:String):String{
            val simple = SimpleDateFormat("dd MMM HH:mm")

            // Creating date from milliseconds
            // using Date() constructor
            if (miliSec=="0") return ""

            val result = Date(miliSec.toLong()*1000L)

            var newate=  simple.format(result)
            return when (type) {
                "DAY" -> newate.split(" ")[0]
                "Month" -> newate.split(" ")[1]
                else -> newate.split(" ")[2]
            }

        }


        @Bindable
        var show_cancel_booking= false
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.show_cancel_booking)

                }
            }

        @Bindable
        var show_submit_button= false
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.show_submit_button)

                }
            }

        @Bindable
        var show_share_button = false
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.show_share_button)

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
            }
            return ""
        }

        fun getButtonText(status_id  : Int) : String{
            when (status_id){
                9 -> {
                    return  ActivityBooking.activity.getString(R.string.pay_now)
                }
                1 -> {
                    return ActivityBooking.activity.getString(R.string.download_pdf)
                }
                2 ->{
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

        fun getCancelButtonText(status_id  : Int) : String{
            when (status_id){
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

            }
            return ""
        }


        companion object {


            @BindingAdapter("setTime")
            @JvmStatic
            fun setTime(view: View, bookingModel: BookingModel.Data) {

                 if (bookingModel.seconds == "") {

                    val milliseconds = (bookingModel.to_date_time)!!.toLong()-(bookingModel.timer)!!.toLong()
                    var countDownTimer = object : CountDownTimer(milliseconds*1000L, 500) {

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

                            bookingModel.days = time.split(":")[0]
                            bookingModel.hours = time.split(":")[1]
                            bookingModel.minutes = time.split(":")[2]
                            bookingModel.seconds = time.split(":")[3]


                        }

                        override fun onFinish() {
                            //view.hide()
                        }
                    }

                    countDownTimer.start()
                }


            }

            @JvmStatic
            @BindingAdapter("manageLayout","layoutname")
            fun manageLayout(recyclerView: LinearLayout, model: BookingModel.Data, layoutname:String) {

                Log.e("","")


                when {
                    layoutname.equals("checkup_before_view_user") && model.booking_status== 2 || layoutname.equals("checkup_before_view_user") && model.booking_status== 3
                            || layoutname.equals("checkup_before_view_user") && model.booking_status== 5
                            || layoutname.equals("checkup_before_view_user") && model.booking_status== 6 -> {
                        recyclerView.show()

                    }
                    layoutname.equals("checkup_after_view_user") && model.booking_status== 5 || layoutname.equals("checkup_after_view_user") && model.booking_status== 6 -> {
                        recyclerView.show()

                    }
                    layoutname.equals("cancel_submit_btn") && model.booking_status==9 || layoutname.equals("cancel_submit_btn") && model.booking_status==0
                            || layoutname.equals("cancel_submit_btn") && model.booking_status==1 ||  layoutname.equals("cancel_submit_btn") && model.booking_status==2
                            ||  layoutname.equals("cancel_submit_btn") && model.booking_status==3 ||  layoutname.equals("cancel_submit_btn") && model.booking_status==5
                            ||  layoutname.equals("cancel_submit_btn") && model.booking_status==6 ||  layoutname.equals("cancel_submit_btn") && model.booking_status==4 -> {
                        if(model.booking_status == 0){
                            model.show_submit_button = false
                            model.show_cancel_booking = true
                        } else if(model.booking_status == 1 || model.booking_status == 3 || model.booking_status == 5){
                            model.show_submit_button = true
                            model.show_cancel_booking = true
                        } else if(model.booking_status == 6){
                            model.show_submit_button = true
                            model.show_cancel_booking = true
                            model.show_share_button = true
                        }else if(model.booking_status == 4){
                            model.show_submit_button = false
                            model.show_cancel_booking = false
                        }
                        else{
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

        var model_id : String? = null
        var vehicle_year : String? = null
        var extended_date_time : String? = null
        var is_extended : String? = null

        var booking_id: String? = null
        var vehicle_image: String? = null
        var vehicle_model: Any? = null
        var seater : String? = null
        var vehicle_type : String? = null
        var vehicle_subtype : String? = null
        var transmission : String? = null
        var fuel_type : String? = null
        var user_data: UserData? = null
        var from_date_time: String? = null
        var to_date_time: String? = null
        var pick_address: String? = null
        var drop_address: String? = null
        var is_door_delivery: String? = null
        var is_different_city_drop: String? = null
        var one_day_free_kilometer: String? = null
        var payable_amount: Int = 0
        var per_kilometer_charge: String? = null
        var total_amount : Int = 0
        var refundable_amount : String? = null
        var booking_status = 0
        var timer : Int = 0
        var extra_kilometer_charges : String? = null
        var fuel_charges : String? = null
        var car_accessories_charges : String? = null
        var vehicle_damage_charge : String? = null
        var cancellation_charge : String? = null
        var late_return_charge : String? = null
        var extended_charges : String? = null
        var booking_detail_pdf : String? = null
        var extra_payable_amount : String? = null
        var upload_url : String? = null
        var before_pickup_detail = BeforePickUpDetails()
        var after_pickup_detail = AfterPickUpDetails()

        class UserData : Serializable {
            /**
             * name : naseem akhtar
             * profile_pic : 1551336186XXimage-1994.jpg
             * contact_number : 9001638910
             */

            var name: String? = null
            var profile_pic: String? = null
            var contact_number: String? = null
            var license_image : String? = null
            var dl_number : String? = null
        }

        class BeforePickUpDetails : Serializable{
            var detail_id : String? = null
            var user_id : String? = null
            var booking_id : String? = null
            var kilometer : String? = null
            var fuel_amount : String? = null
            var detail_type : String? = null
            var vehicle_damage_charges : String? = null
            var vehicle_images= ArrayList<VehicleImages>()
            var accessories= ArrayList<Accessories>()

        }

        class AfterPickUpDetails : Serializable{
            var detail_id : String? = null
            var user_id : String? = null
            var booking_id : String? = null
            var kilometer : String? = null
            var fuel_amount : String? = null
            var detail_type : String? = null
            var vehicle_damage_charges : String? = null
            var vehicle_images= ArrayList<VehicleImages>()
            var accessories= ArrayList<Accessories>()
        }

        class VehicleImages :Serializable{

            var image_id : String? = null
            var user_id : String? = null
            var type : String? = null
            var car_image : String? = null
            var booking_id : String? = null
        }

        class Accessories : Serializable{

            var name : String? = null
            var quantity : String? = null
            var value : String? = null
            var booking_id  : String? = null
            var type : String? = null
            var status : String? = null
        }
    }

}
