package pick.com.app.varient.user.ui.fragment.vehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adapter.UniverSalBindAdapter
import kotlinx.android.synthetic.main.dialog_add_wallet.*
import kotlinx.android.synthetic.main.fav_vehicle_fragment.view.*
import pick.com.app.R
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.user.pojo.FilterModel
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.pojo.VehicleSearchData
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ListGridFragment : Fragment(),UniverSalBindAdapter.ItemAdapterListener, onResponse {
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        var result=result as VehicleSearchData

        if (result.status=="1"){

            for (item in result.data!!){

                    item.vehicles.pick_adddress=item.location_address

                    if (!searchBoomodel.isdiferentcity) {
                        item.vehicles.is_different_city="2"

                    }else{
                        item.vehicles.is_different_city="1"
                        item.vehicles.drop_location_address = item.vehicles.drop_location[0].drop_location_address!!
                    }
                    adpater.add(item.vehicles)

            }
        }

    }

    override fun onError(error: String?) {

    }

    override fun onItemSelected(model: Any) {

var bundel=Bundle()
        bundel.putSerializable("model",model as VehicleSearchData.Data.Vehicles)

        bundel.putSerializable("searchodel",searchBoomodel)
        Redirection().goToVehicleDetail(activity= activity!!,bundle=bundel)

    }

    lateinit var visssew: View
    companion object {
        val ARG_PAGE = "ARG_PAGE"
        val model = "model"

         fun newInstance(layout: Int,searchBoomodel:SearchBookingModel): ListGridFragment {
            val args = Bundle()
            args.putSerializable(model, searchBoomodel)
            args.putInt(ARG_PAGE, layout)
            val fragment = ListGridFragment()
            fragment.arguments = args
            return fragment
        }
    }


    fun callapi(view : View){

        /*location_address:fadsfasdfsa
location_latitude:fasdfasfasdf
location_longitude:fasdfasdfas
seater_id:1
start_date_time:123123123
end_date_time:1231231
is_diferent_city:1
vehicle_subtype:1
drop_location:123123
is_delivery_atdoor:123123123*/
        adpater= UniverSalBindAdapter(layout,this)
        visssew.recycleview.adapter=adpater

var hashMap =  SearchBookingModel().searchVehicleBooking( searchBoomodel, view)
        hashMap.putAll(FilterModel.hashmap)
        ApiServices<VehicleSearchData>().callApi(
            Urls.SEARCH_VEHICLE,this,
            hashMap ,
            VehicleSearchData::class.java,true,activity!!)

    }


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        visssew= inflater.inflate(R.layout.fav_vehicle_fragment, container, false)
        return  visssew
    }


    var layout = 0
    lateinit var adpater: UniverSalBindAdapter
    lateinit var searchBoomodel:SearchBookingModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layout = arguments!!.getInt(ARG_PAGE, 0)
        searchBoomodel=arguments!!.getSerializable(model) as SearchBookingModel



    }

    override fun onResume() {
        super.onResume()
        callapi(visssew)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

