package pick.com.app.varient.user.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.toolbar.*

import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.ActivityContactUsBindingImpl
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ContactUsActivity : BaseActivity() {


    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        when (methodtype){

            Urls.SAVE_CONTACT_US->{
                var result=result as RegistrationModel

                onError(result.description)
                activityContactUsBinding.user= RegistrationModel("User")
                activityContactUsBinding.invalidateAll()
            }

            Urls.GET_CONTACT_US->{
                var result=result as RegistrationModel.ContactUS

                if (result.status==1)
                     showMessageWithError(message=result.description!!,isfinish = false)

                activityContactUsBinding=DataBindingUtil.setContentView(this,R.layout.activity_contact_us)

                val toolbarCustom =
                    ToolbarCustom(ToolbarCustom.lefticon, resources.getString(R.string.contact_us), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
                activityContactUsBinding.toolbar=toolbarCustom

                activityContactUsBinding.user= RegistrationModel("User")

                activityContactUsBinding.activity=this

                left_Icon.onClick { onBackPressed() }

                activityContactUsBinding.contact= result
                activityContactUsBinding.invalidateAll()
            }

        }
    }
    lateinit var activityContactUsBinding:ActivityContactUsBindingImpl
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        supportActionBar!!.hide()
//
//
//        val hashMap = HashMap<String, Any>()
//        hashMap["user_id"] = SessionManager.getLoginModel(this).data.user_id
//
//        ApiServices<RegistrationModel.ContactUS>().callApi(
//            Urls.GET_CONTACT_US, this, hashMap,
//            RegistrationModel.ContactUS::class.java, true, this
//        )
//
//    }

    fun submitQuery(url:String,view: View){


        if (RegistrationModel("User").checkContacyusValidatoin(view,activityContactUsBinding.user!!)) {
            val hashMap = HashMap<String, Any>()
            hashMap["user_id"] = SessionManager.getLoginModel(this).data.user_id
            hashMap["name"] = activityContactUsBinding.user!!.data.dl_name
            hashMap["email"] = activityContactUsBinding.user!!.data.email
            hashMap["phone_number"] = activityContactUsBinding.user!!.data.contact_number
            hashMap["message"] = activityContactUsBinding.user!!.data.message

            ApiServices<RegistrationModel>().callApi(
                url, this, hashMap,
                RegistrationModel::class.java, true, this
            )

        }
    }
}
