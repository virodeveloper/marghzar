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

import com.livinglifetechway.k4kotlin.core.*
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.owner_registration_layout.*
import kotlinx.android.synthetic.main.registration_footer.*
import kotlinx.android.synthetic.main.registration_header.*
import pick.com.app.R
import pick.com.app.base.model.CustomFirebaseUser
import pick.com.app.base.model.LocationModel

import pick.com.app.base.sociallogin.SocialLogin
import pick.com.app.databinding.OwnerRegistrationLayoutBinding
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



class RegistrationActivity : SocialLogin(), onResponse {
    lateinit var locamodel: RegistrationModel.Data.Locations

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

            Log.e("", "")

            when(methodtype){

                Urls.GET_CITIES->{
                    locamodel = result as RegistrationModel.Data.Locations

                    addapter.add(RegistrationModel.Data.Locations("registration",result.data))
                }
                else->{

                    var result = result as RegistrationModel
                    showMessage(result.message, result.description)

                    if (result.message != "Unsuccess") {
                        var bundle = Bundle()
                        bundle.putSerializable("model", (result))
                        result.purpuse = "Login"
                        SessionManager.setLoginModel(result,this)
                        Redirection().goToConfirmOtp(true, this, null)
                    }
                }



            }


    }

    override fun onError(error: String?) {
        showMessage("Unsuccess", if (error.toString()=="connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString())
    }


    override fun getCurretnUser(firebaseUser: CustomFirebaseUser?) {
        super.getCurretnUser(firebaseUser)



        try {
            binding.user!!.data.dl_name = firebaseUser!!.displayName!!
            binding.user!!.data.email = firebaseUser.email!!
        } catch (e: Exception) {
            binding.user!!.data.email=""
        }
        binding.user!!.data.social_pic = firebaseUser!!.photoUrl!!.toString()
        loadImage(profile_image, binding.user!!.data.social_pic)
        binding.user!!.data.social_id = firebaseUser.uid!!
        binding.user!!.data.is_social = 1
        binding.user!!.data.login_type = firebaseUser.providerstype!!.get(0).toString()

        binding.invalidateAll()
        hideViews(layoutConnetcWith,layoutLoginbuttons)
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

        currentLocation= Location("")
        currentLocation!!.latitude=address.latitude.toDouble()
        currentLocation!!.longitude=address.logitude.toDouble()
    }
     var currentLocation: Location?=null
     var gpslocation: Location?=null

    override fun myCurrentLocation(currentLocation: Location?) {
        super.myCurrentLocation(currentLocation)
        Log.e("","")

        gpslocation=currentLocation

        if (this.currentLocation==null)
        this.currentLocation=currentLocation!!
    }


    override fun newLocation(location: Location?) {
        super.newLocation(location)
        gpslocation=location
        if (this.currentLocation==null)
        this.currentLocation=location!!
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
            if (currentLocation!=null&&!isalreadyClickformap) {
                val fm = supportFragmentManager
                isalreadyClickformap=true
                val editNameDialogFragment = MapDialogFragment(this, currentLocation!!,gpslocation!!)
                editNameDialogFragment.show(fm, "fragment_edit_name")
            }else{
                simplelocation.beginUpdates()

                if (simplelocation.latitude!=0.0){
                    currentLocation= Location("")
                    currentLocation!!.latitude=simplelocation.latitude
                    currentLocation!!.longitude=simplelocation.longitude
                }else{
                    toast(getString(R.string.pleaserestartappnotgettinglocationperoperly))
                }
            }
        }
    }

    lateinit var binding: OwnerRegistrationLayoutBinding

    var registrationModel =
        RegistrationModel("Owner")



    lateinit var simplelocation: SimpleLocation


    override fun onResume() {
        super.onResume()

        // make the device update its location




            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                simplelocation.beginUpdates()
            }




        // ...
    }


    override fun onPause() {
        // stop location updates (saves battery)
        simplelocation.endUpdates()

        // ...

        super.onPause()
    }


    fun goToCountryCode(){

        Redirection().goToCountryCode(false, this, COUNTRYCODE_REWUEST)

    }
    var COUNTRYCODE_REWUEST=10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                binding.user!!.data.country_code=requiredValue
                binding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@RegistrationActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun manageDropAddress(view: View) {

        if (binding.user!!.data.isdifferentcity)
            addapter.add(RegistrationModel.Data.Locations("registration", locamodel.data))
        else
            addapter.removeAll()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, pick.com.app.R.layout.owner_registration_layout)

        // construct a new instance of SimpleLocation
       // simplelocation =  SimpleLocation(this,true)
        val context = this
        val requireFineGranularity = false
        val passiveMode = false
        val updateIntervalInMilliseconds = (10 * 60 * 500).toLong()
        val requireNewLocation = false
        simplelocation =    SimpleLocation(context, requireFineGranularity, passiveMode, updateIntervalInMilliseconds, requireNewLocation)





        /*Google @{Initiliz
        atin(),Listner()} */
        googleOnCreat(this, googleLogin)
        /*Facebook @{Initilizatin(),Listner()} */
        facebookonCreat(this, facebooklogin)

        profile_image.onClick { showImagePicker("Profile") }

        radioIndividual.isChecked = true
        radioYEs.isChecked = true
        if (intent.extras.get("model") != null) {
            registrationModel = intent.extras.get("model") as RegistrationModel
            if (registrationModel.data.social_id!=""){

                hideViews(layoutConnetcWith,layoutLoginbuttons)
            }
        }

        binding.activity = this
        binding.user = registrationModel
         addapter = UniverSalBindAdapter(pick.com.app.R.layout.custom_add_drop_loc)
        recycleviwe.adapter = addapter



        var hahsp=HashMap<String,Any>();
        hahsp["sdf"]="dsf"
ApiServices<RegistrationModel.Data.Locations>().callApi(Urls.GET_CITIES,this,hahsp,
    RegistrationModel.Data.Locations::class.java,true,this)
        if (binding.user!!.data.login_type != "") {
            ApiServices<RegistrationModel>().getSocileFile(registrationModel)

        }
        hideKeyboard()
        hideKeyboard()

        radioNo.onClick {
            binding.user!!.data.deliver_user_door = 0
            layoutPerkilometer.hide()
        }
        radioYEs.onClick {
            binding.user!!.data.deliver_user_door = 1
            layoutPerkilometer.show() }
    }


    fun checkLocationPermission() {


        if (!simplelocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        }else {
            setPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

    fun checkExternalStpratePermission() {

        setPermission(Manifest.permission_group.STORAGE)

    }


    fun signUpOnclick(view: View) {



        //  ApiServices<RegistrationModel>().add_photo_coddddmment(this, registrationModel.getUserRegesterJSONOBJECT(registrationModel),this)
        registrationModel.userType="Owner"
        if (binding.user!!.checkvalidation(view, registrationModel) && if (binding.user!!.data.isdifferentcity) binding.user!!.checkMultipleLocation(
                view,
                addapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>
            )else true) {
            ApiServices<RegistrationModel>().callApiinObject(
                Urls.REGISTER_URL_OWNER,
                this,
                registrationModel.getUserRegesterJSONOBJECT(registrationModel),
                RegistrationModel::class.java,
                true, this
            )

        }


    }


    /*
    * @override for get Image from galary and camera
    * */


    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        binding.user!!.data.profilepic = File(images.path)
        loadImage(profile_image, images.path)
      //  binding.invalidateAll()
    }


    fun loginonClick(view: View) {

        Redirection().goToLogin(true, this, null)

    }
}
