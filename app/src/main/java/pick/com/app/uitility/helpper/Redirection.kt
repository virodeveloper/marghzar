package pick.com.app.uitility.helpper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import pick.com.app.Constants
import pick.com.app.R
import pick.com.app.base.*
import pick.com.app.varient.owner.activity.*
import pick.com.app.varient.owner.activity.EditProfileActivity
import pick.com.app.varient.owner.activity.RegistrationActivity
import pick.com.app.varient.user.ui.activity.*


class Redirection {

    lateinit var view:Context

var arrayList=ArrayList<Activity>()

    constructor(view: Activity) {
        this.view = view
    }

    constructor()

    fun goTofragment(activity: Activity,fragment: Fragment,backstuck:Boolean){





    }
    fun goToVechileListingUser(isfinish:Boolean = false,activity: Activity, bundle: Bundle? =Bundle() ) {
        this.view=activity
        arrayList.add(activity)
        redirectTo(VehicleListingUser::class.java, bundle ?: Bundle())
        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }




    fun goToListingPage(isfinish:Boolean = false,activity: Activity, bundle: Bundle? =Bundle() ) {
        this.view=activity
        arrayList.add(activity)
        redirectTo(ListActivity::class.java, bundle ?: Bundle())
        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }

    fun goToFilterActivity(isfinish:Boolean = false,activity: Activity, bundle: Bundle? =Bundle() ) {
        this.view=activity
        arrayList.add(activity)
        redirectTo(FilterActivity::class.java, bundle ?: Bundle())
        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }

    fun goToLogin(isfinish:Boolean,activity: Activity, bundle: Bundle?) {
         this.view=activity
         arrayList.add(activity)
        redirectTo(LoginActivity::class.java, bundle ?: Bundle())
         if (isfinish) {
             activity.finish()
             arrayList.remove(activity)
         }
    }

    fun goToAddVehicle(isfinish:Boolean=false, activity: Activity, bundle: Bundle=Bundle()) {
        this.view=activity
        arrayList.add(activity)
        redirectTo(AddNewVehicleActivity::class.java, bundle)
        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }


    fun goToAddVehicle(isfinish:Boolean=false, activity: Context, bundle: Bundle=Bundle()) {
        this.view=activity

        redirectTo(AddNewVehicleActivity::class.java, bundle)
        if (isfinish) {

            arrayList.remove(activity)
        }
    }


     fun goToConfirmOtp(isfinish:Boolean,activity: Activity, bundle: Bundle?) {
         this.view=activity
         arrayList.add(activity)
        redirectTo(ConfirmOtpActivity::class.java,bundle ?: Bundle())
         if (isfinish) {
             activity.finish()
             arrayList.remove(activity)
         }
    }

    fun goToOtp(isfinish:Boolean,activity: Activity, bundle: Bundle?) {
        this.view=activity
         arrayList.add(activity)
        redirectTo(OtpActivity::class.java,bundle ?: Bundle())
         if (isfinish) {
             activity.finish()
             arrayList.remove(activity)
         }
    }

    fun goToForgot(isfinish:Boolean,activity: Activity, bundle: Bundle?) {
        this.view=activity
        arrayList.add(activity)
        redirectTo(ForgotPasswordActivity::class.java,bundle ?: Bundle())
        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }

    fun goToSplash(activity: Activity) {
        this.view=activity
        redirectTo(SplashScreen::class.java,Bundle())

        activity.finishAffinity()
    }




    fun goToRegistrationOwner(isfinish:Boolean,activity: Activity, bundle: Bundle?) {
        this.view=activity
        arrayList.add(activity)
        if(Constants.apptype == Constants.AppType.OWNER){
            redirectTo(RegistrationActivity::class.java,bundle?: Bundle())
        }else{
            redirectTo(pick.com.app.varient.user.ui.activity.RegistrationActivity::class.java,bundle ?: Bundle())
        }

        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }


    fun goToCountryCode(isfinish:Boolean,activity: Activity, REQUEST_CODE: Int) {
        this.view=activity
        arrayList.add(activity)
        val intent= Intent(view, CountryCodeActivity::class.java)
        activity.startActivityForResult(intent,REQUEST_CODE)

        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }

    fun clearAllActivity(){
        for (activity in arrayList){
            activity.finish()
        }
        arrayList.clear()
    }

    fun goToHome(isfinish: Boolean, activity: Activity, bundle: Bundle?){
        this.view = activity
        arrayList.add(activity)

            redirectTo(ActivityBooking::class.java,bundle ?: Bundle())


        if (isfinish) {
            activity.finishAffinity()


        }
    }

    fun goToBookingDetail(isfinish: Boolean, activity: FragmentActivity, bundle: Bundle?){
        this.view = activity
        arrayList.add(activity)

        redirectTo(BookingDetailsActivity::class.java,bundle ?: Bundle())


        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }

    fun goToAddBankAccount(isfinish: Boolean=false, activity: FragmentActivity, bundle: Bundle?=null){
        this.view = activity
        arrayList.add(activity)

        redirectTo(AddAccount::class.java,bundle ?: Bundle())


        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }


    fun goToVehicleDetail(isfinish: Boolean=false, activity: FragmentActivity, bundle: Bundle?=null){
        this.view = activity
        arrayList.add(activity)

        redirectTo(VehicleDetailsActivity::class.java,bundle ?: Bundle())


        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }
    fun goToFillBooking(isfinish: Boolean=false, activity: Context, bundle: Bundle?){
        this.view = activity


        redirectTo(FillBookingActivity::class.java,bundle ?: Bundle())


        if (isfinish) {

            arrayList.remove(activity)
        }
    }

    fun goToUserBookingDetail(isfinish: Boolean, activity: FragmentActivity, bundle: Bundle?){
        this.view = activity
        arrayList.add(activity)

        redirectTo(UserBookingDetailActivity::class.java,bundle ?: Bundle())


        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }



    fun goToEdit(isfinish: Boolean, activity: Activity, bundle: Bundle?){
        this.view = activity
        arrayList.add(activity)
        if(Constants.apptype == Constants.AppType.USER){
            redirectTo(pick.com.app.varient.user.ui.activity.EditProfileActivity::class.java,bundle ?: Bundle())
        }else{
            redirectTo(EditProfileActivity::class.java,bundle ?: Bundle())
        }



        if (isfinish) {
            activity.finish()
            arrayList.remove(activity)
        }
    }


    inline fun <reified T : Activity> Activity.redirectTo() {
        startActivity(createIntent<T>())
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }


    inline fun <reified T : Activity> Context.createIntent(vararg extras: Pair<String, Any?>) =
        Intent(this, T::class.java).apply {  }

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)


     fun redirectTo(cls: Class<*>, bundle: Bundle?) {
       val intent= Intent(view, cls)
         intent.putExtras(bundle)

        return view.startActivity(intent)


    }



}
