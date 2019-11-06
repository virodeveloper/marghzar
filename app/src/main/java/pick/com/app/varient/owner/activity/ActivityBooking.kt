package pick.com.app.varient.owner.activity


import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.livinglifetechway.k4kotlin.core.hideKeyboard
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.left_side_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import pick.com.app.Constants
import pick.com.app.R
import pick.com.app.base.LoginActivity
import pick.com.app.base.model.DrawerModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.base.model.ToolbarCustom.Companion.NoIcon
import pick.com.app.base.sociallogin.SocialLogin
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.fragment.booking.MyBookingFragmant
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.ui.fragment.booking.HomeFragment
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ActivityBooking : SocialLogin(), onResponse {

    companion object {
        lateinit var activity: Activity
    }

    override fun <T : Any?> onSucess(result: T?, methodtype: String?) {

        if (result is RegistrationModel) {

            val result = result as RegistrationModel

            if (result.message.toLowerCase() == "success") {
                SessionManager.setLoginModel(RegistrationModel(), this)
                Redirection().clearAllActivity()
                faceBookLogout()
                googleLogout()
                Redirection().goToLogin(true, this@ActivityBooking, null)


            }
            // showMessage(result.message, result.description)


        }

    }

    override fun onError(error: String?) {
        showMessage(
            "Unsuccess",
            if (error.toString() == "connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString()
        )

    }

    lateinit var activityBookingBinding: pick.com.app.databinding.ActivityBookingBinding

    lateinit var adpter: UniverSalBindAdapter


    var currentLocation: Location? = null
    override fun myCurrentLocation(currentLocation: Location?) {
        super.myCurrentLocation(currentLocation)
        if (this.currentLocation == null) {
            this.currentLocation = currentLocation!!
            setDrawerData()
        }
    }

    fun setDrawerData() {
        adpter = UniverSalBindAdapter(pick.com.app.R.layout.custom_row_drawer)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        recyclerView.adapter = adpter
        if (currentLocation == null) {

            val simplelocation = SimpleLocation(this)
            currentLocation = Location("")
            currentLocation = Location("")
            currentLocation!!.latitude = simplelocation.latitude
            currentLocation!!.longitude = simplelocation.longitude
        }

        if (Constants.apptype == Constants.AppType.USER) {
            activityBookingBinding.toolbar!!.title = resources.getString(R.string.home)
            adpter.addAll(DrawerModel(this).getUserArrayList(this, this, currentLocation!!) as ArrayList<Any>)

            var vehicle_id = intent.getStringExtra("vehicle_id")
            if (vehicle_id == null)
                vehicle_id = ""
            gotoFragmetn(HomeFragment.newInstance(currentLocation!!, vehicle_id))

        } else {
            activityBookingBinding.toolbar!!.title = resources.getString(R.string.my_booking)

            if (currentLocation == null) {

                val simplelocation = SimpleLocation(this)
                currentLocation = Location("")
                currentLocation = Location("")
                currentLocation!!.latitude = simplelocation.latitude
                currentLocation!!.longitude = simplelocation.longitude
            }

            adpter.addAll(DrawerModel(this).getOwerArrayList(this, this, currentLocation!!) as ArrayList<Any>)

            activityBookingBinding.toolbar!!.righticon=R.drawable.select_language_icon

            gotoFragmetn(MyBookingFragmant())
        }


    }

    override fun newLocation(location: Location?) {
        super.newLocation(location)
        if (this.currentLocation == null) {
            this.currentLocation = location!!
            setDrawerData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (LoginActivity.loginActivity != null) {
            LoginActivity.loginActivity!!.finish()

        }
        activity = this


        Redirection().clearAllActivity()

        // startActivity(Intent(Intent.ACTION_VOICE_COMMAND).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        // voiceInteractor.submitRequest(VoiceInteractor.Request())
        activityBookingBinding = DataBindingUtil.setContentView(this, pick.com.app.R.layout.activity_booking)

        val toolbar = ToolbarCustom(
            pick.com.app.R.drawable.nav_icon,
            resources.getString(R.string.booking),
            NoIcon,
            pick.com.app.R.drawable.noti_icon
        )

        activityBookingBinding.toolbar = toolbar




        left_Icon.onClick {
            hideKeyboard()
            drawer_layout.openDrawer(GravityCompat.START)
        }
        setDrawerData()


    }


    public override fun onResume() {
        super.onResume()

        activityBookingBinding.user = SessionManager.getLoginModel(this)
        activityBookingBinding.invalidateAll()

    }

    fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    fun gotoFragmetn(fragment: Any) {



        if (fragment is Activity) {
            startActivity(Intent(activity, fragment::class.java))
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(pick.com.app.R.id.frame, fragment as Fragment)
            fragmentTransaction.commit()
        }


    }

    fun gotoFragmetn(fragment: Any,strig:String) {


        if (fragment is Activity) {
            startActivity(Intent(activity, fragment::class.java).putExtra("url",strig))
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(pick.com.app.R.id.frame, fragment as Fragment)
            fragmentTransaction.commit()
        }


    }


    fun twoTabButtonDialog(type: Int) {
        if (type == 1) {

            MaterialDialog(this).show {
                title(text = getString(pick.com.app.R.string.app_name))
                message(text = getString(pick.com.app.R.string.logout_message))
                positiveButton(pick.com.app.R.string.yes) {
                    dismiss()
                    ApiServices<RegistrationModel>().callApiinObject(
                        Urls.LOGOUT,
                        this@ActivityBooking,
                        getLogout(),
                        RegistrationModel::class.java,
                        true, this@ActivityBooking
                    )
                }
                negativeButton(pick.com.app.R.string.No) {
                    dismiss()
                }
            }

            /*  SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)

                  .setTitleText(getString(pick.com.app.R.string.app_name))
                  .setContentText(getString(pick.com.app.R.string.logout_message))
                  .setConfirmText(getString(pick.com.app.R.string.yes))
                  .setCancelText(getString(pick.com.app.R.string.No))
                  .setCancelClickListener { sDialog -> sDialog.cancel() }
                  .setConfirmClickListener {

                          sDialog ->
                      sDialog.cancel()

                      ApiServices<RegistrationModel>().callApiinObject(
                          Urls.LOGOUT,
                          this,
                          getLogout(),
                          RegistrationModel::class.java,
                          true, this
                      )
                      // toast("Click Yes Button")
                  }
                  .show()*/
        }
    }

    fun getLogout(): JSONObject {

        val object_ = JSONObject()

        object_.put("user_id", SessionManager.getLoginModel(this@ActivityBooking).data.user_id)
        return object_
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            exitDouble()
        }


    }

    fun exitDouble() {
        if (doubleBackToExitPressedOnce) {
            Redirection().clearAllActivity()
            super.onBackPressed()

            return
        }
        this.doubleBackToExitPressedOnce = true
        toast(resources.getString(pick.com.app.R.string.back_exit))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}