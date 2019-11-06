package pick.com.app.varient.user.ui.fragment.vehicle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.UniverSalBindAdapter
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_vehicle_listing_layout.*
import pick.com.app.R
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.core.RVPageScrollState
import pick.com.app.uitility.core.RVPagerSnapHelperListenable
import pick.com.app.uitility.core.RVPagerStateListener
import pick.com.app.uitility.core.VisiblePageState
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.pojo.VehicleSearchData
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls


class VehicleMapListing : Fragment(), OnMapReadyCallback, UniverSalBindAdapter.ItemAdapterListener, onResponse {

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        var result = result as VehicleSearchData

        if (result.status == "1") {

            for (item in result.data!!) {

                    item.vehicles.pick_adddress = item.location_address
                    item.vehicles.location_latitude = item.location_latitude
                    item.vehicles.location_longitude = item.location_longitude
                    if (!searchBoomodel.isdiferentcity) {
                        item.vehicles.is_different_city="2"

                    }else{
                        item.vehicles.is_different_city="1"
                        item.vehicles.drop_location_address = item.vehicles.drop_location[0].drop_location_address!!
                    }
                    adpater.add(item.vehicles)



            }

            var model=adpater.arrraylist[0] as VehicleSearchData.Data.Vehicles

            setMarkerList(LatLng(model.location_latitude!!,model.location_longitude!!))


            try {
                RVPagerSnapHelperListenable().attachToRecyclerView(recyclerView, object : RVPagerStateListener {
                    override fun onPageScroll(pagesState: List<VisiblePageState>) {
                        for (pageState in pagesState) {
                            /* val vh = visssew.recyclerView.findContainingViewHolder(pageState.view) as IDemoVH
                             vh.setRealtimeAttr(pageState.index, pageState.viewCenterX.toString(), pageState.distanceToSettled, pageState.distanceToSettledPixels)*/
                        }
                    }

                    override fun onScrollStateChanged(state: RVPageScrollState) {
                    }

                    override fun onPageSelected(index: Int) {
                        /*val vh = visssew.recyclerView.findViewHolderForAdapterPosition(index) as IDemoVH?
                        vh?.onSelected()*/
                        Log.e("", "")
                        var model=adpater.arrraylist[index] as VehicleSearchData.Data.Vehicles


                        setMarkerList(LatLng(model.location_latitude!!,model.location_longitude!!))
                    }
                })
            } catch (e: Exception) {
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

    override fun onMapReady(p0: GoogleMap?) {
        gMap = p0;
        gMap!!.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    lateinit var visssew: View
    lateinit var searchBoomodel: SearchBookingModel

    companion object {
        val ARG_PAGE = "ARG_PAGE"
        lateinit var mScrssollView: NestedScrollView
        val model = "model"

        fun newInstance(layout: NestedScrollView, searchBoomodel: SearchBookingModel): VehicleMapListing {
            val args = Bundle()
            args.putSerializable(model, searchBoomodel)
            mScrssollView = layout
            val fragment = VehicleMapListing()
            fragment.arguments = args
            return fragment
        }
    }

    var gMap: GoogleMap? = null
    var gMapView: MapView? = null

    // Inflat  MapView gMapView;e the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        visssew = inflater.inflate(pick.com.app.R.layout.map_vehicle_listing_layout, container, false)
        return visssew
    }

    private var dragThreshold = 10
    var downX = 0
    var downY = 0;

    var layout = 0
    var arrayList = ArrayList<String>()
    lateinit var adpater: UniverSalBindAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(pick.com.app.R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        searchBoomodel = arguments!!.getSerializable(model) as SearchBookingModel




    }


    fun setMarkerList(selecdedMArker: LatLng) {
        gMap!!.clear()
        var oldlatLng = LatLng(0.0, 0.0)
        var position=-1
        for (item in  adpater.arrraylist) {
            position++
            var item = adpater.arrraylist[position] as VehicleSearchData.Data.Vehicles

            if (oldlatLng != LatLng(item.location_latitude!!, item.location_longitude!!)) {
                var marker = gMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(item.location_latitude!!, item.location_longitude!!))



                )

            if (selecdedMArker == LatLng(item.location_latitude!!, item.location_longitude!!)) {

                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon_select))

                gMap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(item.location_latitude!!, item.location_longitude!!), 14F
                    )
                )
            } else {
                if (oldlatLng != LatLng(item.location_latitude!!, item.location_longitude!!))
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon))
            }
            }
            oldlatLng = LatLng(item.location_latitude!!, item.location_longitude!!)
        }

    }


    override fun onResume() {
        super.onResume()
        callapi(visssew)
    }

    fun callapi(view: View) {

        adpater = UniverSalBindAdapter(pick.com.app.R.layout.custom_vehicle_listing_user, this)

        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)


        recyclerView.adapter = adpater
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



        ApiServices<VehicleSearchData>().callApi(
            Urls.SEARCH_VEHICLE, this,
            SearchBookingModel().searchVehicleBooking(searchBoomodel, view),
            VehicleSearchData::class.java, true, activity!!
        )

    }


}


