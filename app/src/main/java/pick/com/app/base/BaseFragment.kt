package pick.com.app.base

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.kotlinpermissions.KotlinPermissions
import com.livinglifetechway.k4kotlin.core.hideKeyboard
import com.livinglifetechway.k4kotlin.core.toast
import pick.com.app.R
import pick.com.app.interfaces.OnDateTimePicker
import pick.com.app.interfaces.OnGooglePlacePicker
import pick.com.app.interfaces.OnPermissinResponse
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.custom.TimePickerCustom
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.launguage.MyContextWrapper
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment : Fragment(), onResponse, OnDateTimePicker, OnGooglePlacePicker, OnPermissinResponse {
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

    }

    override fun onError(error: String?) {
        showMessageWithError(error!!)
    }

    fun manageFavVehicle(vehicle_id : String,listner:onResponse?=this){

        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(BaseActivity.activity).data.user_id
        hashmap["vehicle_id"] = vehicle_id


        ApiServices<BookingModel>().callApi(
            Urls.FAVOURITE_VEHICLE, listner!!, hashmap, BookingModel::class.java, true, activity!!
        )
    }



    fun showMessageWithError(message: String="", isfinish:Boolean=false) {

        MaterialDialog(activity!!).show {
            title(text = getString(pick.com.app.R.string.app_name))
            positiveButton(text = getString(pick.com.app.R.string.ok)){
                if (isfinish) activity!!. finish()

            }
            message(text = message)

        }

    }


    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour : Int = 0
    private var mMinute : Int = 0
    override fun getLocation(location: String?, latitude: Double?, longitude: Double?) {

    }

    override fun getPermissionStatus(type: String?, status: String?) {

    }

    fun setPermission(permission: String) {


        KotlinPermissions.with(activity!!) // where this is an FragmentActivity instance
            .permissions(permission)
            .onAccepted { permissions ->
                //List of accepted permissions
                getPermissionStatus(permission, "accept")
            }
            .onDenied { permissions ->
                //List of denied permissions
                getPermissionStatus(permission, "denied")
            }
            .onForeverDenied { permissions ->
                //List of forever denied permissions
                getPermissionStatus("", "denied")
            }
            .ask()


    }


    fun gettingLocationAddress(lat: Double, long: Double): String {

        var address: String=""
        try {
            val geocoder = Geocoder(activity, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(lat, long, 1)
            val obj: Address = addresses.get(0)
            address = obj.getAddressLine(0)
        } catch (e: Exception) {
        }

        return address.trim()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            var place = PlaceAutocomplete . getPlace (activity, data);
            if (place != null) {


                getLocation(place.address.toString(),place.getLatLng().latitude,place.getLatLng().longitude)

            }

        }
    }


    override fun getDate(date: String?, type:String) {

    }
    var FIRST_PICUPPOINT_PICKER = 100

    fun goToLocationSearch(){
        try {

            var autocompleteFilter =  AutocompleteFilter.Builder()
                .setCountry("IN")

                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();

            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)



                .build(activity)
            startActivityForResult(intent, FIRST_PICUPPOINT_PICKER)
        } catch (e: GooglePlayServicesRepairableException) {

        } catch (e: GooglePlayServicesNotAvailableException) {

        }
    }

    fun changeLaunguage(){

        MaterialDialog(activity!!).show {


            listItemsSingleChoice(pick.com.app.R.array.laungages ,initialSelection = if ( SessionManager().getLaunguage(activity)=="ar") 0 else 1){ dialog, index, text ->

                if (index==0){
                    SessionManager().setLaunguage("ar",activity)
                    MyContextWrapper.wrap(activity, "ar")
                    Redirection().goToHome(true, activity!!, null)
                    /*val mStartActivity = Intent(context, SplashScreen::class.java)
                    mStartActivity.putExtra("lang","ar")
                    val mPendingIntentId = 123456
                    val mPendingIntent = PendingIntent.getActivity(
                        context,
                        mPendingIntentId,
                        mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                    val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent)
                    System.exit(0)*/
                }else{
                    SessionManager().setLaunguage("en",activity)
                    MyContextWrapper.wrap(activity, "en")
                    Redirection().goToHome(true, activity!!, null)
                   /* val mStartActivity = Intent(context, SplashScreen::class.java)
                    val mPendingIntentId = 123456
                    mStartActivity.putExtra("lang","en")

                    val mPendingIntent = PendingIntent.getActivity(
                        context,
                        mPendingIntentId,
                        mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                    val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent)
                    System.exit(0)*/
                }
            }
        }
    }

    override fun getTime(time: String?, type:String) {

    }


    fun showDataPicker(type: String,searchBookingModel: SearchBookingModel){


        var myCalendar = Calendar.getInstance();
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            getDate(sdf.format(myCalendar.time),type)


        }
        if (type=="ToDate"){
            if (searchBookingModel.fromDate==""||searchBookingModel.fromTime == "") {
                activity.toast(resources.getString(R.string.please_select_from_date_and_time))
                return
            }else{
                val dialog = DatePickerDialog( activity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                var myCalendar = Calendar.getInstance()
                myCalendar.set(mYear, mMonth, mDay)
                dialog.datePicker.minDate = myCalendar.timeInMillis
                dialog.show()
            }
        }else {
            searchBookingModel.toDate = ""
            searchBookingModel.toTime = ""
            searchBookingModel.fromTime = ""
            val dialog = DatePickerDialog( activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
            dialog.datePicker.minDate = System.currentTimeMillis() + 1000
            dialog.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.hideKeyboard()
    }


    fun showTimePicker(type: String,searchBookingModel: SearchBookingModel){
        var myCalendar = Calendar.getInstance();
        val date = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            // TODO Auto-generated method stub
            val time_select : String = hourOfDay.toString()+":"+minute.toString()
            mHour = hourOfDay
            mMinute = minute
            if (type=="FromTime"){
                searchBookingModel.toTime=""
            }
            getTime(changeDateTimeFormat(time_select), type)
        }




        if(type == "FromTime"){
            if(searchBookingModel.fromDate != ""){
                if(checkCurrentDate(searchBookingModel.fromDate)){
                    val dialog= TimePickerCustom(
                        activity, date, myCalendar
                            .get(Calendar.HOUR_OF_DAY) , myCalendar.get(Calendar.MINUTE) , true
                    )
                    val hour : Int = myCalendar.get(Calendar.HOUR_OF_DAY)+1
                    val minute : Int = myCalendar.get(Calendar.MINUTE)+1
                    dialog.setMin(hour ,minute)
                    dialog.setMax(23,59)
                    dialog.show()
                }else{

                    val dialog= TimePickerCustom(
                        activity, date, myCalendar
                            .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true
                    )

                    dialog.show()
                }
            }else {
                activity.toast(resources.getString(R.string.please_select_from_date))
                return
            }
        }else{
            if(searchBookingModel.fromDate != "" && searchBookingModel.toDate != "" && searchBookingModel.fromTime != ""){
                if(searchBookingModel.fromDate == searchBookingModel.toDate){
                    val dialog= TimePickerCustom(activity, date, mHour, mMinute, true)
                    dialog.setMin(mHour ,mMinute)
                    dialog.setMax(23,59)
                    dialog.show()


                }else{
                    val dialog= TimePickerCustom(
                        activity, date, myCalendar
                            .get(Calendar.HOUR_OF_DAY)+1, myCalendar.get(Calendar.MINUTE)+1, true
                    )

                    dialog.show()
                }
            }
            else
                activity.toast("Please set other fields first")


        }


    }

    fun convertTimeStamp(){
        val date = Date()
        val ts = Timestamp(date.time)
        activity.toast("time stamp   $ts")

    }

    fun changeDateTimeFormat(time: String): String {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val date = inputFormat.parse(time)
        return outputFormat.format(date)
    }

    fun checkCurrentDate(selected_date : String) : Boolean{
        val selectDate : String =  selected_date
        val c1 : Date = Calendar.getInstance().getTime()
        val df : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate : String = df.format(c1)
        return selectDate == formattedDate
    }
    companion object {
        lateinit var fragment:Fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment=this
    }



}

