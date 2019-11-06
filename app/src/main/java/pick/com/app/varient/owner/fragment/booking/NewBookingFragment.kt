package pick.com.app.varient.owner.fragment.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.fav_vehicle_fragment.*
import pick.com.app.R
import pick.com.app.base.BaseFragment
import pick.com.app.interfaces.onItemClick
import pick.com.app.uitility.custom.ItemOffsetDecoration
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.owner.pojo.ImageModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class NewBookingFragment : BaseFragment(), UniverSalBindAdapter.ItemAdapterListener, onItemClick {
    override fun getItemClick(id: Any, status: String) {

        acceptReject(id as BookingModel.Data, status)

    }

    override fun onItemSelected(model: Any) {
        val bookingBundle = Bundle()
        bookingBundle.putSerializable("model", model as BookingModel.Data)
        Redirection().goToBookingDetail(false, activity!!, bookingBundle)
    }


    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        when (methodtype) {

            Urls.GET_NEWBOOKING, Urls.GET_OngoingBookingsOwner, Urls.GET_PastBookingsOwner -> {

                var result = result as BookingModel


                if (result.getData() != null && result.getData()!!.isNotEmpty()) {
                    if (!adpater!!.arrraylist.isEmpty())
                        adpater!!.removeAll()
                    adpater!!.addAll(result.getData() as ArrayList<Any>)

                    textViewNoDataFound!!.hide()

                    recycleview!!.show()
                } else {
                    recycleview!!.hide()
                    textViewNoDataFound.text = result.description
                    textViewNoDataFound.show()
                }
                /*adpater.add(BookingModelUser.Data().apply {
                    show_accept_reject = true

                })*/
            }

            Urls.CHANGE_BOKING_STATUS -> {
                var result = result as BookingModel

                if (result.status == 1) {
                    activity.toast(result.description)
                    adpater!!.arrraylist.remove(data)
                    getBooking(Urls.GET_NEWBOOKING)

                } else {
                    showMessageWithError(message = result.description)
                }
            }


        }
    }

    lateinit var imageModel: ImageModel
    lateinit var textViewNoDataFound: TextView


    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): NewBookingFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = NewBookingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    internal var view: View? = null
    internal var recycleview: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view = inflater.inflate(R.layout.fav_vehicle_fragment, container, false)
        recycleview = view!!.findViewById(R.id.recycleview)
        textViewNoDataFound = view!!.findViewById(R.id.textViewNoDataFound)
        var itemDecoration = ItemOffsetDecoration(activity!!, R.dimen._10sdp);
        recycleview!!.addItemDecoration(itemDecoration)
        return view

    }

    override fun onResume() {
        super.onResume()

        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing=false
            callApi()
        }

        if (view != null) {
            page = getArguments()!!.getInt(ARG_PAGE, 0)



            callApi()
        }


    }

    private fun callApi() {
        when (page) {
            1 -> {

                getBooking(Urls.GET_NEWBOOKING)


            }
            2 -> {


                getBooking(Urls.GET_OngoingBookingsOwner, isDEtaileShow = true)


                /*   adpater = UniverSalBindAdapter(R.layout.custom_owner_my_booking, this)
                       recycleview.adapter = adpater
                       adpater.add(BookingModelUser.Data().apply {
                           bookingStatus = 1
                       })


                       adpater.add(BookingModelUser.Data().apply {
                           bookingStatus = 2
                       })

                       adpater.add(BookingModelUser.Data().apply {
                           bookingStatus = 3
                       })

                       adpater.add(BookingModelUser.Data().apply {
                           bookingStatus = 4
                       })

                       adpater.add(BookingModelUser.Data().apply {
                           bookingStatus = 5
                       })*/


            }
            else -> {
                /* adpater = UniverSalBindAdapter(R.layout.custom_owner_my_booking, listener = this)
                    recycleview!!.adapter = adpater
                    adpater!!.add(BookingModel.Data().apply {
                        booking_status = 6
                    })

                    adpater!!.add(BookingModel.Data().apply {
                        booking_status = 7
                    })

                    adpater!!.add(BookingModel.Data().apply {
                        booking_status = 8
                    })*/
                getBooking(Urls.GET_PastBookingsOwner, isDEtaileShow = true)


            }
        }
    }

    var page = 0

    fun getBooking(url: String, isDEtaileShow: Boolean = false) {
        if (adpater != null) {
            adpater!!.removeAll()
        }
        var json = HashMap<String, Any>()
        json["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        ApiServices<BookingModel>().callApi(url, this, json, BookingModel::class.java, true, activity!!)
        adpater = if (isDEtaileShow)
            UniverSalBindAdapter(R.layout.custom_owner_my_booking, listener = this)
        else
            UniverSalBindAdapter(R.layout.custom_owner_my_booking, itemclick = this)
        recycleview!!.adapter = adpater


    }


    var adpater: UniverSalBindAdapter? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    var data = BookingModel.Data()
    fun acceptReject(data: BookingModel.Data, status: String) {
        this.data = data

        var hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(activity).data.user_id
        hashmap["booking_id"] = data.booking_id!!
        hashmap["booking_status"] = status


        adpater!!.arrraylist.remove(data)

        ApiServices<BookingModel>().callApi(
            Urls.CHANGE_BOKING_STATUS,
            this,
            hashmap,
            BookingModel::class.java,
            true,
            activity!!
        )

    }
}
