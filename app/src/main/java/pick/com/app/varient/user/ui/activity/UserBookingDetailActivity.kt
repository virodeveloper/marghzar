package pick.com.app.varient.user.ui.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import kotlinx.android.synthetic.main.check_up_after_view.*
import kotlinx.android.synthetic.main.check_up_before_view_user.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_booking_details.*
import pick.com.app.R
import pick.com.app.base.BookingBaseActivity
import pick.com.app.base.PreviewActivity
import pick.com.app.base.model.PreviewModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.BookingAlertDilaogBinding
import pick.com.app.databinding.ExtendedBookingDialogBinding
import pick.com.app.databinding.UserBookingDetailsBinding
import pick.com.app.interfaces.onItemSelect
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.owner.pojo.ImageModel
import pick.com.app.varient.user.pojo.LoginModel
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.util.*

class UserBookingDetailActivity : BookingBaseActivity(), onItemSelect {
    override fun getSelectedItem(o: Any) {
       if (o is PreviewModel){
            var previewModel=o

            when(previewModel.view_type){

                "CheckUpFill" ->{

                }
                "CheckUpBefore" ->{
                    previewModel.arrlist=preiewChekBeforelist
                }
                "CheckUpAfter" ->{
                    previewModel.arrlist=preiewChekAfterlist
                }


            }

            startActivity(Intent(this, PreviewActivity::class.java).putExtra("model",previewModel))



        }
    }

    fun goToListing(){

        if(searchValidate()){
            val from_date_time_timezone =searchBookingMode.fromDate + " " +searchBookingMode.fromTime
            val date = from_date_time_timezone.getDateWithServerTimeStamp()
            val to_date_time_timezone =searchBookingMode.toDate + " " +searchBookingMode.toTime
            val date_to = to_date_time_timezone.getDateWithServerTimeStamp()
            searchBookingMode.fromDateTimeZone = "${date!!.time}"
            searchBookingMode.toDateTimeZone = "${date_to!!.time}"


                val bundle = Bundle()
                bundle.putSerializable("model", searchBookingMode)



           toast("Api calling for rebook")
        }

    }

    var year: Int = 0
     var monthOfYear: Int = 0
     var dayOfMonth: Int = 0
    fun getCalenderIndtance():Calendar{
var myCalendar=Calendar.getInstance()
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        return myCalendar
    }



    var alert_dialog : Dialog? = null

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)


        when(methodtype)
        {
            Urls.GET_CITIES->{
                var model=result as RegistrationModel.Data.Locations


            }
            else ->{
                val result = result as BookingModel

                if(alert_dialog!= null){
                    alert_dialog!!.dismiss()
                }
                if(result.status == 1){

                    showMessageWithError(result.description, true)
                }
            }

        }

    }

    private lateinit var binding : UserBookingDetailsBinding
    lateinit var bookingModel: BookingModel.Data
    private fun getShareApplication(): List<String> {
        val mList = ArrayList<String>()
        mList.add("com.facebook.katana")
        mList.add("com.twitter.android")

        mList.add("com.instagram.android")
        return mList

    }


    private fun Share(PackageName: List<String>, Text: String) {
        try {
            val targetedShareIntents = ArrayList<Intent>()
            val share = Intent(android.content.Intent.ACTION_SEND)
            share.type = "text/plain"
            val resInfo = getPackageManager().queryIntentActivities(share, 0)
            if (!resInfo.isEmpty()) {
                for (info in resInfo) {
                    val targetedShare = Intent(android.content.Intent.ACTION_SEND)
                    targetedShare.type = "text/plain" // put here your mime type
                    if (PackageName.contains(info.activityInfo.packageName.toLowerCase())) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, Text)
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase())
                        targetedShareIntents.add(targetedShare)
                    }
                }
                val chooserIntent = Intent.createChooser(targetedShareIntents.removeAt(0), "Share")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toTypedArray<Parcelable>())
                startActivity(chooserIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, pick.com.app.R.layout.user_booking_details)
        val bookingDetail = ToolbarCustom(ToolbarCustom.lefticon, getString(pick.com.app.R.string.booking_detail), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        binding.toolbar = bookingDetail
        bookingModel = intent.extras!!.get("model") as BookingModel.Data
        binding.user = bookingModel
        left_Icon.onClick {
            onBackPressed()
        }

        check_up_before_view_user()

        check_up_after_view()

        cancel_booking.setOnClickListener {
            when (bookingModel.booking_status) {
                0 -> {
                    cancelBooking("7", bookingModel)
                }

                1 -> {
                    cancelBooking("7", bookingModel)
                }

                3 -> {
                    changeStatus("4", bookingModel)
                }

                5 -> {
                    createReport(bookingModel)
                }

                6 -> {
                    var bundel=Bundle()
                    bundel.putString("vehicle_id",bookingModel.vehicle_id)
                    Redirection().goToHome(false, this, bundel)
                    //rebookDialog(bookingModel)
                }

                11 -> {
                    changeStatus("4", bookingModel)
                }

            }
        }

        first_button.setOnClickListener {
            when(bookingModel.booking_status){
                1 -> {
                    downlaodPDF(bookingModel.upload_url+""+bookingModel.booking_detail_pdf)
                }
                2 -> {
                    changeStatus("3", bookingModel)
                }

                3 -> {
                    extendBookingDialog(bookingModel)
                }

                5 -> {
                    changeStatus("6", bookingModel)
                }

                6 -> {
                    vehicleRating(bookingModel)
                }

                9 ->{
                    startActivity(Intent(this, WalletActivity::class.java).putExtra("booking_id", bookingModel.booking_id))
                }

            }
        }
    }


    var data= BookingModel.Data()
    fun changeStatus(status : String, data : BookingModel.Data) {
        this.data=data

        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["booking_status"] = status

        ApiServices<BookingModel>().callApi(Urls.CHANGE_BOKING_STATUS_USER, this, hashmap, BookingModel::class.java, true, activity)
    }

    fun cancelBooking(status : String, data : BookingModel.Data) {
        this.data=data

        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["booking_status"] = status

        ApiServices<BookingModel>().callApi(Urls.CANCEL_BOOKING_USER, this, hashmap, BookingModel::class.java, true, activity)
    }


    fun createReport(data : BookingModel.Data) {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(pick.com.app.R.layout.user_report_dialog)
        val report_message : EditText = dialog.findViewById(pick.com.app.R.id.report_message)
        val cancel_report : ImageView = dialog.findViewById(pick.com.app.R.id.cancel_report)
        val submit_report : Button = dialog.findViewById(pick.com.app.R.id.submit_report)
        cancel_report.setOnClickListener {
            dialog.dismiss()
        }
        submit_report.setOnClickListener {
            alert_dialog = dialog
            if(report_message.text.toString().trim().isEmpty())
                activity.toast(resources.getString(pick.com.app.R.string.report_empty_message))
            else
                submitReport(data, report_message.text.toString().trim())
        }
        dialog.show()
    }

    fun submitReport(data : BookingModel.Data, message_report : String) {
        this.data=data

        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["report_message"] = message_report

        ApiServices<BookingModel>().callApi(Urls.USER_REPORT, this, hashmap, BookingModel::class.java, true, activity)
    }

    fun shareDetails(view:View){
        shareDailog(binding.user!!)
    }

    fun shareDailog(data : BookingModel.Data) {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(pick.com.app.R.layout.share_dialog)
        val cancel_share_dialog : ImageView = dialog.findViewById(R.id.cancel_share_dialog)
        val facebook : ImageView = dialog.findViewById(pick.com.app.R.id.facebook)
        val twitter : ImageView = dialog.findViewById(pick.com.app.R.id.twitter)
        val instagram : ImageView = dialog.findViewById(pick.com.app.R.id.instagram)

        dialog.show()

        cancel_share_dialog.setOnClickListener {
            dialog.dismiss()
        }

        facebook.onClick {
            ShareHashtag.Builder().setHashtag("#YOUR_HASHTAG").build();
            var shareLinkContent =  ShareLinkContent.Builder()

                .setQuote("Please enter description")
                .setContentUrl(Uri.parse(data.upload_url+"vehicle_images/"+data.vehicle_image))
                .build();

              ShareDialog.show(this@UserBookingDetailActivity,shareLinkContent);


        }


        twitter.onClick {

            val builder = TweetComposer.Builder(this@UserBookingDetailActivity)
                .text("Any text here. \nhttp://efdreams.com/data_images/dreams/face/face-03.jpg")//any sharing text here
                .image(Uri.parse(data.upload_url+"vehicle_images/"+data.vehicle_image))//sharing image uri
            builder.show()
        }
        instagram.onClick {

            val text = "Look at my awesome picture"
            val pictureUri = Uri.parse("http://efdreams.com/data_images/dreams/face/face-03.jpg")
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.setPackage("com.instagram.android")
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://efdreams.com/data_images/dreams/face/face-03.jpg"))
            shareIntent.type = "image/*"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                 startActivity(Intent.createChooser(shareIntent, "Share images..."))
        }

    }


    fun vehicleRating(data : BookingModel.Data) {
        val dialog = Dialog(this, pick.com.app.R.style.mtrate)
        dialog.setCancelable(false)
        dialog.setContentView(pick.com.app.R.layout.add_rating_vechile_dialog)
        val vehicleRating : AppCompatRatingBar = dialog.findViewById(pick.com.app.R.id.vehicleRating)
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        vehicleRating.rating = 0.5f
        vehicleRating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (rating < 0.5f)
                    vehicleRating.rating = 0.5f
            }

        val cancel_rating_dialog : ImageView = dialog.findViewById(pick.com.app.R.id.cancel_rating_dialog)
        val submit_rating : Button = dialog.findViewById(pick.com.app.R.id.submit_rating)
        cancel_rating_dialog.setOnClickListener {
            dialog.dismiss()
        }
        submit_rating.setOnClickListener {
            alert_dialog = dialog
            submitRating(data, vehicleRating.rating)
        }
        dialog.show()
    }

    fun submitRating(data : BookingModel.Data, rating : Float) {
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["rating"] = rating.toString()

        ApiServices<BookingModel>().callApi(Urls.RATING_USER, this, hashmap, BookingModel::class.java, true, activity)
    }

    var adapterBeforeViewImages = UniverSalBindAdapter(pick.com.app.R.layout.custom_image_booking_details)

    var adpatershowadsseries = UniverSalBindAdapter(pick.com.app.R.layout.custom_user_view_asseries)
    var preiewChekBeforelist=ArrayList<PreviewModel.Data>()
    var preiewChekAfterlist=ArrayList<PreviewModel.Data>()
    fun check_up_before_view_user(){

        for (item in bookingModel.before_pickup_detail.vehicle_images){
            val imageModel = ImageModel(this,"CheckUpBefore")
            imageModel.url = "${bookingModel.upload_url}car_image/${item.car_image}"
            preiewChekBeforelist.add(PreviewModel.Data(imageModel.url))

            adapterBeforeViewImages.add(imageModel)

        }

        recycleviewBeforeViewImages.adapter = adapterBeforeViewImages



        /*ASSEries*/

        recycleviewBeforeViewShowAsseries.adapter = adpatershowadsseries
        adpatershowadsseries.addAll(bookingModel.before_pickup_detail.accessories as ArrayList<Any>)
    }

    fun check_up_after_view(){
        val adapterAferFillViewImages=UniverSalBindAdapter(pick.com.app.R.layout.custom_image_booking_details)
        for (item in bookingModel.after_pickup_detail!!.vehicle_images){
            val imageModel = ImageModel(this,"CheckUpAfter")
            imageModel.url = "${bookingModel.upload_url}car_image/${item.car_image}"
            preiewChekAfterlist.add(PreviewModel.Data(imageModel.url))

            adapterAferFillViewImages.add(imageModel)

        }


        recycleview_check_afterView.adapter=adapterAferFillViewImages

        val adpatershowasseries_check_afterView = UniverSalBindAdapter(pick.com.app.R.layout.custom_user_view_asseries)
        recycleview_asseries_check_afterView.adapter = adpatershowasseries_check_afterView
        for (item in bookingModel.after_pickup_detail!!.accessories){
            val aseries = BookingModel.Data.BeforePickupDetail.AsseriesData(adpatershowadsseries)
            aseries.name=item.name
            aseries.quantity=item.quantity
            aseries.value=item.value

            adpatershowasseries_check_afterView.add(aseries)

        }

    }
    lateinit var   binding_Extended:ExtendedBookingDialogBinding
    fun extendBookingDialog(data : BookingModel.Data) {

        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
           binding_Extended = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.extended_booking_dialog,
            null,
            false
        ) as ExtendedBookingDialogBinding

        dialog.setContentView(binding_Extended.root)
        binding_Extended.userBooking = data
        binding_Extended.user = searchBookingMode
        binding_Extended.activity = this
        val cancel_extended_dialog : ImageView = dialog.findViewById(pick.com.app.R.id.cancel_extended_dialog)
        val submit_extended : Button = dialog.findViewById(pick.com.app.R.id.submit_extended)
        cancel_extended_dialog.setOnClickListener {
            dialog.dismiss()
        }
        submit_extended.setOnClickListener {
            when {
                binding_Extended.user!!.fromDate.isEmpty() -> activity.toast(resources.getString(R.string.extend_date_empty))
                binding_Extended.user!!.fromTime.isEmpty() -> activity.toast(resources.getString(R.string.extend_time_empty))
                else -> {
                    alert_dialog = dialog
                    val from_date_time_timezone = binding_Extended.user!!.fromDate + " " + binding_Extended.user!!.fromTime
                    val date = from_date_time_timezone.getDateWithServerTimeStamp()
                    binding_Extended.user!!.fromDateTimeZone = "${date!!.time}"
                    extendVehicleBooking(data, binding_Extended.user!!.fromDateTimeZone)
                }
            }
        }




        dialog.show()
    }

    fun downlaodPDF(url:String){
        ApiServices<LoginModel>().PdfDownlaod(url,this)
    }

    fun extendVehicleBooking(data : BookingModel.Data, date_time : String) {
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["date_time"] =   ((date_time.toLong())/1000L).toString()

        ApiServices<BookingModel>().callApi(Urls.EXTEND_BOOKING, this, hashmap, BookingModel::class.java, true, activity)
    }

lateinit var binding_booking_alert: BookingAlertDilaogBinding
    fun rebookDialog(data : BookingModel.Data) {

        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        binding_booking_alert = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.booking_alert_dilaog,
            null,
            false
        ) as BookingAlertDilaogBinding

        dialog.setContentView(binding_booking_alert.root)
        binding_booking_alert.activity = this@UserBookingDetailActivity

        binding_booking_alert.user = searchBookingMode
        val cancel_booking_dialog : ImageView = dialog.findViewById(pick.com.app.R.id.cancel_booking_dialog)
        cancel_booking_dialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}