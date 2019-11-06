package pick.com.app.varient.owner.activity

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hideKeyboard
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.booking_detail_activity.*
import kotlinx.android.synthetic.main.check_up_after_fill_form.*
import kotlinx.android.synthetic.main.check_up_after_view.*
import kotlinx.android.synthetic.main.check_up_before_fill_form.*
import kotlinx.android.synthetic.main.check_up_before_view_user.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.PreviewActivity
import pick.com.app.base.model.PreviewModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.base.model.ToolbarCustom.Companion.NoIcon
import pick.com.app.databinding.BookingDetailActivityBinding
import pick.com.app.interfaces.OnRemoveImage
import pick.com.app.interfaces.onItemSelect
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.owner.pojo.BookingModel.Data.BeforePickupDetail.AsseriesData
import pick.com.app.varient.owner.pojo.ImageModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BookingDetailsActivity : BaseActivity(), onItemSelect, OnRemoveImage {
    override fun onImageRemoved(imageid: String?) {
        removeImageFromServer(imageid!!)
    }

    lateinit var imageModel: ImageModel
    lateinit var binding: BookingDetailActivityBinding
    lateinit var bookingModel: BookingModel.Data
   lateinit var adaptessrmanage : UniverSalBindAdapter

    override fun onDestroy() {
        super.onDestroy()
    }

    fun removeImageFromServer(image_id:String){
        /**/
        val hahmap=HashMap<String,Any>()
        hahmap["image_id"]=image_id
        ApiServices<BookingModel>().callApi(Urls.IMAGE_REMOVE,this,hahmap,BookingModel::class.java,false,this)
    }
    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        when (methodtype) {

            Urls.SAVE_CHECKUP_BEFORE , Urls.SAVE_CHECKUP_AFTER-> {

                val result = result as BookingModel

                if (result.status == 1) {


                    showMessageWithError(result.description,isfinish = true)
                }

            }

            Urls.CHANGE_BOKING_STATUS->{
                val result = result as BookingModel

                if (result.status == 1) {


                    showMessageWithError(result.description,isfinish = true)

                }
            }

            Urls.IMAGE_UPLOAD->{
                val result = result as BookingModel

                if (result.status == 1) {


                    toast(result.description)

                }

            }

            Urls.RATING_USER->{
                val result = result as BookingModel

                if (result.status == 1) {

alert_dialog.dismiss()
                    toast(result.description)

                }

            }
        }
    }


    override fun getSelectedItem(o: Any) {
        if (o is ImageModel) {
            imageModel = o
            showImagePicker(resources.getString(R.string.please_select_vichele_picture))
        }
        else if (o is PreviewModel){
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

          startActivity(Intent(this,PreviewActivity::class.java).putExtra("model",previewModel))



        }
    }

    var images = ArrayList<File>()
    override fun getImage(images: File, title: String) {
        super.getImage(images, title)


   /*  var   compressedImage = Compressor(this)
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setQuality(35)
            .setCompressFormat(Bitmap.CompressFormat.WEBP)
            .setDestinationDirectoryPath(
                Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getAbsolutePath())
            .compressToFile(File(images[0].path));
        //IMG-20190306-WA0007.jpg
        //IMG-20190306-WA0007.jpg*/
        imageModel.url =images.path
        this.images.add(images)

        preiewChekFilllist.add(PreviewModel.Data(imageModel.url  ))


        imageUpload(imageModel)
        imageModel.adapter!!.notifyDataSetChanged()
        if (imageModel.adapter!!.arrraylist.size < 10)
            imageModel.adapter!!.add(ImageModel(imageModel.adapter!!, this,this,imageModel.image_view_type))

    }

    lateinit var adapterAddAsseries: UniverSalBindAdapter
    lateinit var adapterImage: UniverSalBindAdapter


    fun check_up_before_fill_form(){
        adapterImage = UniverSalBindAdapter(R.layout.custom_image_booking_details)
        adapterImage.add(ImageModel(adapterImage, this,this,"CheckUpFill"))

        recycleview_before_chekc.isNestedScrollingEnabled = false
        recycleview_before_chekc.adapter = adapterImage

/*Asseries*/

        adapterAddAsseries = UniverSalBindAdapter(R.layout.custom_add_asseries_layout)

        recycleviewAddasseries_before_chekc.adapter = adapterAddAsseries

        adapterAddAsseries.add(AsseriesData(adapterAddAsseries))

    }

    var adapterBeforeViewImages = UniverSalBindAdapter(R.layout.custom_image_booking_details)

    var adpatershowadsseries = UniverSalBindAdapter(R.layout.custom_user_view_asseries)
    fun check_up_before_view_user(){

        for (item in bookingModel.before_pickup_detail.vehicle_images){
            val imageModel = ImageModel(adapterBeforeViewImages, this,this,"CheckUpBefore")
            imageModel.url = "${bookingModel.upload_url}car_image/${item.car_image}"

            preiewChekBeforelist.add(PreviewModel.Data(imageModel.url))

            adapterBeforeViewImages.add(imageModel)

        }

        recycleviewBeforeViewImages.adapter = adapterBeforeViewImages



        /*ASSEries*/

        recycleviewBeforeViewShowAsseries.adapter = adpatershowadsseries
        adpatershowadsseries.addAll(bookingModel.before_pickup_detail.accessories as ArrayList<Any>)
    }


    fun check_up_after_fill_form(){
        recycleview_check_after.isNestedScrollingEnabled = false

        adapterImage = UniverSalBindAdapter(R.layout.custom_image_booking_details)
        adapterImage.add(ImageModel(adapterImage, this,this,"CheckUpFill"))

        recycleview_check_after.adapter = adapterImage
        adaptessrmanage = UniverSalBindAdapter(R.layout.custom_manage_asseries)
        recycleviewAddasseries_check_after.adapter = adaptessrmanage
        adaptessrmanage.addAll(bookingModel.before_pickup_detail.accessories as ArrayList<Any>)

        recycleviewAddasseries_check_after.addItemDecoration(
            DividerItemDecoration(
                recycleviewAddasseries_check_after.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }


    fun check_up_after_view(){
        val adapterAferFillViewImages=UniverSalBindAdapter(R.layout.custom_image_booking_details)
        for (item in bookingModel.after_pickup_detail!!.vehicle_images){
            val imageModel = ImageModel(adapterAferFillViewImages, this,this,"CheckUpAfter")
            imageModel.url = "${bookingModel.upload_url}car_image/${item.car_image}"

            preiewChekAfterlist.add(PreviewModel.Data(imageModel.url))

            adapterAferFillViewImages.add(imageModel)

        }
        recycleview_check_afterView.adapter=adapterAferFillViewImages

        val adpatershowasseries_check_afterView = UniverSalBindAdapter(R.layout.custom_user_view_asseries)
        recycleview_asseries_check_afterView.adapter = adpatershowasseries_check_afterView
        for (item in bookingModel.after_pickup_detail!!.accessories){
            val aseries = AsseriesData(adpatershowadsseries)
            aseries.name=item.name
            aseries.quantity=item.quantity
            aseries.value=item.value

            adpatershowasseries_check_afterView.add(aseries)

        }

    }

    var preiewChekFilllist=ArrayList<PreviewModel.Data>()
    var preiewChekBeforelist=ArrayList<PreviewModel.Data>()
    var preiewChekAfterlist=ArrayList<PreviewModel.Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.booking_detail_activity)
        bookingModel = intent.extras!!.get("model") as BookingModel.Data
        val bookingDetail =
            ToolbarCustom(ToolbarCustom.lefticon, getString(R.string.booking_detail), NoIcon, NoIcon)
        bookingModel.isdetailsshow=true
        binding.toolbar = bookingDetail
        binding.user = bookingModel
        left_Icon.onClick {
            onBackPressed()
        }

        loadImage(imageLisence, bookingModel.upload_url + "dl_image/" + bookingModel.user_data!!.license_image)









        check_up_before_fill_form()


        check_up_before_view_user()


        check_up_after_fill_form()


        check_up_after_view()

        hideKeyboard()

    }

    fun submitData(view: View) {

        var b=bookingModel.from_date_time
        val first = b[0]
        val two = b[1]
        val three = b[2]
        val four = b[3]
        val sb = StringBuilder()
        sb.append(first)
        sb.append(two)
        sb.append(three)
        sb.append(four)


        var d=(Calendar.getInstance().timeInMillis.toString().toLong())/1000L
        var f=d.toString()
        val firs = f[0]
        val tw = f[1]
        val thre = f[2]
        val fou = f[3]
        val sbi = StringBuilder()
        sbi.append(firs)
        sbi.append(tw)
        sbi.append(thre)
        sbi.append(fou)
        val l = sb.toString().toInt()
        val i=l-1
        val c = sbi.toString().toInt()

        if(l==c){

            when (bookingModel.booking_status) {

                1 -> {
                    saveCheckUPBefore(view)
                }

                4 -> {
                    saveCheckUPAfter(view)
                }

                6->{
                    vehicleRating(view)
                }

            }

        }
        else
            Toast.makeText(applicationContext,"You are not yet authorized for check before",Toast.LENGTH_SHORT).show()







    }

    fun saveCheckUPBefore(view: View) {

        bookingModel.before_pickup_detail.accessories=adapterAddAsseries.arrraylist as java.util.ArrayList<AsseriesData>
        if (BookingModel().checkUpBeforeValidation(view,bookingModel)&&BookingModel().AddAsseriesVilidation(view, bookingModel.before_pickup_detail!!.accessories))
        {



        val hahsmap = HashMap<String, Any>()

        BookingModel()
        hahsmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hahsmap["booking_id"] = bookingModel.booking_id!!
        hahsmap["fuel_amount"] = bookingModel.fuel_amount
        hahsmap["kilometer"] = bookingModel.kilometer

        for (item in adapterAddAsseries.arrraylist) {
            val item = item as AsseriesData

            hahsmap.put("accessory_details[${item.position}][name]", item.name.trim())
            hahsmap.put("accessory_details[${item.position}][quantity]", item.qty.trim())
            hahsmap.put("accessory_details[${item.position}][value]", item.price.trim())


        }



        ApiServices<BookingModel>().callApi(Urls.SAVE_CHECKUP_BEFORE,this,hahsmap,BookingModel::class.java,true,this)

        }
    }


    fun saveCheckUPAfter(view: View) {

        bookingModel.before_pickup_detail.accessories=adaptessrmanage.arrraylist as java.util.ArrayList<AsseriesData>
        if (BookingModel().checkUpAfterValidation(view,bookingModel)&&BookingModel().manageAsseriesVilidation(view, bookingModel.before_pickup_detail.accessories))
        {



            val hahsmap = HashMap<String, Any>()

            BookingModel()
            hahsmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
            hahsmap["booking_id"] = bookingModel.booking_id!!
            hahsmap["fuel_amount"] = bookingModel.afterfuel_amount.trim()
            hahsmap["kilometer"] = bookingModel.afterkilometer.trim()
            hahsmap["vehicle_damage_charges"] = bookingModel.aftervechileDemageCharge.trim()

            for (item in adaptessrmanage.arrraylist) {
                val item = item as AsseriesData

                hahsmap.put("accessory_details[${item.position}][check_status]",if (item.isavail) 1 else 0)
                hahsmap.put("accessory_details[${item.position}][name]", item.name.trim())
                hahsmap.put("accessory_details[${item.position}][quantity]", item.qty.trim())
                hahsmap.put("accessory_details[${item.position}][value]", "${item.value.trim()}")

            }

            ApiServices<BookingModel>().callApi(Urls.SAVE_CHECKUP_AFTER,this,hahsmap,BookingModel::class.java,true,this)

        }
    }


    fun imageUpload(file:ImageModel){


        val item=File(file.url)

        val hahsmap = HashMap<String, Any>()
        hahsmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hahsmap["booking_id"] = bookingModel.booking_id!!

        if (bookingModel.booking_status==4) {
            hahsmap["type"] = "a"
        }
        else {
            hahsmap["type"] = "b"
        }
        try {
            hahsmap["car_image"] = item
            adapterImage.contactListFiltered.removeAt(0)
        } catch (e: Exception) {
        }

        ApiServices<ImageModel>().fileUploadWithProgressBar(file,hahsmap,this)
    }

    fun cancelBookingByOwner(view: View){


        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = bookingModel.booking_id!!
        hashmap["booking_status"] = 8




        ApiServices<BookingModel>().callApi(
            Urls.CHANGE_BOKING_STATUS,
            this,
            hashmap,
            BookingModel::class.java,
            true,
            activity
        )
    }

    private lateinit var alert_dialog : Dialog


    fun vehicleRating(data : View) {
        val dialog = Dialog(this, pick.com.app.R.style.mtrate)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_rating_vechile_dialog)
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent)

        val vehicleRating : AppCompatRatingBar = dialog.findViewById(R.id.vehicleRating)
        vehicleRating.rating = 0.5f
        vehicleRating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (rating < 0.5f)
                    vehicleRating.rating = 0.5f
            }
        val textViewTitle : TextView = dialog.findViewById(R.id.textViewTitle)
        val cancel_rating_dialog : ImageView = dialog.findViewById(R.id.cancel_rating_dialog)
        val submit_rating : Button = dialog.findViewById(R.id.submit_rating)
        textViewTitle.text=getString(R.string.AddRatingForUser)
        cancel_rating_dialog.setOnClickListener {
            dialog.dismiss()
        }
        submit_rating.setOnClickListener {
            alert_dialog = dialog
            submitRating(bookingModel, vehicleRating.rating)
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


}
