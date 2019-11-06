package pick.com.app.varient.user.ui.fragment.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show
import kotlinx.android.synthetic.main.fav_vehicle_fragment.*
import pick.com.app.R
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.custom.ItemOffsetDecoration
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class NewBookingFragment : Fragment(), UniverSalBindAdapter.ItemAdapterListener, onResponse {



    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        var result = result as BookingModel

        if (result.getData()!=null && result.getData()!!.isNotEmpty()){
            if (!adpater.arrraylist.isEmpty())
                adpater.removeAll()
            adpater.addAll(result.getData() as ArrayList<Any>)

            textViewNoDataFound!!.hide()

            recycleview.show()
        }

        else {
            recycleview!!.hide()
            textViewNoDataFound.text=result.description
            textViewNoDataFound.show()
        }
    }

    override fun onError(error: String?) {

    }

    override fun onItemSelected( model : Any) {
        val bookingBundle = Bundle()
        bookingBundle.putSerializable("model",model as BookingModel.Data)
        Redirection().goToUserBookingDetail(false, activity!! , bookingBundle)
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(layout: Int): NewBookingFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, layout)
            val fragment = NewBookingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fav_vehicle_fragment, container, false)

    }


    var  layout=0
   lateinit var  adpater: UniverSalBindAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var itemDecoration =  ItemOffsetDecoration(activity!!, R.dimen._5sdp);
        recycleview.addItemDecoration(itemDecoration)

     }

    override fun onResume() {
        super.onResume()
        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing=false

            layout = getArguments()!!.getInt(ARG_PAGE, 0)
            if(layout == 1){
                getBooking(Urls.GET_OngoingBookingsUser)
            }else {
                getPastBooking(Urls.GET_PastBookingsUser)
            }
        }


        layout = getArguments()!!.getInt(ARG_PAGE, 0)
        if(layout == 1){
            getBooking(Urls.GET_OngoingBookingsUser)
        }else {
            getPastBooking(Urls.GET_PastBookingsUser)
        }
    }

    fun getBooking(url:String) {

        var json = HashMap<String, Any>()
        json["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        ApiServices<BookingModel>().callApi(url, this, json, BookingModel::class.java, true, activity!!)

        adpater= UniverSalBindAdapter(R.layout.custom_user_my_booking, this)
        recycleview.adapter=adpater

    }

    fun getPastBooking(url:String) {

        var json = HashMap<String, Any>()
        json["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        ApiServices<BookingModel>().callApi(url, this, json, BookingModel::class.java, true, activity!!)

        adpater= UniverSalBindAdapter(R.layout.custom_user_my_booking, this)
        recycleview.adapter=adpater


    }
}
