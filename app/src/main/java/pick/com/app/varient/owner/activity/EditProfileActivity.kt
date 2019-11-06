package pick.com.app.varient.owner.activity


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter

import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.show
import com.livinglifetechway.k4kotlin.core.toast
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.edit_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.base.model.LocationModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.base.model.ToolbarCustom.Companion.NoIcon
import pick.com.app.databinding.EditProfileBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.RegistrationModel.Companion.addapter
import pick.com.app.varient.user.pojo.RegistrationModel.Data.Locations.Companion.isalreadyClickformap
import pick.com.app.varient.user.ui.fragment.MapDialogFragment
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.File

class EditProfileActivity : CommonActivity(), onResponse {
    lateinit var locamodel: RegistrationModel.Data.Locations

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        if (methodtype == Urls.UPDATE_PROFILE_URL) {

            val result = result as RegistrationModel

            if (result.message.toLowerCase() == "success") {
                SessionManager.setLoginModel(result, this)
                onBackPressed()
                toast(result.description)
            }


            showMessage(result.message, result.description)
        } else if (methodtype == Urls.GET_CITIES) {
            locamodel = result as RegistrationModel.Data.Locations
            if (binding.user!!.data.locations.size != 0)
                for (item in binding.user!!.data.locations) {

                    item.data = locamodel.data
                    addapter.add(item)

                }
            else {
                if (binding.user!!.data.isdifferentcity)
                addapter.add(RegistrationModel.Data.Locations("edit", locamodel.data))
            }

        }

    }

    var currentLocation: Location? = null
    var gpslocation: Location? = null


    override fun myCurrentLocation(currentLocation: Location?) {
        super.myCurrentLocation(currentLocation)
        Log.e("", "")

        gpslocation = currentLocation

        if (this.currentLocation == null)
            this.currentLocation = currentLocation!!
    }

    override fun newLocation(location: Location?) {
        super.newLocation(location)

        gpslocation = location

        if (this.currentLocation == null)
            this.currentLocation = location!!
    }

    override fun onError(error: String?) {
        showMessage(
            "Unsuccess",
            if (error.toString() == "connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString()
        )

    }


    /*
   * @override fro get LocationModel from Map
   * */
    override fun getLocationFromMap(address: LocationModel?) {
        super.getLocationFromMap(address)
        binding.user!!.data.pick_location_address = address!!.address
        binding.user!!.data.pick_location_latitude = address.latitude.toDouble()
        binding.user!!.data.pick_location_longitude = address.logitude.toDouble()
        binding.user!!.data.owner_city = address.owner_city

        binding.invalidateAll()
        currentLocation = Location("")
        currentLocation!!.latitude = address.latitude.toDouble()
        currentLocation!!.longitude = address.logitude.toDouble()
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
            if (currentLocation != null&&!isalreadyClickformap) {
                val fm = supportFragmentManager
                isalreadyClickformap=true
                val editNameDialogFragment = MapDialogFragment(this, currentLocation!!, gpslocation!!)
                editNameDialogFragment.show(fm, "fragment_edit_name")
            } else {
                simplelocation.beginUpdates()

                if (simplelocation.latitude != 0.0) {
                    currentLocation = Location("")
                    currentLocation!!.latitude = simplelocation.latitude
                    currentLocation!!.longitude = simplelocation.longitude
                } else {
                    toast(getString(R.string.pleaserestartappnotgettinglocationperoperly))
                }
            }
        }
    }


    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        binding.user!!.data.profilepic = File(images.path)
        loadImage(profile_image, images.path)
        //binding.invalidateAll()
    }


    var registrationModel =
        RegistrationModel("Owner")
    lateinit var binding: EditProfileBinding


    lateinit var simplelocation: SimpleLocation


    override fun onResume() {
        super.onResume()
        isalreadyClickformap=false
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            simplelocation.beginUpdates()
        }
    }

    override fun onPause() {
        // stop location updates (saves battery)
        simplelocation.endUpdates()

        // ...

        super.onPause()
    }

    fun goToCountryCode() {

        Redirection().goToCountryCode(false, this, COUNTRYCODE_REWUEST)

    }

    var COUNTRYCODE_REWUEST = 10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                binding.user!!.data.country_code = requiredValue
                binding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@EditProfileActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun manageDropAddress(view: View) {

        if (binding.user!!.data.isdifferentcity)
            addapter.add(RegistrationModel.Data.Locations("edit", locamodel.data))
        else
            addapter.removeAll()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile)

        val edit = ToolbarCustom(ToolbarCustom.lefticon, getString(R.string.edit_profile), NoIcon, NoIcon)


        // construct a new instance of SimpleLocation
        val context = this
        val requireFineGranularity = false
        val passiveMode = false
        val updateIntervalInMilliseconds = (10 * 60 * 500).toLong()
        val requireNewLocation = false
        simplelocation = SimpleLocation(
            context,
            requireFineGranularity,
            passiveMode,
            updateIntervalInMilliseconds,
            requireNewLocation
        )



        binding.toolbar = edit
        binding.user = SessionManager.getLoginModel(this)

        currentLocation = Location("")
        currentLocation!!.latitude = binding.user!!.data.pick_location_latitude
        currentLocation!!.longitude = binding.user!!.data.pick_location_longitude
        binding.toolbar!!.title = "Edit Profile"




        left_Icon.onClick { onBackPressed() }



        binding.activity = this
        binding.user = SessionManager.getLoginModel(this)

        if (binding.user!!.data.deliver_user_door == 1) {
            radioYEs.isChecked = true
        } else {
            radioYEs.isChecked = false
            layoutPerkilometer.hide()
        }

        binding.user!!.data.isdifferentcity=(binding.user!!.data.is_different_city == 1)

        radioNo.onClick {
            binding.user!!.data.deliver_user_door = 0
            layoutPerkilometer.hide()
        }
        radioYEs.onClick {
            binding.user!!.data.deliver_user_door = 1
            layoutPerkilometer.show()
        }

        addapter = UniverSalBindAdapter(R.layout.custom_add_drop_loc)
        recycleviwe.adapter = addapter


        val hahsp = HashMap<String, Any>();
        hahsp["sdf"] = "dsf"
        ApiServices<RegistrationModel.Data.Locations>().callApi(
            Urls.GET_CITIES, this, hahsp,
            RegistrationModel.Data.Locations::class.java, true, this
        )

        binding.invalidateAll()


    }

    fun getProfile() {


    }


    fun showPicker(view: View) {
        showImagePicker("Profile")
    }

    fun checkLocationPermission() {

        if (!simplelocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        } else {
            setPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }


    fun submitOnclick(view: View) {


       /* if (binding.user!!.checkEditOwnervalidation(view, binding.user!!) && binding.user!!.checkMultipleLocation(
                view,
                addapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>
            )
        )
*/

            if (binding.user!!.checkEditOwnervalidation(view,  binding.user!!) && if (binding.user!!.data.isdifferentcity) binding.user!!.checkMultipleLocation(
                    view,
                    addapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>
                )else true)

        {


            ApiServices<RegistrationModel>().callApiinObject(
                Urls.UPDATE_PROFILE_URL,
                this,
                registrationModel.getUpdateOwnerJSONOBJECT(binding.user!!),
                RegistrationModel::class.java,
                true, this
            )


        }


    }


}
