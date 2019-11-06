package pick.com.app.varient.user.ui.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.adapter.UniverSalBindAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.vehicle_listing_user.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.VehicleListingUserBinding
import pick.com.app.interfaces.onFilter
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.varient.user.pojo.FilterModel
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.ui.fragment.booking.HomeFragment
import pick.com.app.varient.user.ui.fragment.vehicle.ListGridFragment
import pick.com.app.varient.user.ui.fragment.vehicle.VehicleMapListing




class VehicleListingUser: BaseActivity() , onFilter {
    override fun getFilter(o: Any?) {

        Log.e("","")
        val bundle = Bundle()
        bundle.putSerializable("model", searchmodel)
FilterModel.hashmap = o as HashMap<String, Any>
        finish()
        Redirection().goToVechileListingUser(activity = activity!!, bundle = bundle, isfinish = true)
    }

    lateinit var visssew: View
    companion object {
        val ARG_PAGE = "ARG_PAGE"
        lateinit var searchmodel:  SearchBookingModel
        lateinit var onFilter:onFilter

        fun newInstance(layout: Int): HomeFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, layout)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var islistingActive=true
    var isGrindActive=false
    var isMapActive=false

    fun getSortingDailog(view: View){
        MaterialDialog(this).show {
            listItems(pick.com.app.R.array.sortarray) { dialog, index, text ->
                // Invoked when the user taps an item
                Log.e("","")
                val bundle = Bundle()
                searchmodel.sortting=text
                bundle.putSerializable("model", searchmodel)
                FilterModel.hashmap["sort"]=index

                finish()
                Redirection().goToVechileListingUser(activity = activity!!, bundle = bundle, isfinish = true)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.invalidateAll()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=   DataBindingUtil.setContentView(this, pick.com.app.R.layout.vehicle_listing_user)
        var bookingDetail = ToolbarCustom(ToolbarCustom.lefticon, getString(pick.com.app.R.string.Vehicle), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        binding.toolbar = bookingDetail

        searchmodel=intent.extras.get("model") as SearchBookingModel

        binding.searchodel=searchmodel


        binding.activiy=this
        left_Icon.onClick {
            onBackPressed()
        }
        onFilter=this

        goToFragment(fragment = ListGridFragment.newInstance(pick.com.app.R.layout.custom_vehicle_listing_user, binding.searchodel!!),framId= pick.com.app.R.id.frame_listing)





        val handler = Handler()
        handler.postDelayed({

            mScrollView.fullScroll(View.FOCUS_UP)
        }, 700)









      /*  mScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view = mScrollView.getChildAt(mScrollView.childCount - 1) as View

            val diff = view.bottom - (mScrollView.height + mScrollView
                .scrollY)

            if (diff == 0) {
                // your pagination code
             //   toast("call pagiantion")
            }
        }*/
    }



    lateinit var    binding:VehicleListingUserBinding
    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title

    var layout = 0
    lateinit var adpater: UniverSalBindAdapter

    fun showListing(){
        binding.activiy!!.islistingActive=true
        binding.activiy!!.isGrindActive=false
        binding.activiy!!.isMapActive=false
        binding.invalidateAll()
        goToFragment(fragment = ListGridFragment.newInstance(pick.com.app.R.layout.custom_vehicle_listing_user, binding.searchodel!!),framId= pick.com.app.R.id.frame_listing)

    }


    fun showMapListing(){
        binding.activiy!!.islistingActive=false
        binding.activiy!!.isGrindActive=false
        binding.activiy!!.isMapActive=true
        binding.invalidateAll()
        goToFragment(fragment = VehicleMapListing.newInstance(
            mScrollView
                    ,binding.searchodel!!),framId= pick.com.app.R.id.frame_listing)

    }

    fun getFilterModel(o: Any?) {

        toast(o as String)
        Log.e("","")
    }


    fun goToFilterActivity(){




       Redirection().goToFilterActivity(activity = this)
    }

    fun showGridListing(){
        binding.activiy!!.islistingActive=false
        binding.activiy!!.isGrindActive=true
        binding.activiy!!.isMapActive=false
        binding.invalidateAll()
        goToFragment(fragment = ListGridFragment.newInstance(pick.com.app.R.layout.custom_vehicle_grid_view, binding.searchodel!!),framId= pick.com.app.R.id.frame_listing)

    }

}
