package pick.com.app.varient.user.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.dialog_fragment_map.*
import kotlinx.android.synthetic.main.dialog_fragment_map.view.*
import pick.com.app.Constants
import pick.com.app.R
import pick.com.app.base.model.LocationModel
import pick.com.app.interfaces.onLocationFromMap
import pick.com.app.varient.user.pojo.RegistrationModel.Data.Locations.Companion.isalreadyClickformap
import java.util.*


@SuppressLint("ValidFragment")
class MapDialogFragment : DialogFragment, OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener {

     var isfirstTimeLoad: Boolean = true
    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var address: String = ""
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    lateinit var mMap: GoogleMap
     var onLocationFromMap: onLocationFromMap
     var gpslocation:Location


    constructor(onLocationFromMap: onLocationFromMap, simplelocation: Location,gpslocation:Location) : super() {
        this.simplelocation = simplelocation
        this.onLocationFromMap = onLocationFromMap
        this.gpslocation = gpslocation


    }

    companion object {
        var isSearched: Boolean = false
        var isResumeSearch: Boolean = false
         var viewMy: View?=null


    }

     var simplelocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(pick.com.app.R.dimen.popup_width)
        val height = resources.getDimensionPixelSize(pick.com.app.R.dimen.popup_height)
        dialog!!.window!!.setLayout(width, height)


    }
    var city=""

    override fun onDestroyView() {
        super.onDestroyView()
        isfirstTimeLoad = false
    }

    var FIRST_PICUPPOINT_PICKER = 100


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            var place = PlaceAutocomplete . getPlace (activity, data);
            if (place != null) {

                latitude = place.getLatLng().latitude
                longitude = place.getLatLng().longitude
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(mMap.cameraPosition)
                mMap.animateCamera(cameraUpdate)

                address = gettingLocationAddress(latitude, longitude)
                moveMap(latitude, longitude)



            }

        }
    }
   lateinit var  v:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // v = inflater.inflate(R.layout.dialog_fragment_map, container, false)



        if (viewMy != null) {
            val viewGroupParent = viewMy!!.getParent() as ViewGroup
            viewGroupParent?.removeView(viewMy)
        }
        try {
            viewMy = inflater.inflate(R.layout.dialog_fragment_map, container, false)
        } catch (e: Exception) {
            // map is already there
        }



v=viewMy!!




        dialog!!.window!!.setBackgroundDrawableResource(pick.com.app.R.drawable.rounded_dialog)


     

        val mapFragment = fragmentManager!!.findFragmentById(pick.com.app.R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        // Do all the stuff to initialize your custom view


        v.buttonGPS.onClick {
            latitude = gpslocation.latitude
            longitude = gpslocation.longitude
            address = gettingLocationAddress(latitude, longitude)
            moveMap(latitude, longitude)

        }
        v.closeButton.onClick {
            dialog!!.dismiss()

        }

        v.textView2.onClick {

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


/*
// return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/


        }
        v.search_button.onClick {

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


/*
// return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/


        }
        v.edit_text_landmark.setText("")

        if(Constants.apptype == Constants.AppType.USER)
            v.edit_text_landmark.hide()



        v.buttonSubmit.onClick {

            if ( v.edit_text_landmark.text!!.trim().toString()=="" && Constants.apptype == Constants.AppType.OWNER){

                Snackbar.make(v,context.getString(R.string.Pleaseenterlandmark),Snackbar.LENGTH_SHORT).show()


            }else {
                isfirstTimeLoad = true
                onLocationFromMap.getLocationFromMap(
                    LocationModel(
                        address,
                        "$latitude",
                        "$longitude",
                        v.edit_text_landmark.text.toString(), city
                    )
                )

                dialog!!.dismiss()
            }
        }

        return v
    }

    public override fun onPause() {
        // stop location updates (saves battery)


        // ...

        super.onPause()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isMyLocationButtonEnabled = true
        if (isfirstTimeLoad) {

            latitude = simplelocation!!.latitude
            longitude = simplelocation!!.longitude
            if (latitude!=0.0)
            address = gettingLocationAddress(latitude, longitude)
            moveMap(latitude, longitude)
        }

//        val latLng = LatLng(37.7688472, -122.4130859)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
//
//
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Position")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//        mMap.addMarker(markerOptions)
//
//
//        if (this.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return
//        }
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveStartedListener(this)
    }

    override fun onCameraIdle() {

            latitude = mMap.cameraPosition.target.latitude
            longitude = mMap.cameraPosition.target.longitude
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(mMap.cameraPosition)
           // mMap.animateCamera(cameraUpdate)

            address = gettingLocationAddress(latitude, longitude)
            moveMap(latitude, longitude)

    }

    private fun gettingLocationAddress(lat: Double, long: Double): String {
        val geocoder = Geocoder(activity, Locale.getDefault())
        var address=""



        try {

            var addresses = geocoder.getFromLocation(lat, long, 1);
            var cityName = addresses.get(0).getAddressLine(0);
            var stateName = addresses.get(0).getAddressLine(1);
            var countryName = addresses.get(0).getAddressLine(2);

            val obj: Address = addresses.get(0)
         address= obj.getAddressLine(0)

        textView2.text = address.trim()
        city = if (addresses.get(0).locality==null)
            addresses.get(0).adminArea
        else
            addresses.get(0).locality


        }catch (e:Exception){}
        isalreadyClickformap=false
        return address
    }

    private fun moveMap(latitude: Double, longitude: Double) {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        if (mMap != null) {
            val latLng = LatLng(latitude, longitude)

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            if (isfirstTimeLoad) {
                isfirstTimeLoad=false
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0F))
            }

        }
    }

    override fun onCameraMoveStarted(reason: Int) {

        when (reason) {
            //        The user gestured on the map.
            GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {

                latitude = 0.0
                longitude = 0.0
            }

            GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION -> {
//            The user tapped something on the map.
            }
            GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> {
//            The app moved the camera.
            }
        }
    }


}
