package pick.com.app.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.toast
import com.paytabs.paytabs_sdk.utils.PaymentParams
import kotlinx.android.synthetic.main.login_layout.*
import pick.com.app.Constants
import pick.com.app.MyApplication

import pick.com.app.base.model.CustomFirebaseUser
import pick.com.app.base.sociallogin.SocialLogin
import pick.com.app.databinding.LoginLayoutBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.LoginModel
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import javax.inject.Inject
import com.hbb20.CountryCodePicker
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import pick.com.app.R


class LoginActivity : SocialLogin(), onResponse {
   lateinit var countryCodePicker: CountryCodePicker
    lateinit var cname: String
    //countryCodePicker=findViewById(R.id.cou);
    //String cname=countryCodePicker.getSelectedCountryCode();

    companion object {
        var  loginActivity : Activity? = null
    }

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        when (methodtype) {
//            Urls.BANK ->{
//                val result = result as RegistrationModel
//                val t=1
//            }

            Urls.LOGIN, Urls.OWNER_LOGIN -> {

                val result = result as RegistrationModel

                if (result.is_active == 0) {
                    val bundel = Bundle()
                    result.country_code= binding.user!!.data.country_code
                    bundel.putSerializable("model", result)
                    result.purpuse = "Login"
                    toast(result.description)

                    SessionManager.setLoginModel(result, this)

                    Redirection().goToConfirmOtp(false, this@LoginActivity, null)


                } else if (result.data.is_active == 1 && result.message.toLowerCase() == "success") {
                    SessionManager.setLoginModel(result, this)

                    Redirection().goToHome(true, this, null)

                } else if (result.data.is_active == 0) {
                    SessionManager.setLoginModel(result, this)

                    Redirection().goToHome(true, this, null)

                } else {


                    showMessage(result.message, result.description)

                }

            }

            Urls.SOCIAL_LOGIN_URL -> {
                val result = result as RegistrationModel
                if (result.message.toLowerCase() == "unsuccess") {
                    val bundle = Bundle()

                    binding.user!!.data.password=""

                    bundle.putSerializable("model", binding.user)


                    Redirection().goToRegistrationOwner(false, this, bundle)
                } else if (result.data.is_active == 0) {
                    val bundel = Bundle()
                    bundel.putSerializable("model", result)
                    result.purpuse = "Login"
                    result.country_code=  binding.user!!.data.country_code


                    SessionManager.setLoginModel(result, this)
                    Redirection().goToConfirmOtp(false, this@LoginActivity, null)


                } else if (result.is_active == 1 && result.message.toLowerCase() == "success") {
                    SessionManager.setLoginModel(result, this)

                    Redirection().goToHome(true, this, null)
                } else if (result.data.is_active == 1 && result.message.toLowerCase() == "success") {
                    SessionManager.setLoginModel(result, this)

                    Redirection().goToHome(true, this, null)
                }


                //


            }
            Urls.SEND_OTP_URL -> {
                val bundle = Bundle()
                val result = result as RegistrationModel
                if (result.message.toLowerCase() == "success") {
                    result.purpuse = "ForgotPasowrd"
                    bundle.putSerializable("model", result)
                    SessionManager.setLoginModel(result, this)

                    Redirection().goToConfirmOtp(false, this, null)

                }
                showMessage(result.message, result.description)

                if (result.message == "success")
                    toast(result.description)

            }

        }
    }


    override fun onError(error: String?) {

        showMessage(message=
            if (error.toString() == "connectionError") resources.getString(pick.com.app.R.string.something_went_wrong) else error.toString()
        )

    }

    override fun getPermissionStatus(type: String?, status: String?) {
        super.getPermissionStatus(type, status)
    }

    @Inject
    lateinit var context: SharedPreferences


    override fun getCurretnUser(firebaseUser: CustomFirebaseUser?) {
        super.getCurretnUser(firebaseUser)
        Log.e("", "")

        /*  binding.user!!.social_id = firebaseUser!!.uid
          binding.user!!.is_social = 1
          binding.user!!.is_social = 1
          binding.user!!.is_social = 1
          binding.user!!.social_type = firebaseUser!!.providers!![0].toString().first().toString()
  */

        binding.user!!.data.dl_name = firebaseUser!!.displayName!!

        try {
            binding.user!!.data.email = firebaseUser.email!! ?: ""
        } catch (e: Exception) {
            binding.user!!.data.email = ""
        }

        binding.user!!.data.social_pic = firebaseUser.photoUrl!!.toString()

        binding.user!!.data.social_id = firebaseUser.uid!!
        binding.user!!.data.is_social = 1

        binding.user!!.data.login_type = firebaseUser.providerstype!!
        binding.user!!.data.login_type = firebaseUser.providerstype!!.get(0).toString()


        val hashmap = HashMap<String, Any>()
        hashmap["social_id"] = binding.user!!.data.social_id

        if (Constants.apptype == Constants.AppType.OWNER) {
            Urls.OWNER_LOGIN
            hashmap["user_type"] = "o"
        } else {
            Urls.LOGIN
            hashmap["user_type"] = "u"
        }

        hashmap["login_type"] = binding.user!!.data.login_type
        ApiServices<RegistrationModel>().callApi(
            Urls.SOCIAL_LOGIN_URL,
            this,
            hashmap,
            RegistrationModel::class.java,
            true,
            this
        )

    }

    lateinit var binding: LoginLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
      //  ApiServices<LoginModel>().goPayment(this)
       // countryCodePicker=findViewById(pick.com.app.R.id.cou)
        binding = DataBindingUtil.setContentView(this, R.layout.login_layout)


        loginActivity=this
        googleOnCreat(this, googleButton)
        facebookonCreat(this, facebookButton)
        countryCodePicker = findViewById(R.id.cou)
        cname = "+" + countryCodePicker.selectedCountryCode

        googleLogout()
        faceBookLogout()
        binding.redirection = this
        binding.user = RegistrationModel("User")

        setPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        setPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    }


    fun loginOnclick(view: View) {
        cname = countryCodePicker.selectedCountryCode
        // Redirection().goToHome(false,this,null)
        if (LoginModel().checkvalidation(view, binding.user!!, "login")) {

            val hashMap = HashMap<String, Any>()
            val hashMa = HashMap<String, Any>()
            hashMap["contact_number"] = binding.user!!.data.contact_number
           // hashMap["country_code"] = binding.user!!.data.country_code
            hashMap["country_code"] = cname
            hashMap["password"] = binding.user!!.data.password
            hashMap["device_type"] = "android"
            hashMap["device_token"] = SessionManager().getDeviceID(this)
            hashMa["device_type"] = "android"

//            ApiServices<RegistrationModel>().callApi(
//                Urls.BANK, this, hashMa,
//                RegistrationModel::class.java, true, this
//            )

            if (Constants.apptype == Constants.AppType.OWNER) {
                Urls.OWNER_LOGIN
                hashMap["user_type"] = "o"
            } else {
                Urls.LOGIN
                hashMap["user_type"] = "u"
            }
            hashMap["device_token"] = ""

            ApiServices<RegistrationModel>().callApi(
                if (Constants.apptype == Constants.AppType.OWNER) {
                    Urls.OWNER_LOGIN
                } else {
                    Urls.LOGIN
                }, this, hashMap,
                RegistrationModel::class.java, true, this
            )

        }
    }

    fun goToCountryCode(){

        Redirection().goToCountryCode(false, this, COUNTRYCODE_REWUEST)

    }
    var COUNTRYCODE_REWUEST=10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data!!.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));
            Toast.makeText(this, data.getStringExtra(PaymentParams.RESPONSE_CODE), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra(PaymentParams.TRANSACTION_ID), Toast.LENGTH_LONG).show();
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }



        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                binding.user!!.data.country_code=requiredValue
                binding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@LoginActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun goToOtpScreen(view: View, isfinish: Boolean) {
        if (LoginModel().checkvalidation(view, binding.user!!, "forgot")) {

            cname = countryCodePicker.selectedCountryCode
            val hashMap = HashMap<String, Any>()
            hashMap["country_code"] = cname

            hashMap["contact_number"] = binding.user!!.data.contact_number
            if (Constants.apptype == Constants.AppType.OWNER) {
                Urls.OWNER_LOGIN
                hashMap["user_type"] = "o"
            } else {
                Urls.LOGIN
                hashMap["user_type"] = "u"
            }

            ApiServices<RegistrationModel>().callApi(
                Urls.SEND_OTP_URL, this, hashMap,
                RegistrationModel::class.java, true, this
            )

        }

    }


    fun goToRegistratinOwner(isfinish: Boolean) {

        Redirection().goToRegistrationOwner(isfinish, this, null)
    }

    fun paymethod(view: View) {
       val intent=Intent(this,PaymentActi()::class.java)
        startActivityForResult(intent,PaymentParams.PAYMENT_REQUEST_CODE)
    }


}
