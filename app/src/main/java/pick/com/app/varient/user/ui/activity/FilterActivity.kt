package pick.com.app.varient.user.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter
import kotlinx.android.synthetic.main.activity_filter_screen.*
import org.json.JSONObject
import pick.com.app.base.BaseActivity
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.varient.user.pojo.FilterModel
import pick.com.app.varient.user.pojo.FilterModel.Companion.hashmap
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class FilterActivity :BaseActivity(){

    lateinit var transmission_filter : LinearLayout
    lateinit var manual_filter : LinearLayout

    lateinit var binding: pick.com.app.databinding.ActivityFilterScreenBinding
    var vehicleModel = VehicleModel()

    override fun <T> onSucess(rerrsult: T, methodtype: String?) {
        super.onSucess(rerrsult, methodtype)
        var arry =  UniverSalBindAdapter(pick.com.app.R.layout.filter_row_layout)
        if(FilterModel.filtermodel.data.size == 0)
          FilterModel.filtermodel=rerrsult as FilterModel
        vehicle_type_recycleview.adapter = arry

        arry.addAll( FilterModel.filtermodel.data[1].filterdata as ArrayList<Any>)
    }

    fun reset(view: View){
        FilterModel.filtermodel=FilterModel()
        recreate()
    }
     var savedInstanceState: Bundle? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        hashmap=HashMap<String,Any>()
        var jsonObject=JSONObject()
        jsonObject.put("","")
        binding = DataBindingUtil.setContentView(this, pick.com.app.R.layout.activity_filter_screen)
        ApiServices<VehicleModel.Data>().callApiinObject(Urls.GET_FILTERS,this,jsonObject,
            FilterModel::class.java,true,this)

        transmission_filter = findViewById(pick.com.app.R.id.transmission_filter)
        manual_filter = findViewById(pick.com.app.R.id.manual_filter)

        transmission_filter.setOnClickListener {
            var colorhh =Color.parseColor("#f2f2f2")
            val background = transmission_filter.getBackground()
            if (background is ColorDrawable) {
                var color = (background as ColorDrawable).color

                if (colorhh == (background as ColorDrawable).color) {

                    FilterModel.filtermodel.data[0].filterdata!![0].isitemSelected=false
                    transmission_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.white));


                } else {

                    FilterModel.filtermodel.data[0].filterdata!![0].isitemSelected=true

                    transmission_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.filter_color));

                }

            }
        }

if (FilterModel.filtermodel.data.size!=0)
        if ( FilterModel.filtermodel.data[0].filterdata!![0].isitemSelected!!)
            transmission_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.filter_color));
        if (FilterModel.filtermodel.data.size!=0)
        if ( FilterModel.filtermodel.data[0].filterdata!![1].isitemSelected!!)
            manual_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.filter_color));


        manual_filter.setOnClickListener {
            var colorhh =Color.parseColor("#f2f2f2")
            val background = manual_filter.getBackground()
            if (background is ColorDrawable) {
                var color = (background as ColorDrawable).color

                if (colorhh == (background as ColorDrawable).color) {
                    hashMap.remove("transmission_id[1]")
                    FilterModel.filtermodel.data[0].filterdata!![1].isitemSelected=false
                    manual_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.white));


                } else {
                    hashMap["transmission_id[1]"] =  FilterModel.filtermodel.data[0].filterdata!![0].transmission_id.toString()
                    FilterModel.filtermodel.data[0].filterdata!![1].isitemSelected=true

                    manual_filter.setBackground(ContextCompat.getDrawable(this, pick.com.app.R.color.filter_color));

                }

            }

        }


    }

    var hashMap=HashMap<String,Any>()
var subtype_id=""
var transmission_id=""
    fun setFilter(view: View){



            for (index in 0 until FilterModel.filtermodel.data[0].filterdata!!.size){
                if (FilterModel.filtermodel.data[0].filterdata!![index].isitemSelected!!){
                    if (transmission_id=="")
                        transmission_id=FilterModel.filtermodel.data[0].filterdata!![index].transmission_id!!
                    else
                        transmission_id=transmission_id+","+FilterModel.filtermodel.data[0].filterdata!![index].transmission_id
                }


            }

        for (subyeitem in  FilterModel.filtermodel.data[1].filterdata!!){

            for (index in 0 until subyeitem.subtypes!!.size){
                if (subyeitem.subtypes!![index].isitemSelected!!){
                    if (subtype_id=="")
                    subtype_id=subyeitem.subtypes!![index].subtype_id!!
                    else
                        subtype_id=subtype_id+","+subyeitem.subtypes!![index].subtype_id!!
                }


            }
        }



        hashMap["transmission_id"]=transmission_id
        hashMap["subtype_id"]=subtype_id

        VehicleListingUser.onFilter.getFilter(hashMap)
        finish()

    }

}
