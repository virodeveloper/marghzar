package pick.com.app.varient.user.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.edit_profile_user.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.EditProfileUserBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.File

class EditProfileActivity : CommonActivity(), onResponse {

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        if (methodtype == Urls.UPDATE_PROFILE_URL) {

            var result = result as RegistrationModel


            if (result.message.toLowerCase() == "success") {
                SessionManager.setLoginModel(result, this)
                onBackPressed()
            }


            showMessage(result.message, result.description)

        }


    }

    override fun onError(error: String?) {
        showMessage(
            "Unsuccess",
            if (error.toString() == "connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString()
        )

    }

    lateinit var binding: EditProfileUserBinding


    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        binding.user!!.data.profilepic = File(images.path)
        loadImage(profile_image, images.path)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbarCustom =
            ToolbarCustom(ToolbarCustom.lefticon, getString(R.string.edit_profile), ToolbarCustom.NoIcon,ToolbarCustom.NoIcon)
        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_user)
        binding.activity = this
        binding.user = SessionManager.getLoginModel(this)
        binding.toolbar = toolbarCustom
        binding.invalidateAll()



        left_Icon.onClick { onBackPressed() }

    }


    fun submitOnclick(view: View) {

        if (binding.user!!.checkEditProfilevalidation(view, binding.user!!)) {
            ApiServices<RegistrationModel>().callApiinObject(
                Urls.UPDATE_PROFILE_URL,
                this, binding.user!!.editProfileHashMap(binding.user!!)
                , RegistrationModel::class.java, true, this
            )
        }
    }

    fun showPicker(view: View) {
        showImagePicker("Profile")
    }

}
