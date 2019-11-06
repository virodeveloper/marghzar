package pick.com.app.varient.user.ui.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.livinglifetechway.k4kotlin.core.hideViews
import com.livinglifetechway.k4kotlin.core.isEmail
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.registration_footer.*
import kotlinx.android.synthetic.main.registration_header.*
import kotlinx.android.synthetic.main.user_registration_layout.*
import pick.com.app.MyApplication
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.base.model.CustomFirebaseUser
import pick.com.app.base.sociallogin.SocialLogin
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.File
import javax.inject.Inject


class RegistrationActivity : SocialLogin(), onResponse {




    @Inject
    lateinit var context: SharedPreferences


    @Inject
    lateinit var string: String


    @Inject
    lateinit var progressBar: ProgressBar


    @Inject
    lateinit var activssity: CommonActivity


    @Inject
    lateinit var sss: Context


    private var progressdialog: ProgressDialog? = null


    override fun <T : Any?> onSucess(result: T, methodtype: String?) {


        if (result is RegistrationModel) {
            Log.e("", "");

            var result = result as RegistrationModel
            showMessage(result.message, result.description)

            if (result.message != "Unsuccess") {
                var bundle = Bundle()
                bundle.putSerializable("model", (result))
                result.purpuse="Login"
                SessionManager.setLoginModel(result,this)
                Redirection().goToConfirmOtp(true, this, null)
            }
        }


    }

    override fun onError(error: String?) {
        showMessage("Unsuccess", if (error.toString()=="connectionError")resources.getString(R.string.internet_is_not_working_properly) else error.toString())


    }


    override fun getCurretnUser(firebaseUser: CustomFirebaseUser?) {
        super.getCurretnUser(firebaseUser)

        Log.e("", "")

        registrationLayoutBinding.user!!.data.dl_name = firebaseUser!!.displayName!!


        try {
            registrationLayoutBinding.user!!.data.email = firebaseUser.email!! ?:""
        } catch (e: Exception) {
            registrationLayoutBinding.user!!.data.email=""
        }
        registrationLayoutBinding.user!!.data.social_pic = firebaseUser.photoUrl!!.toString()
        loadImage(profile_image, registrationLayoutBinding.user!!.data.social_pic)
        registrationLayoutBinding.user!!.data.social_id = firebaseUser.uid!!
        registrationLayoutBinding.user!!.data.is_social = 1
        registrationLayoutBinding.user!!.data.login_type = firebaseUser!!.providerstype!!.get(0).toString()

        registrationLayoutBinding.invalidateAll()
        hideViews(layoutConnetcWith,layoutLoginbuttons)
        ApiServices<RegistrationModel>().getSocileFile(registrationLayoutBinding.user!!)



    }

    var registrationModel =
        RegistrationModel("User")
    fun goToCountryCode(){

        Redirection().goToCountryCode(false, this, COUNTRYCODE_REWUEST)

    }
    var COUNTRYCODE_REWUEST=10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                registrationLayoutBinding.user!!.data.country_code=requiredValue
                registrationLayoutBinding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@RegistrationActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    lateinit var activity: Activity
    lateinit var registrationLayoutBinding: pick.com.app.databinding.UserRegistrationLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        MyApplication.appComponent.inject(this)
        activity = activssity
        string.isEmail()
        val points = 15.0

        val mycar = resources.getString(pick.com.app.R.string.you_have, points)

        TextView(this).text = mycar

        registrationLayoutBinding = DataBindingUtil.setContentView(
            this@RegistrationActivity, pick.com.app.R.layout.user_registration_layout
        )


        profile_image.onClick { showImagepicker("Profile") }

        if (intent.extras.get("model")!=null){
            registrationModel=intent.extras.get("model") as RegistrationModel
            if (registrationModel.data.social_id!=""){

                hideViews(layoutConnetcWith,layoutLoginbuttons)
            }
        }


        registrationLayoutBinding.activity = this
        registrationLayoutBinding.user = registrationModel
        /*Google @{Initiliz
        atin(),Listner()} */
        googleOnCreat(this, googleLogin)
        /*Facebook @{Initilizatin(),Listner()} */
        facebookonCreat(this, facebooklogin)
        if (registrationLayoutBinding!!.user!!.data.login_type!=""){

            ApiServices<RegistrationModel>().getSocileFile(registrationModel)


        }

    }

    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        when (title) {

            "lisence" -> {
                registrationLayoutBinding.user!!.data.isLisancePreview = true
                registrationLayoutBinding.user!!.data.drivingLisencePic = File(images.path)
                registrationLayoutBinding.invalidateAll()
                loadImage(lisence_image, images.path)
            }
            "Profile" -> {
                registrationLayoutBinding.user!!.data.profilepic = File(images.path)
                registrationLayoutBinding.invalidateAll()
                loadImage(profile_image, images.path)
            }

        }
    }


    fun removePreview(view: View) {
        registrationLayoutBinding.user!!.data.drivingLisencePic=File("")
        registrationLayoutBinding.user!!.data.isLisancePreview = false
        registrationLayoutBinding.invalidateAll()
    }

    fun showImagepicker(title: String) {
        showImagePicker(title)
    }


    fun signUpOnclick(view: View) {

        registrationModel.userType="User"

        if (registrationLayoutBinding.user!!.checkvalidation(view, registrationModel)) {


            ApiServices<RegistrationModel>().callApiinObject(
                Urls.REGISTER_URL,
                this,
                registrationModel.getHAshap(registrationModel),
                RegistrationModel::class.java,
                true, this
            )

        }


    }

    fun loginonClick(view: View) {

        Redirection().goToLogin(true, this, null)

    }
}


