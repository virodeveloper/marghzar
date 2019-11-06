package pick.com.app.varient.user.pojo

import android.view.View
import android.widget.Switch
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.BR
import pick.com.app.base.BaseActivity.Companion.activity
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.VehicleSearchData.Companion.isbackenable
import pick.com.app.varient.user.ui.activity.VehicleDetailsActivity
import pick.com.app.varient.user.ui.activity.VehicleListingUser
import java.io.Serializable

class SearchBookingModel:BaseObservable(), Serializable {

    companion object {

        @JvmStatic
        @BindingAdapter("onBackPress")
        fun onBackPress(view: View, model: SearchBookingModel) {


            if (activity is VehicleDetailsActivity){
                view.onClick {
                    if (view is Switch)
                    view.isChecked = (model.isdeliveryatdoor)
                    activity.finish()
                    activity.finish()
                    isbackenable=true
                }
            }else {
                if (isbackenable)
                    activity.onBackPressed()
                view.onClick {
                    if (view is Switch)
                    view.isChecked = (model.isdeliveryatdoor)
                    activity.onBackPressed() }
            }

        }


    }


    var location_address : String = ""
    var sortting : String = "Relevance"
	var location_latitude_search : Double = 0.0
	var location_longitude_search  : Double = 0.0
	var start_date_time_search : String = ""
	var end_date_time_search : String = ""
	var is_diferent_city : String = "0"
	var drop_location_search : String = ""
	var is_delivery_atdoor : String = "0"
    var sort_id : String = ""
    var no_of_items : String = ""
    var transmission_id : String = ""
    var vehicle_type_id = ArrayList<String> ()
    var start_date_time=""
    var end_date_time=""
    var drop_location=""
    var city_id =""

    @Bindable
    var city: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.city)

            }
        }



    @Bindable
    var locationaddress: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.locationaddress)

            }
        }


    var location_latitude: Double = 0.0

    var location_longitude: Double = 0.0


    fun getDay(date:String):String{

        return date.split("/")[0]

    }
    fun getMonthName(date:String):String{

        return when(date.split("/")[1]){

            "01"-> return "January"
            "02"-> return "February"
            "03"-> return "March"
            "04"-> return "April"
            "05"-> return "May"
            "06"-> return "June"
            "07"-> return "July"
            "08"-> return "August"
            "09"-> return "September"
            "10"-> return "October"
            "11"-> return "November"
            "12"-> return "December"

            else -> ""
        }



    }

    @Bindable
    var isdeliveryatdoor: Boolean = false
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.isdeliveryatdoor)

            }
        }



    @Bindable
    var isdiferentcity: Boolean = false
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.isdiferentcity)

            }
        }


    @Bindable
    var fromDateTimeZone : String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.fromDateTimeZone)

            }
        }

    var vehicle_id=""
    @Bindable
    var toDateTimeZone : String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.toDateTimeZone)

            }
        }

fun isValueBlank(string:String):Boolean{

    return string == ""

}


    @Bindable
    var fromDate: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.fromDate)

            }
        }



    @Bindable
    var fromTime: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.fromTime)

            }
        }



    @Bindable
    var toTime: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.toTime)

            }
        }




    @Bindable
    var toDate: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.toDate)

            }
        }


    fun searchVehicleBooking(searchBookingModel: SearchBookingModel, view : View): HashMap<String, Any> {
/*location_address:fadsfasdfsa
location_latitude:fasdfasfasdf
location_longitude:fasdfasdfas
seater_id:1
start_date_time:123123123
end_date_time:1231231
is_diferent_city:1
vehicle_subtype:1
drop_location:123123
is_delivery_atdoor:123123123*/

        searchBookingModel.fromDateTimeZone

        return HashMap<String, Any>().apply {
            put("location_address", searchBookingModel.locationaddress)
            put("searchuser_id",SessionManager.getLoginModel(view.context).data.user_id)
            put("location_latitude", searchBookingModel.location_latitude)
            put("location_longitude", searchBookingModel.location_longitude)
            put("start_date_time", ((searchBookingModel.fromDateTimeZone.toLong())/1000L).toString())
            put("end_date_time", ((searchBookingModel.toDateTimeZone.toLong())/1000L).toString())
            put("city_id", searchBookingModel.city_id)
            put("is_different_city",if (searchBookingModel.isdiferentcity) 1 else 2)
            put("drop_location", searchBookingModel.drop_location_search)
            put("is_delivery_atdoor", if (searchBookingModel.isdeliveryatdoor) 1 else 2)
            put("vehicle_id", VehicleListingUser.searchmodel.vehicle_id)
            put("sort_id", "")
            put("no_if_item", "")
            put("filter_data", "")
                putAll(FilterModel.hashmap)
        }
    }


}
