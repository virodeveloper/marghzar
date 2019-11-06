package pick.com.app.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlinpermissions.KotlinPermissions
import com.livinglifetechway.k4kotlin.core.hideKeyboard
import com.testfairy.TestFairy
import pick.com.app.MyApplication
import pick.com.app.base.model.LocationModel
import pick.com.app.interfaces.OnPermissinResponse
import pick.com.app.interfaces.getImages
import pick.com.app.interfaces.onLocationFromMap
import pick.com.app.uitility.launguage.MyContextWrapper
import pick.com.app.uitility.location.AppLocationActivity
import pick.com.app.uitility.session.SessionManager
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*


open class CommonActivity : AppLocationActivity(), getImages, OnPermissinResponse, onLocationFromMap {

    override fun myCurrentLocation(currentLocation: Location?) {

    }

    override fun newLocation(location: Location?) {

    }

    override fun attachBaseContext(base: Context) {

        setLanguage(base,SessionManager().getLaunguage(base))

        super.attachBaseContext(MyContextWrapper.wrap(base, SessionManager().getLaunguage(base)))
    }

    fun setLanguage(c: Context, lang: String) {
        val localeNew = Locale(lang)
        Locale.setDefault(localeNew)

        val res = c.resources
        val newConfig = Configuration(res.configuration)
        newConfig.locale = localeNew
        newConfig.setLayoutDirection(localeNew)
        res.updateConfiguration(newConfig, res.displayMetrics)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newConfig.setLocale(localeNew)
            c.createConfigurationContext(newConfig)
        }
    }


    private var mDrawerLayout: DrawerLayout? = null
    override fun getLocationFromMap(address: LocationModel?) {

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        MyApplication.appComponent.inject(this)
        TestFairy.begin(this, "46f2dd7ef2fa4cd1d23caf5e114c11d703c0c917")

    }


    companion object {
        lateinit var activity: Activity
    }

     fun showMessage(status: String="unsuccess", message: String) {


        if (status.toLowerCase() != "success") {

            var type =
                if (status.toLowerCase() == "success") SweetAlertDialog.SUCCESS_TYPE else SweetAlertDialog.ERROR_TYPE
            MaterialDialog(this@CommonActivity).show {
                title(text = getString(pick.com.app.R.string.app_name))
                positiveButton(text = getString(pick.com.app.R.string.ok))
                message(text = message)

            }

          /*   MaterialDialog.Builder(this)
                .title("MaterialDialog")
                .message("This is a simple MaterialDialog.")
                .positiveText("OK")*/

        }


    }


    override fun getPermissionStatus(type: String?, status: String?) {

    }

    override fun getImage(images: File, title: String) {
        Log.e("", "")
    }


    fun loadImage(view: ImageView, url: String) {
        Glide.with(this)
            .asBitmap()
            .apply(
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(pick.com.app.R.drawable.placeholder).error(
                    pick.com.app.R.drawable.placeholder
                )
            )
            .load(url)
            .into(view);
    }


    inline fun <T> T.apply(block: T.() -> Unit): T {


        return this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagePicked(imageFile: File, source: EasyImage.ImageSource, type: Int) {


                getImage(imageFile, imagepickertitle)
            }


        })







        super.onActivityResult(requestCode, resultCode, data)

    }

    var imagepickertitle = ""

    inline fun showImagePicker(title: String) {
        this.imagepickertitle = title

        EasyImage.openChooserWithGallery(this, "Select Image", 100)


    }

    inline fun <reified T : Activity> Activity.redirectTo() {
        startActivity(createIntent<T>())
        overridePendingTransition(pick.com.app.R.anim.slide_from_right, pick.com.app.R.anim.slide_to_left);

    }


    inline fun <reified T : Activity> Context.createIntent(vararg extras: Pair<String, Any?>) =
        Intent(this, T::class.java).apply { }

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)


    public fun redirectTo(cls: Class<*>) {


        return startActivity(setdata(cls))


    }

    fun setdata(cls: Class<*>): Intent {

        return Intent(this, cls)
    }


    fun setPermission(permission: String) {


        KotlinPermissions.with(this) // where this is an FragmentActivity instance
            .permissions(permission)
            .onAccepted { permissions ->
                //List of accepted permissions
                getPermissionStatus(permission, "accept")
            }
            .onDenied { permissions ->
                //List of denied permissions
                getPermissionStatus(permission, "denied")
            }
            .onForeverDenied { permissions ->
                //List of forever denied permissions
                getPermissionStatus("", "denied")
            }
            .ask()


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestFairy.begin(this, "5da127016263347b5e7b712f47e7806297f0cefc")
        hideKeyboard()
        activity = this


    }


}
