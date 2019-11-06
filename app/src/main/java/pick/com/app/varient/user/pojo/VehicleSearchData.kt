package pick.com.app.varient.user.pojo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.BR
import pick.com.app.MainActivity
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.CommonActivity
import pick.com.app.databinding.CustomVehicleListingUserBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.custom.CircleProgressBar
import pick.com.app.uitility.helpper.Redirection
import java.io.Serializable
import kotlin.coroutines.coroutineContext

class VehicleSearchData :Serializable{

companion object {
    var isbackenable= false
}

    /**
     * status : 0
     * message : Success
     * data : [{"location_address":"A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India","location_latitude":"26.8913782","location_longitude":"75.7721017","from_date_time":"123123123","to_date_time":"1231231","is_different_city":"0","delivery_at_door":"0","vehicles":[{"vehicle_id":"53","user_id":"8","model_id":"5","model_name":"Hyundai eOn","seater_id":"3","seater":"3","vehicle_year":"2018","fuel_type_id":"2","fuel_type":"diesel","vehicle_type":"1","vehicle_type_name":"Small","vehicle_subtype":"12","vehicle_subtype_name":"Hatchback","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"1","is_vehicle_available":"2","vehicle_charges_one_day":"120","one_day_free_kilometer":"0","per_kilometer_charge":"0","insurance_policy":"1551263023XXbaxtertestdocument.pdf","vehicle_image":"1551263023XXimg-20190227-wa0007.jpg","vehicle_availabality":[{"avail_id":"1","avail_name":"avail1","avail_price":"355.00"},{"avail_id":"3","avail_name":"avail3","avail_price":"988.00"}],"upload_url":"https://in.endivesoftware.com/sites/pick/uploads/"}]},{"location_address":"35, Mahadev Nagar, Swage Farm, Ganesh Nagar, Narendra Nagar, Jaipur, Rajasthan 302019, India","location_latitude":"26.888157","location_longitude":"75.77758490000001","from_date_time":"123123123","to_date_time":"1231231","is_different_city":"0","delivery_at_door":"1","vehicles":[{"vehicle_id":"52","user_id":"46","model_id":"1","model_name":"Audi Audi1","seater_id":"2","seater":"2","vehicle_year":"2018","fuel_type_id":"3","fuel_type":"cng","vehicle_type":"1","vehicle_type_name":"Small","vehicle_subtype":"11","vehicle_subtype_name":"SUV","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"1","is_vehicle_available":"1","vehicle_charges_one_day":"2580","one_day_free_kilometer":"147","per_kilometer_charge":"369","insurance_policy":"1551262348XXinsurance_policy.png","vehicle_image":"1551262348XXvehicleimage.png","vehicle_availabality":[{"avail_id":"1","avail_name":"avail1","avail_price":"855.00"},{"avail_id":"2","avail_name":"avail2","avail_price":"522.00"},{"avail_id":"3","avail_name":"avail3","avail_price":"44.00"}],"upload_url":"https://in.endivesoftware.com/sites/pick/uploads/"},{"vehicle_id":"54","user_id":"46","model_id":"5","model_name":"Hyundai eOn","seater_id":"5","seater":"5","vehicle_year":"2014","fuel_type_id":"1","fuel_type":"petrol","vehicle_type":"2","vehicle_type_name":"Medium","vehicle_subtype":"16","vehicle_subtype_name":"Hatchback","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"1","is_vehicle_available":"1","vehicle_charges_one_day":"3000","one_day_free_kilometer":"75","per_kilometer_charge":"300","insurance_policy":"1551273316XXinsurance_policy.png","vehicle_image":"1551273316XXvehicleimage.png","vehicle_availabality":[{"avail_id":"1","avail_name":"avail1","avail_price":"15000.00"},{"avail_id":"1","avail_name":"avail1","avail_price":"4000.00"},{"avail_id":"2","avail_name":"avail2","avail_price":"8000.00"},{"avail_id":"3","avail_name":"avail3","avail_price":"12000.00"}],"upload_url":"https://in.endivesoftware.com/sites/pick/uploads/"},{"vehicle_id":"55","user_id":"46","model_id":"2","model_name":"Mercedes MN3","seater_id":"3","seater":"3","vehicle_year":"2018","fuel_type_id":"2","fuel_type":"diesel","vehicle_type":"2","vehicle_type_name":"Medium","vehicle_subtype":"16","vehicle_subtype_name":"Hatchback","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"0","is_vehicle_available":"1","vehicle_charges_one_day":"500","one_day_free_kilometer":"0","per_kilometer_charge":"0","insurance_policy":"1551274886XXinsurance_policy.pdf","vehicle_image":"1551274886XXvehicleimage.png","vehicle_availabality":[{"avail_id":"1","avail_name":"avail1","avail_price":"1546.00"}],"upload_url":"https://in.endivesoftware.com/sites/pick/uploads/"}]}]
     */

    var status: String? = null
    var message: String? = null
    var data: List<Data>? = null

    class Data :Serializable{
        /**
         * location_address : A-68,69 Sunder Singh Bhandari Nagar, Swej Farm, Sodala, Jaipur, Rajasthan 302019, India
         * location_latitude : 26.8913782
         * location_longitude : 75.7721017
         * from_date_time : 123123123
         * to_date_time : 1231231
         * is_different_city : 0
         * delivery_at_door : 0
         * vehicles : [{"vehicle_id":"53","user_id":"8","model_id":"5","model_name":"Hyundai eOn","seater_id":"3","seater":"3","vehicle_year":"2018","fuel_type_id":"2","fuel_type":"diesel","vehicle_type":"1","vehicle_type_name":"Small","vehicle_subtype":"12","vehicle_subtype_name":"Hatchback","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"1","is_vehicle_available":"2","vehicle_charges_one_day":"120","one_day_free_kilometer":"0","per_kilometer_charge":"0","insurance_policy":"1551263023XXbaxtertestdocument.pdf","vehicle_image":"1551263023XXimg-20190227-wa0007.jpg","vehicle_availabality":[{"avail_id":"1","avail_name":"avail1","avail_price":"355.00"},{"avail_id":"3","avail_name":"avail3","avail_price":"988.00"}],"upload_url":"https://in.endivesoftware.com/sites/pick/uploads/"}]
         */

        var status: Int? =0
        var message: String? = null
        var data=Vehicles()
        var location_address: String? = null
        var location_latitude: Double? = null
        var location_longitude: Double? = null
        var from_date_time: String? = null
        var to_date_time: String? = null
        var is_different_city: String? = null
        var delivery_at_door: String? = null
        var vehicles= Vehicles()


        class Vehicles :Serializable,BaseObservable(){


            fun isUserHaveCompany( url:String):Boolean{

                return (url=="")
            }
            var policies=ArrayList<Policyes>()
var is_different_city="1"
var drop_location_address=""

            /*  "payable_amount": 154,
                    "refundable_charge": 25,
                    "refueling_charge": 173,*/


            class Policyes :Serializable{
                var name=""
                var description=""
            }

fun isDifferentCity(different:String):Boolean{

    return  (different=="1")

}



             var drop_location = ArrayList<DropLocationsBean>()

            class DropLocationsBean : Serializable{
                override fun toString(): String {
                    return drop_location_address.toString()
                }
                var drop_id: String? = null
                var drop_location_address: String? = null
                var drop_pin_code: String? = null
                var detail_id: String? = null
                var user_id: String? = null
                var city_id: String? = null
            }
            companion object {
                @JvmStatic
                @BindingAdapter("loadImageData")
                fun loadImageData(profile_image: ImageView, url: String) {

                    var bining:CustomVehicleListingUserBinding

                    Log.e("url",url)
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
                   /* Glide.with(profile_image)
                        .load(url)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        .into(profile_image)*/


                }


                @JvmStatic
                @BindingAdapter("bookNow")
                fun bookNow(view: View, model: Vehicles) {

                    view.onClick {

                        var bundle= Bundle()

                        bundle.putSerializable("model",model)

                        Redirection().goToFillBooking(activity =view.context ,bundle=bundle)

                    }

                }


                @JvmStatic
                @BindingAdapter("setFav")
                fun setFav(view: ImageView, model: Vehicles) {



                    if (model.is_favourite == 1)
                        view.setImageResource(R.drawable.profile_fav_icon)
                    else
                        view.setImageResource(R.drawable.fav_icon)


                    view.onClick {

                        (BaseActivity.activity as BaseActivity).manageFavVehicle(model.vehicle_id!!,object: onResponse{
                            override fun <T : Any?> onSucess(result: T, methodtype: String?) {

                                model.is_favourite=if (model.is_favourite==1) 0 else 1
                                if (model.is_favourite == 1)
                                    view.setImageResource(R.drawable.profile_fav_icon)
                                else
                                    view.setImageResource(R.drawable.fav_icon)
                            }

                            override fun onError(error: String?) {

                            }


                        })

                    }

                }

            }


            var location_latitude: Double? = null
            var rating: Float? = null
            var pick_location_address: String? = null
            var location_longitude: Double? = null
            var vehicle_id=""
            @Bindable
            var is_favourite: Int? = 0
                set(value) {
                    if (field!=value){

                        field=value
                        notifyPropertyChanged(BR.is_favourite)

                    }
                }


fun isfav(data:Vehicles):Boolean{

    return  (data.is_favourite==0)

}


            var isfav: Boolean=false
                @Bindable
                set(value) {


                        field= is_favourite==1


                    // These methods take care of calling notifyPropertyChanged()

                }
            var refueling_charge: String? = null
            var payable_amount: String? = null


            var refundable_charge: String? = null
            var pick_adddress: String? = null
            var user_id: String? = null
            var model_id: String? = null
            var model_name: String? = null
            var seater_id: String? = null
            var seater: String? = null
            var vehicle_year: String? = null
            var fuel_type_id: String? = null
            var fuel_type: String? = null
            var vehicle_type: String? = null
            var vehicle_type_name: String? = null
            var vehicle_subtype: String? = null
            var vehicle_subtype_name: String? = null
            var vehicle_transmission: String? = null
            var vehicle_transmission_title: String? = null
            var vehicle_for=0

            fun getvehicle_for(vehicle_for:Int):Boolean{

                return (vehicle_for==1)
            }
            var is_vehicle_available: String? = null
            var vehicle_charges_one_day: String? = null
            var one_day_free_kilometer: String? = null
            var per_kilometer_charge: String? = null
            var insurance_policy: String? = null
            var vehicle_image: String? = null
            var upload_url: String? = null
            var vehicle_availabality: List<VehicleAvailabality>? = null


            class VehicleAvailabality :Serializable{
                /**
                 * avail_id : 1
                 * avail_name : avail1
                 * avail_price : 355.00
                 */
                fun getPerdayPrice(model: VehicleAvailabality):Int{

                    return (model.avail_price!!.toDouble()/model.noofdays).toInt()

                }

                var avail_id: String? = null
                var noofdays=0
                var avail_name: String? = null
                var avail_price: String? = null

            }



        }
        var policies:List<Policyes>?=null
        class Policyes :Serializable{
            var name=""
            var description=""
        }
    }
}
