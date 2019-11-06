package pick.com.app.base

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.kotlinpermissions.KotlinPermissions
import com.livinglifetechway.k4kotlin.core.hideKeyboard
import com.livinglifetechway.k4kotlin.core.toast
import com.shockwave.pdfium.PdfiumCore
import com.testfairy.TestFairy
import pick.com.app.R
import pick.com.app.interfaces.*
import pick.com.app.uitility.custom.TimePickerCustom
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity :AppCompatActivity() , getImages, OnDateTimePicker, OnPermissinResponse, onResponse ,
    OnGooglePlacePicker {
    override fun getLocation(location: String?, latitude: Double?, longitude: Double?) {

    }

    override fun getDate(date: String?, type: String?) {




    }

    override fun getTime(time: String?, type: String?) {
    }

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        when(methodtype){
            Urls.FAVOURITE_VEHICLE->{
                var result=result as BookingModel

                if (result.status==1){

                    toast(result.description)
                }else{
                    onError(result.description)
                }
            }

        }
    }



    override fun onError(error: String?) {
        showMessageWithError(message=error!!,isfinish = false)
    }


    override fun onResume() {
        super.onResume()
        activity = this

    }
    fun manageFavVehicle(vehicle_id : String,listner:onResponse?=this){

        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(BaseActivity.activity).data.user_id
        hashmap["vehicle_id"] = vehicle_id


        ApiServices<BookingModel>().callApi(
            Urls.FAVOURITE_VEHICLE, listner!!, hashmap, BookingModel::class.java, true, activity!!
        )
    }


    companion object {
        lateinit var activity: Activity

    }

    fun generateImageFromPdf(pdfUri: Uri):Bitmap {
        val pageNumber = 0
        val pdfiumCore = PdfiumCore(this)
        var bmp : Bitmap?=null
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            val fd = contentResolver.openFileDescriptor(pdfUri, "r")
            val pdfDocument = pdfiumCore.newDocument(fd)
            pdfiumCore.openPage(pdfDocument, pageNumber)
            val width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber)
            val height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber)
             bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height)
            //saveImage(bmp)

            return bmp

            pdfiumCore.closeDocument(pdfDocument) // important!
        } catch (e: Exception) {

            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pdfUri);

        }
        return bmp!!
    }





    val FOLDER = ""+Environment.getExternalStorageDirectory()+"/PDF"
    private fun saveImage(bmp: Bitmap) {
        var out: FileOutputStream? = null
        try {
            val folder = File(FOLDER)
            if (!folder.exists())
                folder.mkdirs()
            val file = File(folder, "PDF.png")
            out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
        } catch (e: Exception) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out!!.close()
            } catch (e: Exception) {
                //todo with exception
            }

        }
    }
    fun goToFragment(fragment: androidx.fragment.app.Fragment, isBAckStuck:Boolean=false, framId:Int=pick.com.app.R.id.frame){



        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(framId,
            fragment!!
        )
        if (isBAckStuck)
            fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()




    }

    fun gotoshowMessage(status: String="Unsucess", message: String, activity: Class<*>?) {




            var type =
                if (status.toLowerCase() == "success") SweetAlertDialog.SUCCESS_TYPE else SweetAlertDialog.ERROR_TYPE
            MaterialDialog(this).show {
                title(text = getString(pick.com.app.R.string.app_name))
                cancelable(false)
                positiveButton(text = getString(pick.com.app.R.string.ok)){
                    if (activity==null){

                    }
                    finish()
                }
                message(text = message)

            }

    }

    fun showMessageWithError(message: String="", isfinish:Boolean=false) {

        MaterialDialog(this).show {
            title(text = getString(pick.com.app.R.string.app_name))
            cancelable(false)
            positiveButton(text = getString(pick.com.app.R.string.ok)){
                if (isfinish==true){
                    finish()
                }
            }
            message(text = message)

        }






    }


    fun showMessage(status: String="Unsucess", message: String="") {


        if (status.toLowerCase() != "success") {

            var type =
                if (status.toLowerCase() == "success") SweetAlertDialog.SUCCESS_TYPE else SweetAlertDialog.ERROR_TYPE
            MaterialDialog(this).show {
                title(text = getString(pick.com.app.R.string.app_name))
                cancelable(false)
                positiveButton(text = getString(pick.com.app.R.string.ok))
                message(text = message)

            }



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
        EasyImage.openChooserWithGallery(this, getString(R.string.SelectImage), 100)



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

    override fun onStart() {
        super.onStart()

        TestFairy.begin(this, "46f2dd7ef2fa4cd1d23caf5e114c11d703c0c917")
        hideKeyboard()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = token
                Log.e("token", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })

    }

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    fun showDataPicker(type: String,to_date_time: String,myCalendar:Calendar=Calendar.getInstance()){


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            getDate(sdf.format(myCalendar.time),type)


        }

        val result = Date(to_date_time.toLong()*1000L)
        myCalendar.time = result

            val dialog = DatePickerDialog( activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))


            dialog.datePicker.minDate = myCalendar.timeInMillis
            dialog.show()

    }

    fun showDataPicker(type: String,searchBookingModel: SearchBookingModel){


        var myCalendar = Calendar.getInstance();
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            getDate(sdf.format(myCalendar.time),type)


        }
        if (type=="ToDate"){
            if (searchBookingModel.fromDate=="") {
                activity.toast(resources.getString(R.string.please_select_from_date))
                return
            }else{
                val dialog = DatePickerDialog( activity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                var myCalendar = Calendar.getInstance()
                myCalendar.set(mYear, mMonth, mDay)
                dialog.datePicker.minDate = myCalendar.timeInMillis
                dialog.show()
            }
        }else {
            val dialog = DatePickerDialog( activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
            dialog.datePicker.minDate = System.currentTimeMillis() + 1000
            dialog.show()
        }

    }


    fun showTimePicker(type: String,searchBookingModel: SearchBookingModel){
        var myCalendar = Calendar.getInstance();
        val date = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            // TODO Auto-generated method stub
            val time_select : String = hourOfDay.toString()+":"+minute.toString()
            getTime(changeDateTimeFormat(time_select), type)
        }
        if(searchBookingModel.fromDate != ""){
            if(checkCurrentDate(searchBookingModel.fromDate)){
                val dialog= TimePickerCustom(
                    activity, date, myCalendar
                        .get(Calendar.HOUR_OF_DAY) , myCalendar.get(Calendar.MINUTE) , true
                )
                val hour : Int = myCalendar.get(Calendar.HOUR_OF_DAY)+1
                val minute : Int = myCalendar.get(Calendar.MINUTE)+1
                dialog.setMin(hour ,minute)
                dialog.setMax(23,59)

                dialog.show()
            }else{
                val dialog= TimePickerCustom(
                    activity, date, myCalendar
                        .get(Calendar.HOUR_OF_DAY)+1, myCalendar.get(Calendar.MINUTE), true
                )

                dialog.show()
            }
        }else {
            activity.toast(resources.getString(R.string.please_select_from_date))
            return
        }

    }


    fun changeDateTimeFormat(time: String): String {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val date = inputFormat.parse(time)
        return outputFormat.format(date)
    }

    fun checkCurrentDate(selected_date : String) : Boolean{
        val selectDate : String =  selected_date
        val c1 : Date = Calendar.getInstance().getTime()
        val df : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate : String = df.format(c1)
        return selectDate == formattedDate
    }
}
