package pick.com.app.varient.user.ui.fragment.booking

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.livinglifetechway.k4kotlin.core.toast
import im.delight.android.location.SimpleLocation
import pick.com.app.R
import pick.com.app.base.BaseFragment
import pick.com.app.base.model.LocationModel
import pick.com.app.databinding.SearchBookingActivityBinding
import pick.com.app.interfaces.onLocationFromMap
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.user.pojo.FilterModel
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.RegistrationModel.Data.Locations.Companion.isalreadyClickformap
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.pojo.VehicleSearchData
import pick.com.app.varient.user.ui.fragment.MapDialogFragment
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap



class HomeFragment : BaseFragment(), onLocationFromMap {
    override fun getLocation(location: String?, latitude: Double?, longitude: Double?) {
        super.getLocation(location, latitude, longitude)
        binding.user!!.locationaddress = location!!
        binding.user!!.location_latitude = latitude!!
        binding.user!!.location_longitude = longitude!!

    }

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        when (methodtype) {
            Urls.GET_DROP_CITIES -> {
                var model = result as RegistrationModel.Data.Locations

                citiesmodel = model
            }
        }
    }

    var currentLocation: Location? = null
    var gpslocation: Location? = null

    /*
   * @override fro get LocationModel from Map
   * */
    override fun getLocationFromMap(address: LocationModel?) {


        val hahsp = HashMap<String, Any>();
        hahsp["pick_location_latitude"] = address!!.latitude
        hahsp["pick_location_longitude"] = address.logitude
        ApiServices<RegistrationModel.Data.Locations>().callApi(
            Urls.GET_DROP_CITIES, this, hahsp,
            RegistrationModel.Data.Locations::class.java,
            true, activity!!
        )


        binding.user!!.locationaddress = address.address

        binding.user!!.location_latitude = address.latitude.toDouble()
        binding.user!!.location_longitude = address.logitude.toDouble()
        currentLocation = Location("")
        currentLocation!!.latitude = address.latitude.toDouble()
        currentLocation!!.longitude = address.logitude.toDouble()
        binding.invalidateAll()

    }


    companion object {
        val ARG_PAGE = "location"
        var VEHICLE_ID = "vehicle_id"

        fun newInstance(location: Location,vehicle_id:String): HomeFragment {
            val args = Bundle()
            args.putParcelable(ARG_PAGE, location)
            args.putSerializable(VEHICLE_ID, vehicle_id)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getDate(date: String?, type: String) {
        super.getDate(date, type)
        if (type == "FromDate") {
            binding.user!!.fromDate = date!!
        } else {
            binding.user!!.toDate = date!!
        }

    }


    override fun getTime(time: String?, type: String) {
        super.getTime(time, type)

        if (type == "FromTime") {
            binding.user!!.fromTime = time!!
        } else {
            binding.user!!.toTime = time!!
        }

    }


    override fun onPause() {
        super.onPause()
        binding.user!!.vehicle_id=""
    }
    lateinit var binding: SearchBookingActivityBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = DataBindingUtil.inflate(
            inflater,
            pick.com.app.R.layout.search_booking_activity, container, false
        )

        binding.activity = this
        binding.user = SearchBookingModel()
        binding.user!!.vehicle_id =  getArguments()!!.get(VEHICLE_ID).toString()


        return binding.root

    }


    var citiesmodel = RegistrationModel.Data.Locations()
    fun getCities() {

        if (binding.user!!.locationaddress.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_where))
            return
        }
        if (binding.user!!.location_latitude != 0.0)
            MaterialDialog(activity!!).show {
                title(pick.com.app.R.string.select_city)
                customListAdapter(UniverSalBindAdapter(
                    pick.com.app.R.layout.custom_list_city,
                    object : UniverSalBindAdapter.ItemAdapterListener {
                        override fun onItemSelected(modssel: Any) {
                            dismiss()
                            var item = modssel as RegistrationModel.Data.Locations.Cities
                            binding.user!!.city = item.city
                            binding.user!!.city_id = item.city_id
                            binding.invalidateAll()
                        }


                    }).apply { addAll(citiesmodel.data as ArrayList<Any>) })
            }
        else
            activity.toast(activity!!.resources.getString(R.string.empty_where_location))


    }

    /*
   * @override fro get Permission Statusand type
   * */
    override fun getPermissionStatus(type: String?, status: String?) {
        super.getPermissionStatus(type, status)

        /*
         * @Manifest.permission.ACCESS_FINE_LOCATION Allowed @{MapDialogFragment()} Show
          * */
        if (type!!.equals(Manifest.permission.ACCESS_FINE_LOCATION) && status.equals("accept")) {
            if (currentLocation != null && !isalreadyClickformap) {
                isalreadyClickformap = true
                val fm = activity!!.supportFragmentManager
                val editNameDialogFragment = MapDialogFragment(this, currentLocation!!, gpslocation!!)
                editNameDialogFragment.show(fm!!, "fragment_edit_name")
            } else {
                simplelocation.beginUpdates()

                if (simplelocation.latitude != 0.0) {
                    currentLocation = Location("")
                    currentLocation!!.latitude = simplelocation.latitude
                    currentLocation!!.longitude = simplelocation.longitude
                } else {
                    activity.toast(getString(pick.com.app.R.string.pleaserestartappnotgettinglocationperoperly))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        VehicleSearchData.isbackenable=false
        FilterModel.hashmap= HashMap<String,Any>()
        FilterModel.filtermodel=FilterModel()
    }
    fun goToListing() {

        if (searchValidate()) {
            FilterModel.filtermodel=FilterModel()
            val from_date_time_timezone = binding.user!!.fromDate + " " + binding.user!!.fromTime
            val date = from_date_time_timezone.getDateWithServerTimeStamp()
            val to_date_time_timezone = binding.user!!.toDate + " " + binding.user!!.toTime
            val date_to = to_date_time_timezone.getDateWithServerTimeStamp()
            binding.user!!.fromDateTimeZone = "${date!!.time}"
            binding.user!!.toDateTimeZone = "${date_to!!.time}"

            if(binding.user!!.fromDateTimeZone<binding.user!!.toDateTimeZone){
                val bundle = Bundle()
                bundle.putSerializable("model", binding.user!!)
                Redirection().goToVechileListingUser(activity = activity!!, bundle = bundle)
            }
            else
                activity.toast("Error Occured")

        }

    }

    fun searchValidate(): Boolean {

      /*  var frm:Int=binding.user!!.fromTime.toInt()
        var to=binding.user!!.toTime.toInt()
        var diff=frm - to
                var i=1-2
        var diffi= diff/60

        Toast.makeText(activity,diffi.toString(),Toast.LENGTH_SHORT).show()*/
        if (binding.user!!.locationaddress.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_where))
            return false
        } else if (binding.user!!.fromDate.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_from_date))
            return false
        } else if (binding.user!!.fromTime.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_from_time))
            return false
        } else if (binding.user!!.toDate.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_to_date))
            return false
        } else if (binding.user!!.toTime.isEmpty()) {
            activity.toast(resources.getString(pick.com.app.R.string.please_select_to_time))
            return false
        } else if (binding.user!!.isdiferentcity && binding.user!!.city_id=="") {
            activity.toast(resources.getString(R.string.empty_city_message))
            return false
        }


        if (binding.user!!.toTime<=binding.user!!.fromTime){
                activity.toast("please Select different time")
            return false
        }


////        else if (binding.user!!.toTime<=binding.user!!.fromTime+1) {
////            activity.toast("From and to cannot be same")
////            return false
////        }
//        else if(binding.user!!.toDate==binding.user!!.fromDate){
//            if (binding.user!!.toTime<=binding.user!!.fromTime)
//                activity.toast("please Select different time")
//            return false
//        }
        else {
            return true
        }
    }


    fun getGpsLocation() {

        val location = getArguments()!!.get(ARG_PAGE) as Location


        //  binding.user!!.locationaddress= gettingLocationAddress(location.latitude,location.longitude)

        gpslocation = location
        currentLocation = location
        binding.user!!.location_latitude = location.latitude
        binding.user!!.location_longitude = location.longitude
    }

    /** Converting from String to Date **/
    fun String.getDateWithServerTimeStamp(): Date? {

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale("en"))

        try {
            return formatter.parse(this)
        } catch (e: ParseException) {
            return null
        }
    }

    /** Converting from Date to String**/
    fun Date.getStringTimeStampWithDate(): String {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(this)
    }

    lateinit var simplelocation: SimpleLocation
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //  val context = this
        val requireFineGranularity = false
        val passiveMode = false
        val updateIntervalInMilliseconds = (10 * 60 * 500).toLong()
        val requireNewLocation = false

        simplelocation = SimpleLocation(
            activity,
            requireFineGranularity,
            passiveMode,
            updateIntervalInMilliseconds,
            requireNewLocation
        )




        currentLocation = Location("")
        currentLocation!!.latitude = binding.user!!.location_latitude
        currentLocation!!.longitude = binding.user!!.location_longitude
        getGpsLocation()


    }


    fun checkLocationPermission() {

        if (!simplelocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(activity)
        } else {
            setPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

}

