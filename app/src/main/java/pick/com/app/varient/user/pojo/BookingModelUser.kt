package pick.com.app.varient.user.pojo

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.k4kotlin.core.hide
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.varient.user.ui.activity.VehicleListingUser
import java.io.File
import java.io.Serializable

class BookingModelUser : BaseObservable(), Serializable {

    /*“profile_pic”:””
“dl_name”:””
“contact_number”:””
“upload_license_image”:””
“emil_address”:””
“dl_number”:””
“delivery_address”:””
 “user_id”:””
*/

    fun showMessage(view: View,message:String){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()

    }

    fun validation(view: View,data: Data,searchBookingModel: SearchBookingModel):Boolean {
        when {

            data.dl_name == "" -> {
                showMessage(view,view.context.getString(R.string.please_enter_name))
                return false
            }

            data.contact_number == "" -> {
                showMessage(view,view.context.getString(R.string.please_enter_mobile_number))
                return false
            }

            !data.isLisancePreview -> {
                showMessage(view,view.context.getString(R.string.please_select_driving_image))
                return false
            }


            data.email == "" -> {
                showMessage(view,view.context.getString(R.string.please_enter_email_address))
                return false
            }
            data.dl_number == "" -> {
                showMessage(view,view.context.getString(R.string.please_enter_driving_license_no))
                return false
            }
            data.delivery_address == "" && searchBookingModel.isdeliveryatdoor -> {
                showMessage(view,view.context.getString(R.string.please_enter_delivery_address))
                return false
            }


        }

        return true
    }

    var status = 0
    var description = ""
    var booking_id = ""

    var data: Data? = null


    class Data : BaseObservable(), Serializable {


        var profile_picFile = File("")
        var dl_name: String? = ""
        var contact_number: String? = ""
        var upload_license_image = File("")
        var upload_license =""
        var emil_address: String? = ""
        var dl_number: String? = ""
        var delivery_address: String? = ""
        var user_id: String? = ""

        @Bindable
        var isLisancePreview: Boolean = false
            set(value) {
                if (field != value) {

                    field = value
                    notifyPropertyChanged(BR.isLisancePreview)

                }
            }
companion object {

    @BindingAdapter("manageLayoutVisibility")
    @JvmStatic
    fun manageLayoutVisibility(view: View,data: Data){
       if(!VehicleListingUser.searchmodel.isdeliveryatdoor)
           view.hide()

}
}

        var email: String? = null
        var is_social: String? = null
        var password: String? = null
        var device_type: String? = null
        var device_token: String? = null
        var profile_pic: String? = null
        var dl_image: String? = null
        var term_condition: String? = null
        var user_type: String? = null
        var login_type: String? = null
        var social_id: String? = null
        var otp_number: String? = null
        var is_active: String? = null
        var country_code: String? = null
        var detail_id: String? = null
        var deliver_user_door: String? = null
        var charges_kilometer: String? = null
        var pick_location_latitude: String? = null
        var pick_location_longitude: String? = null
        var pick_location_address: String? = null
        var bank_name: String? = null
        var beneficiary_name: String? = null
        var bank_address_branch: String? = null
        var account_number: String? = null
        var iban_number: String? = null
        var upload_url: String? = null
    }

}
