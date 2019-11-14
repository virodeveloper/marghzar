package pick.com.app.varient.owner.activity


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter

import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import com.obsez.android.lib.filechooser.ChooserDialog
import kotlinx.android.synthetic.main.add_vehicle_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.interfaces.onVehicleSpinner
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream


class AddNewVehicleActivity : BaseActivity() , onVehicleSpinner {
    private val IMG_REQUEST = 2
  lateinit  var modell:VehicleModel.Data
    override fun getSpinnerSelectedItem(model: Any?, url: String?) {
        when(url){


            Urls.GET_SEATERS->{
                model as VehicleModel.Data.Seaters
                addNewVehicleActivity.model!!.seater=model.toString()
                addNewVehicleActivity.model!!.seater_id=model.toString()
            }


            Urls.GET_FUEL_TYPE->{
                model as VehicleModel.Data.Seaters
                addNewVehicleActivity.model!!.fuel_type=model.toString()
                addNewVehicleActivity.model!!.fuel_type_id=model.fuel_type_id
            }

            Urls.GET_TRANSMISSION->{
                model as VehicleModel.Data.Seaters
                addNewVehicleActivity.model!!.vehicle_transmission_title=model.toString()
                addNewVehicleActivity.model!!.vehicle_transmission=model.transmission_id
            }

            Urls.GET_YEARS->{
                addNewVehicleActivity.model!!.vehicle_year=model.toString()
            }

            Urls.BULK_AVAILABLE_LIST->{
                model as VehicleModel.Data.Seaters

                if (model.position==bulkAvailability.position){
                    val model= VehicleModel.Data.Seaters()
                    model.avail_id= bulkAvailability.avail_id
                    model.avail_title= bulkAvailability.avail_name
                    arrlistBultAvalibilit.remove(model)
                }

                model.position=bulkAvailability.position
                arrlistBultAvalibilit.add(model)

                bulkAvailability.avail_name=model.toString()
                bulkAvailability.avail_id=model.avail_id

            }

            Urls.GET_VEHICLE_TYPES->{


                if ( model is VehicleModel.Data.Seaters) {


                    addNewVehicleActivity.model!!.vehicle_type_name = model.toString()
                    addNewVehicleActivity.model!!.vehicle_type=model.type_id

                    addNewVehicleActivity.model!!.vehicle_subtype_name = ""
                    addNewVehicleActivity.model!!.vehicle_subtype = ""

                }
                else {
                    model as VehicleModel.Data.Seaters.SubType
                    addNewVehicleActivity.model!!.vehicle_subtype_name = model.toString()
                    addNewVehicleActivity.model!!.vehicle_subtype = model.subtype_id
                }

            }

            Urls.GET_MODELS->{

                model as VehicleModel.Data.Seaters
                addNewVehicleActivity.model!!.model_name=model.toString()
                addNewVehicleActivity.model!!.model_id=model.model_id
                addNewVehicleActivity.model!!.brand_id=model.brand_id
            }
        }
        addNewVehicleActivity.invalidateAll()
    }


    companion object {
        lateinit var  activity: AddNewVehicleActivity

        lateinit var onVehicleSpinner:onVehicleSpinner
    }
    lateinit var addNewVehicleActivity: pick.com.app.databinding.AddVehicleLayoutBinding

     var arrlistBultAvalibilit=ArrayList<VehicleModel.Data.Seaters>()
    fun showPicker(view: View) {
        showImagePicker(resources.getString(R.string.vehicle))
    }

    fun gotoListACtivity(url:String){

        val bundle=Bundle()
        bundle.putString("url",url)


        Redirection().goToListingPage(activity = this,bundle = bundle)

    }


    fun gotoListACtivity(url:String,bulkAvailability: VehicleModel.Data.BulkAvailability){

        val bundle=Bundle()
        bundle.putString("url",url)

this.bulkAvailability=bulkAvailability
        Redirection().goToListingPage(activity = this,bundle = bundle)

    }

  lateinit var  bulkAvailability: VehicleModel.Data.BulkAvailability
    fun getBulkAvalibity(){
        val jsonObject= JSONObject()

        jsonObject.put("name","dsfdsf")

        ApiServices<VehicleModel.Data>().callApiinObject(url=Urls.BULK_AVAILABLE_LIST,onResponse=this,hashMap =jsonObject,class_=VehicleModel.Data::class.java,showprogressbar=true,activity=this)


    }

    fun gotoVehicleSubtypes(url:String){
        if(addNewVehicleActivity.model!!.vehicle_type == ""){
            toast(resources.getString(R.string.please_select_vehicle_type))
        }else{
            val bundle=Bundle()
            bundle.putString("url",url)
            if (url==Urls.GET_VEHICLE_TYPES)
                bundle.putString("type_id",addNewVehicleActivity.model!!.vehicle_type)
            Redirection().goToListingPage(activity = this,bundle = bundle)
        }
    }

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)




        when (methodtype) {



            Urls.BULK_AVAILABLE_LIST->{

                val result=result as VehicleModel.Data

                val adapter = UniverSalBindAdapter(pick.com.app.R.layout.custom_add_vehicle)

                VehicleModel.Data.BulkAvailability.bulksize=result.data.size
                VehicleModel.Data.BulkAvailability.univerSalBindAdapter=adapter
                if (addNewVehicleActivity.model!!.vehicle_availabality.size==0)
                addNewVehicleActivity.model!!.vehicle_availabality.add(VehicleModel.Data.BulkAvailability())

                for (item in addNewVehicleActivity.model!!.vehicle_availabality){
                    val model= VehicleModel.Data.Seaters()
                    model.avail_id= item.avail_id
                    model.avail_title= item.avail_name
                    arrlistBultAvalibilit.add(model)

                }
                recycleviwe.adapter = adapter
                adapter.addAll(addNewVehicleActivity.model!!.vehicle_availabality as ArrayList<Any>)
            }

            Urls.ADD_VECHICLE, Urls.UPDATE_VECHICLE->{

                val result=result as VehicleModel

                if (result.status==1)
                gotoshowMessage(result.message,result.description,null)
                else
                    showMessage(result.message,result.description)



            }

        }
    }

    override fun getImage(images: File, title: String) {
        super.getImage(images, title)
        addNewVehicleActivity.model!!.vehicle_imagefile = File(images.path)
        loadImage(profile_image, images.path)
        //binding.invalidateAll()
    }

    fun removePreview(view: View) {
        addNewVehicleActivity.model!!.insurance_policyFile = File("")
        addNewVehicleActivity.model!!.insurance_policy=""
        addNewVehicleActivity.model!!.isLisancePreview = false
        addNewVehicleActivity.invalidateAll()
    }

    fun getInsurancePolocy(view: View, model: VehicleModel.Data) {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), IMG_REQUEST)

        model.isLisancePreview = true




//        ChooserDialog().with(view.context)
//            .withFilter(false, false, "jpg", "jpeg", "png", "pdf", "doc", "docx")
//            .withStartFile(Environment.getExternalStorageDirectory().absolutePath)
//
//            .withChosenListener { path, _ ->
//
//                addNewVehicleActivity.model!!.insurance_policyFile = File(path)
//                addNewVehicleActivity.model!!.insurance_policy=File(path).name
//                model.isLisancePreview = true
//               addNewVehicleActivity.invalidateAll()
//                insurance_image.setImageBitmap(generateImageFromPdf(Uri.fromFile(model.insurance_policyFile)))
//            }
//            .build()
//            .show()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addNewVehicleActivity = DataBindingUtil.setContentView(this, pick.com.app.R.layout.add_vehicle_layout)
        supportActionBar!!.hide()
        val toolbarCustom =
            ToolbarCustom(
                ToolbarCustom.lefticon,
                resources.getString(R.string.add_new_vehicle),
                ToolbarCustom.NoIcon,
                ToolbarCustom.NoIcon
            )

        left_Icon.onClick { onBackPressed() }
        var model= VehicleModel.Data()
        if (intent.extras!!.get("model")!=null) {
             model = (intent.extras!!.get("model") as VehicleModel.Data)
            loadImage(profile_image, model.upload_url+"vehicle_images/"+model.vehicle_image)
            url=Urls.UPDATE_VECHICLE
            toolbarCustom.title=resources.getString(R.string.update_vehilce)
            model.dayWise=model.setdayWise(model)

        }
        addNewVehicleActivity.toolbar = toolbarCustom
        addNewVehicleActivity.model = model
        addNewVehicleActivity.activity = this
        onVehicleSpinner=this
        activity=this
        val owenr_type=SessionManager.getLoginModel(this).data.user_type
        addNewVehicleActivity.model!!.isOwner_Company=(owenr_type=="oc")
        val pattern = "^[png]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}\$"
        addNewVehicleActivity.invalidateAll()
        getBulkAvalibity()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null
            && data.getData()!=null){


            var bitmap:Bitmap



            var path:Uri=data.data

            val file = File(path.path)
//            val os = BufferedOutputStream(FileOutputStream(file))
//
//            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
//            os.close()
            addNewVehicleActivity.model!!.insurance_policyFile = file
                addNewVehicleActivity.model!!.insurance_policy=file.name



               addNewVehicleActivity.invalidateAll()

        }
    }


    fun changeDaywiseCalue(isDayWise: Boolean) {


        addNewVehicleActivity.model!!.dayWise = isDayWise
        addNewVehicleActivity.invalidateAll()

    }

    var url=Urls.ADD_VECHICLE

    fun addNewVehicle(view: View) {


        if (VehicleModel().validation(view, addNewVehicleActivity.model!!,url)&&VehicleModel().bulkVilidation(view,VehicleModel.Data.BulkAvailability.univerSalBindAdapter.arrraylist as ArrayList<VehicleModel.Data.BulkAvailability>)){
            addNewVehicleActivity.model!!.vehicle_availabality=VehicleModel.Data.BulkAvailability.univerSalBindAdapter.arrraylist as ArrayList<VehicleModel.Data.BulkAvailability>
            ApiServices<VehicleModel>().callApi(
                url,
                this,
                VehicleModel().gethashMApAddVEhicle(view,addNewVehicleActivity.model!!),
                VehicleModel::class.java,
                true,
                this
            )
        }

    }


}
