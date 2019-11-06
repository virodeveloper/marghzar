package pick.com.app.varient.user.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.toast
import pick.com.app.Constants
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.databinding.OtpLayoutBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ConfirmOtpActivity : BaseActivity(), onResponse {
    lateinit var text12: TextView
    lateinit var text11: TextView
    val maxTimeInMilliseconds: Long = 60000

    override fun <T> onSucess(result: T, methodtype: String?) {
super.onSucess(result, methodtype)
        when (methodtype) {
            Urls.SEND_OTP_URL -> {


                var result = result as RegistrationModel

                toast(result.description)

            }
            Urls.VERIFY_OTP_URL -> {


                var result = result as RegistrationModel

                if (result.message.toLowerCase() == "success") {


                    when(model.purpuse) {
                        "Login" -> {
                            SessionManager.setLoginModel(result, this)
                            finishAffinity()
                            Redirection().goToHome(true, this, null)
                        }
                       "ForgotPasowrd" -> {

                           toast(result.description)
                         var bundel=  Bundle()
                             bundel.putSerializable("model",result)
                           SessionManager.setLoginModel(result, this)

                           Redirection().goToForgot(true, this, null)
                       }
                    }
                }

                showMessage(result.message, result.description)

            }
        }

    }



    override fun onError(error: String?) {
        //showMessage("Unsuccess", if (error.toString()=="connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString())

    }

    lateinit var otpLayoutBinding: OtpLayoutBinding
    var model = RegistrationModel("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpLayoutBinding = DataBindingUtil.setContentView(
            this@ConfirmOtpActivity, R.layout.otp_layout
        )
        text12=findViewById(R.id.tx1)
        text11=findViewById(R.id.tx2)
        // in your case

        startTimer(maxTimeInMilliseconds, 1000)
      //  message("Resend button will be enabled in 60 seconds")




        model = SessionManager.getLoginModel(this)

        if (model.contact_number==""){
            model.contact_number=model.data.contact_number
            model.country_code=model.data.country_code
        }
        otpLayoutBinding.user = model
        otpLayoutBinding.activity = this


    }

   /* fun message(msg:String){
        object : CountDownTimer(40000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text11.visibility = View.VISIBLE
                text12.text=msg

            }
            override fun onFinish() {
                text12.visibility = View.INVISIBLE
                text11.text = ""
            }
        }.start()
    }*/




    fun confirmOTPClick(view: View) {
/*otp_number,contact_number*/


        if (RegistrationModel("User").checkOTPValidation(view, otpLayoutBinding.user!!)) {
            val hashMap = HashMap<String, Any>()
            hashMap["otp_number"] = otpLayoutBinding.user!!.data.otp_number

            if (otpLayoutBinding.user!!.contact_number=="") {
                hashMap["country_code"] = otpLayoutBinding.user!!.data.country_code

                hashMap["contact_number"] = otpLayoutBinding.user!!.data.contact_number
            }
            else {
                hashMap["country_code"] = otpLayoutBinding.user!!.country_code

                hashMap["contact_number"] = otpLayoutBinding.user!!.contact_number
            }
            ApiServices<RegistrationModel>().callApi(
                Urls.VERIFY_OTP_URL,
                this,
                hashMap,
                RegistrationModel::class.java,
                true,
                this
            )

        }

    }

    fun resendOTP(view: View) {
        val hashMap = HashMap<String, Any>()
        startTimer(maxTimeInMilliseconds, 1000)



        if (otpLayoutBinding.user!!.contact_number=="") {
            hashMap["country_code"] = otpLayoutBinding.user!!.data.country_code

            hashMap["contact_number"] = otpLayoutBinding.user!!.data.contact_number
        }
        else {
            hashMap["country_code"] = otpLayoutBinding.user!!.country_code

            hashMap["contact_number"] = otpLayoutBinding.user!!.contact_number
        }



        if (Constants.apptype == Constants.AppType.OWNER) {
            Urls.OWNER_LOGIN
            hashMap["user_type"] = "o"
        } else {
            Urls.LOGIN
            hashMap["user_type"] = "u"
        }

        ApiServices<RegistrationModel>().callApi(
            Urls.SEND_OTP_URL,
            this,
            hashMap,
            RegistrationModel::class.java,
            true,
            this
        )

    }
    fun startTimer(finish: Long, tick: Long) {

        object : CountDownTimer(finish, tick) {

            override fun onTick(millisUntilFinished: Long) {
                text12.visibility = View.VISIBLE
                val remainedSecs = millisUntilFinished / 1000
                text12.setText("" + remainedSecs / 60 + ":" + remainedSecs % 60)// manage it accordign to you
                text11.visibility=View.INVISIBLE
            }

            override fun onFinish() {
                text12.setText("If not recieved click here")
                Toast.makeText(this@ConfirmOtpActivity, "Finish", Toast.LENGTH_SHORT).show()

                cancel()
                text11.visibility=View.VISIBLE
            }
        }.start()
    }
}
