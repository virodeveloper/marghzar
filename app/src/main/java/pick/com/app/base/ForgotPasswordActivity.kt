package pick.com.app.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import pick.com.app.R
import pick.com.app.databinding.ActivityForgotPasswordBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ForgotPasswordActivity : BaseActivity(), onResponse {

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        if (result is RegistrationModel) {
            Log.e("", "");

            var result = result as RegistrationModel
            if (result.message.toLowerCase()=="success"){
                finishAffinity()
                Redirection().goToLogin(true,this,null)}
            showMessage(result.message, result.description)
        }
    }


    override fun onError(error: String?) {

        showMessage(message=if (error.toString() == "connectionError") activity.getString(R.string.something_went_wrong) else error.toString())

    }

    lateinit var  binding: ActivityForgotPasswordBinding
    var registrationModel = RegistrationModel("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)



            registrationModel= SessionManager.getLoginModel(this)

        binding.user = registrationModel
        binding.activity = this
    }
    fun onClickSubmit(view: View) {
        if (binding.user!!.checkSetPassWordvalidation(view , registrationModel, "forgotpassword")) {

            ApiServices<RegistrationModel>().callApi(
                Urls.SET_PASSWORD_URL,
                this,
                registrationModel.forgotPassword(registrationModel),
                RegistrationModel::class.java,
                true, this
            )
        }
    }
}