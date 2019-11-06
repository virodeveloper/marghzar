package pick.com.app.varient.user.pojo

import android.graphics.Color
import android.graphics.PorterDuff
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.*
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.k4kotlin.core.*
import com.tuyenmonkey.textdecorator.TextDecorator
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener
import org.json.JSONObject
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.uitility.custom.CustomEditText
import java.io.File
import java.io.Serializable

class RegistrationModel : Serializable , BaseObservable {


    //https://medium.com/@fabioCollini/android-data-binding-f9f9d3afc761

    /*"status":"1","message":"Success","description":"User successfully created.","user_id":14,"otp":"12345"
   */
    var status = 0
    lateinit var drop_information: Any

    var data = Data()
    var message: String = ""
    var purpuse: String = ""
    var description: String = ""
    var user_id: String = ""
    val bank_name : String= ""

    var address: String = ""
    var email: String = ""



    class ContactUS{

        var company_name: String = ""
        var description: String = ""
        var address: String = ""
        var status=0
        var email: String = ""
        var contact_number: String = ""

    }

    var contact_number: String = ""
    var country_code: String = "966"
    var is_active: Int = -1


    fun addcountValidation(view: View,model: Data):Boolean{

        when{
            model.account_number==""->{
                Validation(view,"","",view.context.getString(R.string.please_enter_account_no))
                return false

            }  model.bank_name==""->{
            Validation(view,"","",view.context.getString(R.string.please_enter_bank_name))
            return false
        }  model.iban_number==""->{
            Validation(view,"","",view.context.getString(R.string.please_enter_ibn_no))
            return false
        } model.beneficiary_name==""->{
            Validation(view,"","",view.context.getString(R.string.please_enter_beneficiary_name))
            return false
        }


        }
        return true
    }

    class Data : Serializable , BaseObservable() {
        var isLisancePreview: Boolean = false


        var otp: String = ""
        var is_different_city=0
        /*Usinf from API*/



        @Bindable
        var isdifferentcity=true
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.isdifferentcity)

                }
            }

        var is_active: Int = -1
        var drivingLisenceNo = ""
        var drivingLisencePic = File("")
        var profilepic = File("")
        var user_id: String = ""
        var new_password: String = ""
        var confirm_password: String = ""
        var country_code: String = "966"
        var email: String = ""
        var message: String = ""
        var dl_name: String = ""
     @Bindable   var contact_number: String = ""
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.contact_number)

                }
            }
        var dl_number: Any = ""
        var password: String = ""
        var profile_pic: Any = ""
        var dl_image: Any = ""
        var user_type: String = "oi"
        var login_type: String = ""
        var otp_number: String = ""
        var detail_id: String = ""
        var deliver_user_door: Int = 1
        var wallet_amount = "";

        var bank_name: String = ""

        var upload_url: String = ""
        var locations= ArrayList<Locations>()


        var owner_type: String = "oi"
        var term_condition: Int = 0
        var is_social: Int = 0
        var social_type: String = ""
        var charges_kilometer: String = ""
        var pick_location_latitude: Double = 0.0
        var pick_location_longitude: Double = 0.0
        var pick_location_address: String = ""
        var owner_city: String = ""

        var beneficiary_name: String = ""
        var bank_number: String = ""
        var bank_address_branch: String = ""
        var account_number: String = ""
        @Bindable  var iban_number: String = ""
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.iban_number)

                }
            }


        var device_type: String = ""
        var device_token: String = ""
        var social_id: String = ""
        var social_pic: String = ""
        var user_pic = upload_url + "profile_pic/" + profile_pic




        @Bindable  var isPerKilomerVisible :Int?=0
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.isPerKilomerVisible)

                }
            }


        class Locations : Serializable , BaseObservable {

            var position: Int = 0

            var adapter = UniverSalBindAdapter
            var type = "edit"


            @Bindable
            var city = ""
                set(value) {
                    if (field!=value){

                        field=value
                        notifyPropertyChanged(BR.city)

                    }
                }
            @Bindable
            var city_id = ""
                set(value) {
                    if (field!=value){

                        field=value
                        notifyPropertyChanged(BR.city_id)

                    }
                }
            var data=ArrayList<Cities>()

            class Cities :Serializable{

                override fun toString(): String {
                    return city
                }

                var city_id="";
                var city="";
            }

            constructor(type: String,cities:ArrayList<Cities>) {
                this.type = type
                this.data = cities
            }

            constructor()

            var drop_id: String = ""
          @Bindable  var drop_location_address :String?=""
            set(value) {
                if (field!=value){

                    field=value
                    notifyPropertyChanged(BR.drop_location_address)

                }
            }







            @Bindable  var drop_pin_code :String?=""
                set(value) {
                    if (field!=value){

                        field=value!!.replace("\'","")
                        notifyPropertyChanged(BR.drop_pin_code)

                    }
                }



            var detail_id: String = ""
            var user_id: String = ""


            fun getUpdatesAddress(add: String): String {

                return add.replace("\"", "")
            }

            fun getUserType(add: String): String {

                if (add=="oi"){

                    return  "Individual"
                }else {
                    return "Company"
                }


            }

            fun isOwner(user_type: String): Boolean {

                if (user_type=="u"){

                    return  false
                }else {
                    return true
                }


            }


            companion object {

                var isalreadyClickformap=false




                @JvmStatic
                @BindingAdapter("setValue")
                fun setValue(editText: CustomEditText, model: Locations) {


                    if (model.type != "registration") {
                        editText.setBorderColor(Color.parseColor("#353535"))
                        editText.setHintTextColor(Color.parseColor("#71353535"))
                        editText.setTextColor(Color.parseColor("#353535"))


                        try {
                            // Get the cursor resource id
                            var field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                            field.isAccessible = true
                            val drawableResId = field.getInt(editText)

                            // Get the editor
                            field = TextView::class.java.getDeclaredField("mEditor")
                            field.isAccessible = true
                            val editor = field.get(editText)

                            // Get the drawable and set a color filter
                            val drawable = ContextCompat.getDrawable(editText.getContext(), drawableResId)
                            drawable!!.setColorFilter(Color.parseColor("#353535"), PorterDuff.Mode.SRC_IN)
                            val drawables = arrayOf(drawable, drawable)

                            // Set the drawables
                            field = editor.javaClass.getDeclaredField("mCursorDrawable")
                            field.isAccessible = true
                            field.set(editor, drawables)
                        } catch (ignored: Exception) {
                        }


                    }

                    val filter = InputFilter { source, start, end, dest, dstart, dend ->
                        if (source != null && "\"\\\"".contains("" + source)) {
                            ""
                        } else null
                    }
                    editText.setFilters(arrayOf(filter))

                    var textWatcher =
                        editText.addTextWatcher(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->



                            when {
                                editText.hint.toString().toLowerCase().contains("address") -> {
                                 //   model.drop_location_address = charSequence.toString().replace("\"", "")

                                }
                                editText.hint.toString().toLowerCase().contains("pincode") -> {
                                   // model.drop_pin_code = charSequence.toString().replace("\"", "")


                                }

                            }


                        })

                }

                @JvmStatic
                @BindingAdapter("addLocation")
                fun addLocation(view: ImageView, model: Locations) {

                    if (model.type != "registration") view.setColorFilter(Color.BLACK)

                    if (addapter.arrraylist.size - 1 == model.position) {
                        view.show()
                    } else {
                        view.invisible()
                    }
                    view.onClick {

                        CommonActivity.activity.hideKeyboard()
                        if (addapter.arrraylist.size<20)
                        addapter.addWithNotify(RegistrationModel.Data.Locations(model.type,model.data))
                        else
                            view.context.toast(view.context.getString(R.string.drop_location_max_twenty))

                    }

                }



                @JvmStatic
                @BindingAdapter("ShowForOwner")
                fun ShowForOwner(view: View, model: RegistrationModel.Data) {

                    if (model.user_type=="u"){

                        view.hide()

                    }else {
                        view.show()
                    }

                }

                @JvmStatic
                @BindingAdapter("setSpinerData")
                fun setSpinerData(view: TextView, model: Locations) {
                    if (model.type != "registration") view.backgroundTintList=(ContextCompat.getColorStateList(view.context, R.color.black));
                    var adpatermate= UniverSalBindAdapter(R.layout.custom_list_city)
                    adpatermate.addAll(model.data as ArrayList<Any>)
                    if(model.city == ""){
                        model.city = view.context.getString(R.string.select_city)
                    }
                    if (model.type != "registration")
                        view.setTextColor(Color.parseColor("#353535"))
                    else
                        view.setTextColor(Color.WHITE);

                    view.setTextSize(12f)
                    view.onClick {
                        MaterialDialog(view.context).show {
                            title(R.string.select_city)
                            customListAdapter( UniverSalBindAdapter(R.layout.custom_list_city,object: UniverSalBindAdapter.ItemAdapterListener{
                                override fun onItemSelected(modssel: Any) {
                                    dismiss()
                                    var item=modssel as Cities
                                    model.city= item.city
                                    model.city_id=item.city_id
                                }


                            }).apply { addAll(model.data as ArrayList<Any>)})


                        }
                    }




                }


  @JvmStatic
                @BindingAdapter("manageLayoutbackground")
                fun manageLayoutbackground(view: LinearLayout, model: Locations) {
                    if (model.type != "registration") view.setBackgroundResource(R.drawable.rect_view_editprofile)





                }




                @JvmStatic
                @BindingAdapter("locationss")
                fun locationss(view: View, model: Locations) {

                    /*  model.drop_location_address.replace("\"","")
                      view.setText(model.drop_location_address)*/

                }


                @JvmStatic
                @BindingAdapter("removeLocation")
                fun removeLocation(view: ImageView, model: Locations) {
                    if (model.type != "registration") view.setColorFilter(Color.BLACK)


                    if (model.position == addapter.arrraylist.size - 1) {
                        view.show()
                    } else {
                        view.show()
                    }

                    if (addapter.arrraylist.size == 1) {
                        view.invisible()
                    }

                    view.onClick {
                        CommonActivity.activity.hideKeyboard()
                        addapter.removeItemWithnotify(model)
                    }

                }
            }


        }

    }

    class RegistrationResponse : Serializable {
        constructor()

        var status: String = ""
        var message: String = ""
        var description: String = ""
        var user_id: String = ""
        var otp: String = ""
        var contact_number: String = ""


    }


    var arrayList = ArrayList<RegistrationModel.Data.Locations>()


    fun checkMultipleLocation(view: View, arrayList: ArrayList<Data.Locations>): Boolean {

        for (item in arrayList) {
            if (item.drop_location_address == "") {


                Validation(view, "address", "", view.context.getString(R.string.please_enter_drop_location))

                return false
            } else if (item.drop_pin_code == "") {
                Validation(view, "address", "", view.context.getString(R.string.please_enter_drop_pindcode))

                return false
            }else if (item.city == "Select city") {
                Validation(view, "address", "", view.context.getString(R.string.empty_city_message))

                return false
            }
        }

        return true
    }

    fun editProfileHashMap(registratioi: RegistrationModel): JSONObject {

        return JSONObject().apply {
            put("user_id", registratioi.data.user_id)
            put("dl_name", registratioi.data.dl_name)
            put("email", registratioi.data.email)
            put("profile_pic", registratioi.data.profilepic)
        }


    }

    var userType: String = "User"
    fun checkvalidation(view: View, registratioi: RegistrationModel): Boolean {

        when {
            registratioi.data.dl_name == "" -> {
                Validation(view, registratioi.data.dl_name, "", view.context.getString(R.string.please_enter_name))
                return false
            }
            registratioi.data.email == "" || !registratioi.data.email.isEmail() -> {
                Validation(
                    view,
                    registratioi.data.email,
                    "email",
                    view.context.getString(R.string.please_enter_email_address)
                )
                return false
            }
            registratioi.data.contact_number == "" -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

            registratioi.data.contact_number.length <8 -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

            registratioi.data.charges_kilometer == "" && registratioi.userType == "Owner" && registratioi.data.deliver_user_door==1-> {

                Validation(
                    view,
                    registratioi.data.charges_kilometer,
                    "",
                    view.context.getString(R.string.please_enter_one_km_charge)
                )
                return false
            }

            registratioi.data.pick_location_address == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.charges_kilometer,
                    "",
                    view.context.getString(R.string.please_enter_pick_location)
                )
                return false
            }

            registratioi.data.beneficiary_name == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.beneficiary_name,
                    "",
                    view.context.getString(R.string.please_enter_beneficiary_name)
                )
                return false
            }

            registratioi.data.bank_number == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.beneficiary_name,
                    "",
                    view.context.getString(R.string.please_enter_bank_name)
                )
                return false
            }

            registratioi.data.bank_address_branch == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.beneficiary_name,
                    "",
                    view.context.getString(R.string.please_enter_bank_address)
                )
                return false
            }

            registratioi.data.account_number == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.account_number,
                    "",
                    view.context.getString(R.string.please_enter_account_no)
                )
                return false
            }
            registratioi.data.iban_number == "" && registratioi.userType == "Owner" -> {

                Validation(
                    view,
                    registratioi.data.iban_number,
                    "",
                    view.context.getString(R.string.please_enter_ibn_no)
                )
                return false
            }

            registratioi.data.drivingLisenceNo == "" && registratioi.userType == "User" -> {

                Validation(
                    view,
                    registratioi.data.drivingLisenceNo,
                    "",
                    view.context.getString(R.string.please_enter_driving_license_no)
                )
                return false
            }

            registratioi.data.drivingLisencePic.path == "" && registratioi.userType == "User" -> {

                Validation(
                    view,
                    registratioi.data.drivingLisencePic.path,
                    "",
                    view.context.getString(R.string.Pleaselectdrivinglicensepic)
                )
                return false
            }


            registratioi.data.password == "" -> {

                Validation(view, registratioi.data.password, "", view.context.getString(R.string.please_enter_password))
                return false
            }

            registratioi.data.password.length <6 || registratioi.data.password.length >20-> {

                Validation(view, registratioi.data.password, "", view.context.getString(R.string.password_length_six_to_twenty))
                return false
            }

            registratioi.data.term_condition == 0 -> {

                Validation(view, "", "", view.context.getString(R.string.please_accept_term_and_condition))
                return false
            }


            registratioi.data.profilepic.path == ""  -> {

                Validation(
                    view,
                    registratioi.data.profilepic.path,
                    "",
                    view.context.getString(R.string.pleaseselectprofilepic)
                )

                return false
            }


        }


        return true

    }
    fun checkContacyusValidatoin(view: View, registratioi: RegistrationModel): Boolean {

        when {
            registratioi.data.dl_name == "" -> {
                Validation(view, registratioi.data.dl_name, "", view.context.getString(R.string.please_enter_name))
                return false
            }
            registratioi.data.email == "" || !registratioi.data.email.isEmail() -> {
                Validation(
                    view,
                    registratioi.data.email,
                    "email",
                    view.context.getString(R.string.please_enter_email_address)
                )
                return false
            }
            registratioi.data.contact_number == "" -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

            registratioi.data.contact_number.length <8 -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

           

            registratioi.data.message == ""  -> {

                Validation(
                    view,
                    registratioi.data.message,
                    "",
                    view.context.getString(R.string.PleaseEnterMessage)
                )

                return false
            }


        }


        return true

    }



    fun checkEditOwnervalidation(view: View, registratioi: RegistrationModel): Boolean {

        when {
            registratioi.data.dl_name == "" -> {
                Validation(view, registratioi.data.dl_name, "", view.context.getString(R.string.please_enter_name))
                return false
            }
            registratioi.data.email == "" || !registratioi.data.email.isEmail() -> {
                Validation(
                    view,
                    registratioi.data.email,
                    "email",
                    view.context.getString(R.string.please_enter_email_address)
                )
                return false
            }
            registratioi.data.contact_number == "" -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

            registratioi.data.contact_number.length <8 ||  registratioi.data.contact_number.length >15 -> {
                Validation(
                    view,
                    registratioi.data.contact_number,
                    "number",
                    view.context.getString(R.string.please_enter_mobile_number)
                )
                return false
            }

            registratioi.data.charges_kilometer == ""  && registratioi.data.deliver_user_door ==1  -> {

                Validation(
                    view,
                    registratioi.data.charges_kilometer,
                    "",
                    view.context.getString(R.string.please_enter_one_km_charge)
                )
                return false
            }

            registratioi.data.pick_location_address == ""  -> {

                Validation(
                    view,
                    registratioi.data.charges_kilometer,
                    "",
                    view.context.getString(R.string.please_enter_pick_location)
                )
                return false
            }


        }


        return true

    }


    fun checkEditProfilevalidation(view: View, registratioi: RegistrationModel): Boolean {

        when {
            registratioi.data.dl_name == "" -> {
                Validation(view, registratioi.data.dl_name, "", view.context.getString(R.string.please_enter_name))
                return false
            }
            registratioi.data.email == "" || !registratioi.data.email.isEmail() -> {
                Validation(
                    view,
                    registratioi.data.email,
                    "email",
                    view.context.getString(R.string.please_enter_email_address)
                )
                return false
            }



        }


        return true

    }


    fun checkSetPassWordvalidation(view: View, registratioi: RegistrationModel, type: String): Boolean {

        when {
            registratioi.data.password == "" && type == "changepassword" -> {
                Validation(
                    view,
                    registratioi.data.password,
                    "",
                    view.context.getString(R.string.please_enter_old_password)
                )
                return false
            }

            registratioi.data.new_password == "" -> {
                Validation(
                    view,
                    registratioi.data.new_password,
                    "",
                    view.context.getString(R.string.please_enter_password)
                )
                return false
            }

            registratioi.data.new_password.length <6 || registratioi.data.new_password.length >20 -> {

                Validation(view, registratioi.data.password, "", view.context.getString(R.string.password_length_six_to_twenty))
                return false
            }

            registratioi.data.confirm_password == "" -> {
                Validation(
                    view,
                    registratioi.data.confirm_password,
                    "",
                    view.context.getString(R.string.please_enter_confirm_password)
                )
                return false
            }
            registratioi.data.new_password != registratioi.data.confirm_password -> {
                Validation(
                    view,
                    registratioi.data.new_password,
                    "",
                    view.context.getString(R.string.newconfirm)
                )
                return false
            }
        }
        return true

    }


    fun checkOTPValidation(view: View, registratioi: RegistrationModel): Boolean {
        when {
            registratioi.data.otp_number == "" -> {
                Validation(
                    view,
                    registratioi.data.otp_number,
                    "",
                    view.context.getString(R.string.please_enter_otp_code)
                )
                return false
            }
        }
        return true
    }


    fun Validation(view: View, value: String, type: String, message: String) {

        when (type) {

            "email" -> {

                if (value.isEmpty())
                    Snackbar.make(
                        view, view.context.getString(R.string.please_enter_email_address), Snackbar.LENGTH_SHORT
                    ).show()
                else
                    if (!value.isEmail())
                        Snackbar.make(
                            view, view.context.getString(R.string.invalid_email), Snackbar.LENGTH_SHORT
                        ).show()

            }

            "number" -> {
                if (value.isEmpty()) {
                    Snackbar.make(
                        view, view.context.getString(R.string.please_enter_mobile_number), Snackbar.LENGTH_SHORT
                    ).show()
                } else if (value.length < 8||value.length > 13) {
                    Snackbar.make(
                        view, view.context.getString(R.string.please_enter_vaild_mobile_no), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {

                Snackbar.make(
                    view,
                    message, Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    var text = ObservableField<String>()

    constructor(userType: String) {
        this.userType = userType

    }

    constructor() {
    }


    fun getUpdateOwnerJSONOBJECT(registratioi: RegistrationModel): JSONObject {


        /*name,email,contact_number,owner_type(u,oc,oi),password,deliver_user_door(0,1),charges_kilometer,pick_location_latitude,pick_location_longitude,pick_location_address,bank_name,beneficiary_name,bank_address_branch,account_number,iban_number,drop_information(drop_location_address,drop_pin_code)*/
        return JSONObject().apply {
            put("dl_name", registratioi.data.dl_name)
            put("user_id", registratioi.data.user_id)
            put("email", registratioi.data.email)
            put("contact_number", registratioi.data.contact_number)
            put("country_code", registratioi.data.country_code)

            put("owner_type", registratioi.data.user_type)
            put("is_different_city", if(registratioi.data.isdifferentcity) 1 else 2)

            put("deliver_user_door", registratioi.data.deliver_user_door)

            put("charges_kilometer", registratioi.data.charges_kilometer)
            put("pick_location_latitude", registratioi.data.pick_location_latitude)
            put("pick_location_longitude", registratioi.data.pick_location_longitude)

            put("pick_location_address", registratioi.data.pick_location_address)

            put("bank_name", registratioi.data.bank_number)
            put("beneficiary_name", registratioi.data.beneficiary_name)
            put("bank_address_branch", registratioi.data.bank_address_branch)
            put("account_number", registratioi.data.account_number)
            put("iban_number", registratioi.data.iban_number)
            put("owner_city", registratioi.data.owner_city)

            put("device_type", registratioi.data.device_type)
            put("login_type", registratioi.data.login_type)
            put("device_token", registratioi.data.device_token)
            put("social_id", registratioi.data.social_id)
            put("profile_pic", registratioi.data.profilepic)
            put("social_pic", registratioi.data.social_pic)
            var multidrop = ArrayList<RegistrationModel.Data.Locations>()

            multidrop =
                addapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>

            var arrayaddress = ""
            var arraypincode = ""
            var arraycities = ""
            /* for (item in multidrop){
                 var jsonojetc=JSONObject()

                 jsonojetc.put("drop_location_address",item.drop_location_address)
                 jsonojetc.put("drop_pin_code",item.drop_pin_code)
                 arrayaddress.put(jsonojetc)
             }*/

            for (i in 0..multidrop.size - 1) {

                if (i==0){
                    arrayaddress=multidrop[i].drop_location_address!!.trim()
                    arraypincode=multidrop[i].drop_pin_code!!.trim()
                    arraycities=multidrop[i].city_id!!.trim()
                }else{
                    arrayaddress=arrayaddress+"||"+multidrop[i].drop_location_address!!.trim()
                    arraypincode=arraypincode+"||"+multidrop[i].drop_pin_code!!.trim()
                    arraycities=arraycities+"||"+multidrop[i].city_id!!.trim()

                }

            }


            put("drop_location_address", arrayaddress)
            put("drop_pin_code", arraypincode)
            put("city_id", arraycities)
        }


    }


    fun getUserRegesterJSONOBJECT(registratioi: RegistrationModel): JSONObject {


        /*name,email,contact_number,owner_type(u,oc,oi),password,deliver_user_door(0,1),charges_kilometer,pick_location_latitude,pick_location_longitude,pick_location_address,bank_name,beneficiary_name,bank_address_branch,account_number,iban_number,drop_information(drop_location_address,drop_pin_code)*/
        return JSONObject().apply {
            put("name", registratioi.data.dl_name)
            put("wallet_amount", registratioi.data.wallet_amount)
            put("email", registratioi.data.email)
            put("contact_number", registratioi.data.contact_number)
            put("country_code", registratioi.data.country_code)

            put("owner_type", registratioi.data.user_type)
            put("password", registratioi.data.password)
            put("deliver_user_door", registratioi.data.deliver_user_door)
            put("is_different_city", if(registratioi.data.isdifferentcity) 1 else 2)

            put("charges_kilometer", registratioi.data.charges_kilometer)
            put("pick_location_latitude", registratioi.data.pick_location_latitude)
            put("pick_location_longitude", registratioi.data.pick_location_longitude)

            put("pick_location_address", registratioi.data.pick_location_address)

            put("bank_name", registratioi.data.bank_number)
            put("beneficiary_name", registratioi.data.beneficiary_name)
            put("bank_address_branch", registratioi.data.bank_address_branch)
            put("account_number", registratioi.data.account_number)
            put("iban_number", registratioi.data.iban_number)
            put("owner_city", registratioi.data.owner_city)
            put("term_condition", "1")

            put("login_type", registratioi.data.login_type)
            put("device_token", registratioi.data.device_token)
            put("social_id", registratioi.data.social_id)
            put("profile_pic", registratioi.data.profilepic)
            put("social_type", registratioi.data.social_type)
            put("social_pic", registratioi.data.social_pic)
            put("login_type", registratioi.data.login_type)
            put("device_type", "android")
            put("user_type", "o")


            var multidrop = ArrayList<RegistrationModel.Data.Locations>()

            multidrop =
                addapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>

            var arrayaddress = ""
            var arraypincode = ""
            var arraycities = ""
            /* for (item in multidrop){
                 var jsonojetc=JSONObject()

                 jsonojetc.put("drop_location_address",item.drop_location_address)
                 jsonojetc.put("drop_pin_code",item.drop_pin_code)
                 arrayaddress.put(jsonojetc)
             }*/

            for (i in 0..multidrop.size - 1) {

                if (i==0){
                    arrayaddress=multidrop[i].drop_location_address!!
                    arraypincode=multidrop[i].drop_pin_code!!
                    arraycities=multidrop[i].city_id!!
                }else{
                    arrayaddress=arrayaddress+"||"+multidrop[i].drop_location_address!!.trim()
                    arraypincode=arraypincode+"||"+multidrop[i].drop_pin_code!!.trim()
                    arraycities=arraycities+"||"+multidrop[i].city_id!!.trim()
                }

            }


            put("drop_location_address", arrayaddress)
            put("drop_pin_code", arraypincode)
            put("city_id", arraycities)
        }


    }


    fun getHAshap(registratioi: RegistrationModel): JSONObject {

        return JSONObject().apply {
            put("dl_name", registratioi.data.dl_name)
            put("email", registratioi.data.email)
            put("contact_number", registratioi.data.contact_number)
            put("country_code", registratioi.data.country_code)

            put("dl_number", registratioi.data.drivingLisenceNo)
            put("wallet_amount", registratioi.data.wallet_amount)
            put("dl_image", registratioi.data.drivingLisencePic)
            put("profile_pic", registratioi.data.profilepic)

            put("term_condition", registratioi.data.term_condition)
            put("is_social", registratioi.data.is_social)
            put("password", registratioi.data.password)

            put("social_id", registratioi.data.social_id)

            put("social_pic", registratioi.data.social_pic)
            put("login_type", registratioi.data.login_type)
            put("user_type", "o")
        }


    }

    fun changePassword(registratioi: RegistrationModel): HashMap<String, Any> {

        return HashMap<String, Any>().apply {
            put("user_id", registratioi.data.user_id)
            put("old_password", registratioi.data.password)
            put("new_password", registratioi.data.new_password)
        }
    }

    fun forgotPassword(registratioi: RegistrationModel): HashMap<String, Any> {


        return HashMap<String, Any>().apply {
            put("user_id", registratioi.data.user_id)
            put("password", registratioi.data.new_password)
        }
    }

    fun getProfile(registratioi: RegistrationModel): HashMap<String, Any> {



        return HashMap<String, Any>().apply {
            put("user_id", registratioi.data.user_id)

        }
    }

    fun getHAshapOwner(registratioi: RegistrationModel): HashMap<String, Any> {


        /*dl_name:naseem akhtar
email:naseem@endivesoftware.com
contact_number:2525252525
dl_number:RJ14
term_condition:1
password:endive@123
//profile_pic:
//dl_image:
is_social:0*/
        return HashMap<String, Any>().apply {
            put("name", registratioi.data.dl_name)
            put("wallet_amount", registratioi.data.wallet_amount)
            put("email", registratioi.data.email)
            put("contact_number", registratioi.data.contact_number)
            put("country_code", registratioi.data.country_code)
            put("owner_type", registratioi.data.user_type)
            put("password", registratioi.data.password)
            put("deliver_user_door", registratioi.data.deliver_user_door)

            put("charges_kilometer", registratioi.data.charges_kilometer)
            put("pick_location_latitude", registratioi.data.pick_location_latitude)
            put("pick_location_longitude", registratioi.data.pick_location_longitude)

            put("pick_location_address", registratioi.data.pick_location_address)

            put("bank_name", registratioi.data.bank_number)
            put("beneficiary_name", registratioi.data.beneficiary_name)
            put("bank_address_branch", registratioi.data.bank_address_branch)
            put("account_number", registratioi.data.account_number)
            put("iban_number", registratioi.data.iban_number)

            put("device_type", "android")
            put("device_token", registratioi.data.device_token)
            put("social_id", registratioi.data.social_id)
            put("social_pic", registratioi.data.social_pic)


            var multidrop = ArrayList<RegistrationModel.Data.Locations>()

            multidrop =
                UniverSalBindAdapter.univerSalBindAdapter.arrraylist as ArrayList<RegistrationModel.Data.Locations>


            put("drop_information", multidrop)


        }

    }


    companion object {



        lateinit var  addapter : UniverSalBindAdapter


        @JvmStatic
        @BindingAdapter("android:visibility")
        fun managervis(view: View, model: RegistrationModel) {


            if (model.data.deliver_user_door==0){
                view.hide()
            }

        }




        @JvmStatic
        @BindingAdapter("setPrefix")
        fun setPrefix(view: CustomEditText, model: RegistrationModel) {
            val locale = view.getTextLocale().getLanguage()
            if (locale.endsWith("ar")){
                view.prefix="+966 - "
            }

        }


            @JvmStatic
        @BindingAdapter("onUserCheckedChange")
        fun setUserCheckedChangeListener(view: CompoundButton, model: RegistrationModel) {
            view.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->

                if (b)
                    model.data.term_condition = 1
                else
                    model.data.term_condition = 0

            }
        }

        @JvmStatic
        @BindingAdapter("setLinkSetting")
        fun setLinkSetting(view: TextView, model: RegistrationModel) {

            TextDecorator
                .decorate(view, view.context.getString(R.string.i_accept_the_terms_amp_contition_and_privacy_policy))

                .underline(
                    view.context.getString(R.string.termscondition),
                    view.context.getString(R.string.privacypolocy)
                )

                .makeTextClickable(
                    OnTextClickListener { view, text ->
                       // Toast.makeText(view!!.context, text, Toast.LENGTH_SHORT).show();
                    },
                    true,
                    view.context.getString(R.string.termscondition),
                    view.context.getString(R.string.privacypolocy)
                )

                .build()

        }

        @JvmStatic
        @BindingAdapter("setValue")
        fun setValue(editText: EditText, model: RegistrationModel) {

            editText.addTextWatcher(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->


                when {

                    editText.hint.toString().contains(editText.context.getString(R.string.beneficiary_name)) -> {
                        model.data.beneficiary_name = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.otp_code)) -> {
                        model.data.otp_number = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.bank_number)) -> {
                        model.data.bank_number = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.bank_address_branch)) -> {
                        model.data.bank_address_branch = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.account_number)) -> {
                        //model.data.account_number = charSequence.toString().trim()
                    }
                    editText.hint.toString().contains(editText.context.getString(R.string.iban_number)) -> {
                        model.data.iban_number = charSequence.toString().trim()
                    }
                    editText.hint.toString().contains(editText.context.getString(R.string.name)) -> {
                        model.data.dl_name = charSequence.toString().trim()

                    }
                    editText.hint.toString().toLowerCase().contains(editText.context.getString(R.string.email)) -> {
                        model.data.email = charSequence.toString().trim()

                        /* if (!model.data.email.isEmail()){

                             editText.error="Invalid email"

                         }*/

                    }
                    editText.hint.toString().toLowerCase().contains(editText.context.getString(R.string.mobile)) -> {
                        model.data.contact_number = charSequence.toString().trim()
                    }
                    editText.hint.toString().toLowerCase().contains(editText.context.getString(R.string.number)) -> {
                        model.data.drivingLisenceNo = charSequence.toString().trim()
                    }
                    editText.hint.toString().contains(editText.context.getString(R.string.confirm_password)) -> {
                        model.data.confirm_password = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.new_password)) -> {
                        model.data.new_password = charSequence.toString().trim()
                    }

                    editText.hint.toString().toLowerCase().contains(editText.context.getString(R.string.password)) -> {
                        model.data.password = charSequence.toString().trim()
                    }


                    editText.hint.toString().contains(editText.context.getString(R.string.amount)) -> {
                        model.data.charges_kilometer = charSequence.toString().trim()
                    }

                    editText.hint.toString().contains(editText.context.getString(R.string.pick_address)) -> {
                        model.data.pick_location_address = charSequence.toString().trim()
                    }


                }


            })

        }

        @JvmStatic
        @BindingAdapter("setValue")
        fun setValue(editText: EditText, model: RegistrationModel.RegistrationResponse) {

            editText.addTextWatcher(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->


                when {


                    editText.hint.toString().contains(editText.context.getString(R.string.otp_code)) -> {
                        // model.data.otp = charSequence.toString().trim()
                    }


                }


            })

        }


        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(profile_image: ImageView, url: String) {

            if (url != "")
                Glide.with(profile_image.context)
                    .asBitmap()
                    .apply(
                        RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.placeholder).error(
                            R.drawable.placeholder
                        )
                    )
                    .load(url)
                    .into(profile_image)


        }


        @JvmStatic
        @BindingAdapter("loadRegisterpciImage")
        fun loadRegisterpciImage(profile_image: ImageView, model: RegistrationModel) {
            if (model.data.profile_pic == "profile_pic/" || model.data.profile_pic == "")
                model.data.profile_pic = model.data.social_pic
            if (model.data.profile_pic != "")
                Glide.with(profile_image.context)
                    .asBitmap()
                    .apply(
                        RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.placeholder).error(
                            R.drawable.placeholder
                        )
                    )
                    .load(model.data.profile_pic)
                    .into(profile_image)


        }


        @JvmStatic
        @InverseBindingAdapter(attribute = "setRadiovalue")
        fun getRadiovalue( model: RegistrationModel):RegistrationModel {
Log.e("","")

            return model
        }


        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setRadiovalue(radioGroup: RadioButton, model: RegistrationModel) {
            radioGroup.onClick {

                when (radioGroup.text) {

                    this.context.getString(pick.com.app.R.string.company) -> {

                        model.data.user_type = "oc"
                    }
                    this.context.getString(pick.com.app.R.string.individual) -> {

                        model.data.user_type = "oi"
                    }
                    this.context.getString(pick.com.app.R.string.yes) -> {
                        model.data.isPerKilomerVisible=View.VISIBLE
                        model.data.deliver_user_door = 1
                    }

                    this.context.getString(pick.com.app.R.string.No) -> {



                        model.data.isPerKilomerVisible=View.GONE
                        model.data.deliver_user_door = 0
                    }


                }

            }


        }


    }
}
