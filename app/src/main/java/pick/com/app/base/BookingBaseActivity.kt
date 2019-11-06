package pick.com.app.base

import android.Manifest
import android.location.Location
import android.os.Bundle
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.kotlinpermissions.ifNotNullOrElse
import com.livinglifetechway.k4kotlin.core.toast
import im.delight.android.location.SimpleLocation
import pick.com.app.base.model.LocationModel
import pick.com.app.interfaces.onLocationFromMap
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.ui.fragment.MapDialogFragment
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



public open class BookingBaseActivity :BaseActivity(), onLocationFromMap {
   
     var searchBookingMode=SearchBookingModel()
    fun getCities(){
        var materialDialog=   MaterialDialog(activity!!)

        var adpter=UniverSalBindAdapter(pick.com.app.R.layout.custom_list_city,object: UniverSalBindAdapter.ItemAdapterListener{
            override fun onItemSelected(modssel: Any) {
                materialDialog.dismiss()
                var item=modssel as RegistrationModel.Data.Locations.Cities
                searchBookingMode.city= item.city
                searchBookingMode.city_id=item.city_id

            }


        })

        adpter.addAll(citiesmodel.data as ArrayList<Any>)



        materialDialog .show {
            title(pick.com.app.R.string.select_city)
            customListAdapter(adpter)}



    }




    override fun getLocation(location: String?, latitude: Double?, longitude: Double?) {
        super.getLocation(location, latitude, longitude)
        searchBookingMode.locationaddress=location!!
        searchBookingMode.location_latitude=latitude!!
        searchBookingMode.location_longitude=longitude!!

    }

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        when(methodtype)
        {
            Urls.GET_CITIES->{
                var model=result as RegistrationModel.Data.Locations

                citiesmodel=model
            }

            Urls.GET_DROP_CITIES -> {
                var model = result as RegistrationModel.Data.Locations

                citiesmodel = model
            }
        }
    }

    var currentLocation: Location?=null
    var gpslocation: Location?=null

    /*
   * @override fro get LocationModel from Map
   * */
    override fun getLocationFromMap(address: LocationModel?) {


        val hahsp = HashMap<String, Any>();
        hahsp["pick_location_latitude"] = address!!.latitude
        hahsp["pick_location_longitude"] = address!!.logitude
        ApiServices<RegistrationModel.Data.Locations>().callApi(
            Urls.GET_DROP_CITIES, this, hahsp,
            RegistrationModel.Data.Locations::class.java,
            true, activity!!
        )


        searchBookingMode.locationaddress=address!!.address

        searchBookingMode.location_latitude=address.latitude.toDouble()
        searchBookingMode.location_longitude=address.logitude.toDouble()
        currentLocation = Location("")
        currentLocation!!.latitude = address.latitude.toDouble()
        currentLocation!!.longitude = address.logitude.toDouble()

    }

    override fun getDate(date: String?, type: String?) {
        super.getDate(date, type)
        if (type=="FromDate"){
            searchBookingMode.fromDate=date!!
        }
        else{
            searchBookingMode.toDate=date!!
        }
    }


    override fun getTime(time: String?, type: String?) {
        super.getTime(time, type)
    

        if (type=="FromTime"){
            searchBookingMode.fromTime=time!!
        }else{
            searchBookingMode.toTime=time!!
        }

    }

    var citiesmodel=RegistrationModel.Data.Locations()
    var isalreadyClickformap=false

    /*
   * @override fro get Permission Statusand type
   * */
    override fun getPermissionStatus(type: String?, status: String?) {
        super.getPermissionStatus(type, status)

        /*
         * @Manifest.permission.ACCESS_FINE_LOCATION Allowed @{MapDialogFragment()} Show
          * */
        if (type!!.equals(Manifest.permission.ACCESS_FINE_LOCATION) && status.equals("accept")) {
            if (currentLocation != null&&!isalreadyClickformap) {
                val fm = supportFragmentManager
                isalreadyClickformap=true
                val editNameDialogFragment = MapDialogFragment(this, currentLocation!!,gpslocation!!)

                editNameDialogFragment.show(fm!!, "fragment_edit_name")
            } else {
                simplelocation.beginUpdates()

                if (simplelocation.latitude != 0.0) {
                    currentLocation = Location("")
                    currentLocation!!.latitude = simplelocation.latitude
                    currentLocation!!.longitude = simplelocation.longitude
                } else {
                    toast(getString(pick.com.app.R.string.pleaserestartappnotgettinglocationperoperly))
                }
            }
        }
    }




    fun searchValidate() : Boolean{
        if(searchBookingMode.locationaddress.isEmpty()){
            activity.toast(resources.getString(pick.com.app.R.string.please_select_where))
            return false
        }else if(searchBookingMode.fromDate.isEmpty()){
            activity.toast(resources.getString(pick.com.app.R.string.please_select_from_date))
            return false
        }else if(searchBookingMode.fromTime.isEmpty()){
            activity.toast(resources.getString(pick.com.app.R.string.please_select_from_time))
            return false
        }else if(searchBookingMode.toDate.isEmpty()){
            activity.toast(resources.getString(pick.com.app.R.string.please_select_to_date))
            return false
        }else if(searchBookingMode.toTime.isEmpty()){
            var waqas=searchBookingMode.fromTime
            activity.toast(waqas)
            return false
        }
        else{
            return true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  val context = this
        val requireFineGranularity = false
        val passiveMode = false
        val updateIntervalInMilliseconds = (10 * 60 * 500).toLong()
        val requireNewLocation = false
        activity=this
        simplelocation = SimpleLocation(
            activity,
            requireFineGranularity,
            passiveMode,
            updateIntervalInMilliseconds,
            requireNewLocation
        )




        currentLocation = Location("")
        currentLocation!!.latitude = simplelocation.latitude
        currentLocation!!.longitude = simplelocation.longitude

        gpslocation=currentLocation

        val hahsp=HashMap<String,Any>();
        hahsp["sdf"]="dsf"
        ApiServices<RegistrationModel.Data.Locations>().callApi(
            Urls.GET_CITIES,this,hahsp,
            RegistrationModel.Data.Locations::class.java,true,this)
    }


    /** Converting from String to Date **/
    fun String.getDateWithServerTimeStamp(): Date? {
        val dateFormat = SimpleDateFormat(
            "dd/MM/yyyy HH:mm a",
            Locale.getDefault()
        )
        try {
            return dateFormat.parse(this)
        } catch (e: ParseException) {
            return null
        }
    }
    /** Converting from Date to String**/
    fun Date.getStringTimeStampWithDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(this)
    }
    lateinit var  simplelocation : SimpleLocation
    fun checkLocationPermission() {

        if (!simplelocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(activity)
        } else {
            setPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }
}
