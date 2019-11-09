package pick.com.app.varient.user.ui.activity


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.fill_booking_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.FillBookingActivityBinding
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.user.pojo.*
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.File

class FillBookingActivity : BaseActivity() {

    private lateinit var booking_id : String
    private lateinit var payable_amount : String




    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)

        when (methodtype) {

            Urls.GET_PROFILE -> {
                fillBookingActivityBinding.model = (result as BookingModelUser).data
                loadImage(
                    profile_image,
                    fillBookingActivityBinding.model!!.upload_url + "profile_pic/" + fillBookingActivityBinding.model!!.profile_pic
                )
                fillBookingActivityBinding.model!!.isLisancePreview = true

                var lisenceimageurl=  fillBookingActivityBinding.model!!.upload_url + "dl_image/"+fillBookingActivityBinding.model!!.dl_image
                fillBookingActivityBinding.model!!.upload_license = File(lisenceimageurl).name
                fillBookingActivityBinding.invalidateAll()

                loadImage(lisence_image, lisenceimageurl)

                fillBookingActivityBinding.invalidateAll()
            }

            Urls.VEHICLE_BOOKING -> {

                var result = result as BookingModelUser
                if (result.status == 1) {
                    booking_id = result.booking_id
                    changeStatus("9", result.booking_id)
                  // startActivity(Intent(this, WalletActivity::class.java).putExtra("booking_id", result.booking_id))
                   // showMessageWithError(message = result.description, isfinish = true)
                } else {
                    onError(result.description)
                }
            }

            Urls.CHANGE_BOKING_STATUS_USER -> {


                var result = result as BookingModel
                if (result.status == 1) {
                     startActivity(Intent(this, WalletActivity::class.java).putExtra("booking_id", booking_id).
                     putExtra("amount", payable_amount).putExtra("payment_type","i"))
                    // showMessageWithError(message = result.description, isfinish = true)
                } else {
                    onError(result.description)
                }
            }

            Urls.USER_WALLET->{
                val sharedPreferences: SharedPreferences = this.getSharedPreferences("Mupre",Context.MODE_PRIVATE)
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()

                var model= result as WalletModel
                var am=model.wallet_amount
                editor.putString("wallet",am)
                editor.apply()
            }

        }
    }

    fun changeStatus(status : String, booking_id : String) {

        val hashmap = java.util.HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = booking_id
        hashmap["booking_status"] = status

        ApiServices<BookingModel>().callApi(Urls.CHANGE_BOKING_STATUS_USER, this, hashmap, BookingModel::class.java, true, activity)
    }

    fun removePreview(view: View) {
        fillBookingActivityBinding.model!!.upload_license = ""
        fillBookingActivityBinding.model!!.upload_license_image = File("")
        fillBookingActivityBinding.model!!.isLisancePreview = false
        fillBookingActivityBinding.invalidateAll()

    }
        lateinit var fillBookingActivityBinding: FillBookingActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fillBookingActivityBinding = DataBindingUtil.setContentView(this, R.layout.fill_booking_activity)
        val toolbarCustom =
            ToolbarCustom(
                ToolbarCustom.lefticon,
                resources.getString(R.string.fill_booking),
                ToolbarCustom.NoIcon,
                ToolbarCustom.NoIcon
            )
        left_Icon.onClick { onBackPressed() }
        fillBookingActivityBinding.toolbar = toolbarCustom
        fillBookingActivityBinding.activity = this
        fillBookingActivityBinding.model = BookingModelUser.Data()

        model = intent.extras.get("model") as VehicleSearchData.Data.Vehicles
        searchmodel = VehicleListingUser.searchmodel

        if (fillBookingActivityBinding!!.model!!.profile_pic != null) {
        }

        getProfile()
    }

    lateinit var model: VehicleSearchData.Data.Vehicles
    lateinit var searchmodel: SearchBookingModel

    fun getProfile() {


        ApiServices<BookingModelUser>().callApi(
            Urls.GET_PROFILE,
            this,
            RegistrationModel().getProfile(SessionManager.getLoginModel(this)),
            BookingModelUser::class.java,
            true,
            this
        )


    }


    fun fillbooking(view: View) {
        /*profile_pic,dl_name,contact_number,upload_license_image,email_address,dl_number,delivery_address,user_id(Mandatory),vehicle_id(Mandatory)*/

        var hash = HashMap<String, Any>()
        userWallet()


        hash.put("booking_pic", fillBookingActivityBinding!!.model!!.profile_picFile)
        hash.put("dl_name", fillBookingActivityBinding!!.model!!.dl_name!!)
        hash.put("contact_number", fillBookingActivityBinding!!.model!!.contact_number!!)
        hash.put("license_image", fillBookingActivityBinding!!.model!!.upload_license_image!!)
        hash.put("email_address", fillBookingActivityBinding!!.model!!.email!!)
        hash.put("dl_number", fillBookingActivityBinding!!.model!!.dl_number!!)
        hash.put("delivery_address", fillBookingActivityBinding!!.model!!.delivery_address!!)
        hash.put("user_id", SessionManager.getLoginModel(this@FillBookingActivity).data.user_id)
        hash.put("vehicle_id", model.vehicle_id!!)

        hash.put("from_date_time", ((searchmodel.fromDateTimeZone.toLong())/1000L).toString())
        hash.put("drop_address", model!!.drop_location_address)
        hash.put("to_date_time", ((searchmodel.toDateTimeZone.toLong())/1000L).toString())
        hash.put("location_address", searchmodel.locationaddress)
        hash.put("location_latitude", searchmodel.location_latitude)
        hash.put("location_longitude", searchmodel.location_longitude)
        hash.put("is_door_delivery", if (searchmodel.isdeliveryatdoor) 1 else 0)
        hash.put("is_different_city_drop", if (searchmodel.isdiferentcity) 1 else 2)


   //     hash.put("delivery_at_my_door_charge", 200)

        hash.put("payable_amount", model!!.payable_amount!!)

        payable_amount = model!!.payable_amount!!

        if (BookingModelUser().validation(view, fillBookingActivityBinding!!.model!!,searchmodel))
            ApiServices<BookingModelUser>().callApi(
                Urls.VEHICLE_BOOKING,
                this,
                hash,
                BookingModelUser::class.java,
                true,
                this
            )
    }
    fun userWallet(){
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
        ApiServices<WalletModel>().callApi(Urls.USER_WALLET, this, hashmap, WalletModel::class.java,
            true, this)
    }

    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        when (title) {

            "lisence" -> {

                fillBookingActivityBinding.model!!.isLisancePreview = true
                fillBookingActivityBinding.model!!.upload_license_image = File(images.path)
                fillBookingActivityBinding.model!!.upload_license = images.name

                fillBookingActivityBinding.invalidateAll()
                loadImage(lisence_image, images.path)

            }
            "Profile" -> {
                fillBookingActivityBinding.model!!.profile_picFile = File(images.path)
                fillBookingActivityBinding.invalidateAll()
                loadImage(profile_image, images.path)
            }
        }
    }

    fun showPicker(title: String) {
        showImagePicker(title)
    }
}
