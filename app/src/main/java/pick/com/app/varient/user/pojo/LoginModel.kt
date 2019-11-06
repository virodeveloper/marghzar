package pick.com.app.varient.user.pojo

import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.EditText
import com.livinglifetechway.k4kotlin.core.addTextWatcher
import com.livinglifetechway.k4kotlin.core.isEmail
import pick.com.app.R
import java.io.Serializable

class LoginModel :Serializable{

    var mobileNo = ""
    var password = ""
    var social_id = ""
    var social_type = ""
    var is_social = 0
    var is_active = -1
    var status: String = ""
    var message: String = ""
    var description: String = ""
    var upload_url: String = ""
    lateinit var data: Data

    class Data{
        var user_id : String = ""
        var email : String = ""
        var dl_name : String = ""
        var contact_number : String = ""
        var country_code: String = ""
        var dl_number : String = ""
        var is_social : String = ""
        var password : String = ""
        var device_type : String = ""
        var device_token : String = ""
        var profile_pic : String = ""
        var dl_image : String = ""
        var term_condition : String = ""
        var otp_number : String = ""
    }

    companion object {

        @JvmStatic @BindingAdapter("setValue")
        fun setValue(editText: EditText, model: LoginModel) {

            var textWatcher = editText.addTextWatcher(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->


                when {
                    editText.hint.toString().toLowerCase().contains("mobile") -> {
                        model.mobileNo=charSequence.toString()

                    }

                    editText.hint.toString().toLowerCase().contains("password") -> {
                        model.password=charSequence.toString()
                    }

                }




            })

        }
    }


    fun checkvalidation(view: View, registratioi:RegistrationModel,type: String):Boolean{

        when {
            registratioi.data.contact_number==""&&type=="forgot" -> {
                Validation(view,registratioi.data.contact_number,"number",view.context.getString(R.string.please_enter_mobile_number))
                return false
            }
            registratioi.data.contact_number==""&&type=="login" -> {
                Validation(view,registratioi.data.contact_number,"number", view.context.getString(R.string.please_enter_mobile_number))
                return false
            }

            registratioi.data.password==""&&type=="login"  -> {
                Validation(view,registratioi.data.password,"",view.context.getString(R.string.please_enter_password))
                return false
            }

        }


        return true

    }


    fun Validation(view :View,value:String,type:String,message:String){

        when(type){

            "email"->{

                if (value.isEmpty())
                    Snackbar.make(view, view.context.getString(R.string.please_enter_email_address), Snackbar.LENGTH_SHORT).show()
                else
                    if (!value.isEmail())
                        Snackbar.make(view, view.context.getString(R.string.invalid_email), Snackbar.LENGTH_SHORT).show()

            }

            "number"->{


                if (value.isEmpty()){
                    Snackbar.make(view, view.context.getString(R.string.please_enter_mobile_number), Snackbar.LENGTH_SHORT).show()
                }
                else   if (value.length<10){
                    Snackbar.make(view, view.context.getString(R.string.please_enter_vaild_mobile_no), Snackbar.LENGTH_SHORT).show()
                }




            }

            else ->{

                Snackbar.make(view,
                    message, Snackbar.LENGTH_SHORT).show()
            }



        }




    }



}
