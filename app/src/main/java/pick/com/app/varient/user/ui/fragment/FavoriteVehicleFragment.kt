package pick.com.app.varient.user.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.fav_vehicle_fragment.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.BaseFragment
import pick.com.app.databinding.FavVehicleFragmentBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class FavoriteVehicleFragment : BaseFragment(), onResponse {




    var modeldata= BookingModel()
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {



        when(methodtype){
            Urls.FAVOURITE_VEHICLE->{
                var result=result as BookingModel

                if (result.status==1){

                   activity.toast(result.description)
                    myFavoriteVehicle()
                }else{
                    onError(result.description)
                }
            }

            Urls.MY_FAVOURITE_VEHICLE->{
                val result = result as  BookingModel
                if(result.status == 1){
                    adpater.removeAll()
                    adpater.addAll(result.getData() as ArrayList<Any>)

                    textViewNoDataFound.hide()
                    recycleview.show()
                }
                else{
                    recycleview.hide()
                    textViewNoDataFound.show()
                    textViewNoDataFound.text=result.description

                }

            }

        }




    }

    override fun onError(error: String?) {

    }


    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): FavoriteVehicleFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = FavoriteVehicleFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun manageVechile(data: BookingModel.Data,view: ImageView){

       manageFavVehicle(data.vehicle_id!!,object: onResponse {
            override fun <T : Any?> onSucess(result: T, methodtype: String?) {

                data!!.is_favourite=if (data.is_favourite==1) 0 else 1
                if (data.is_favourite == 1)
                    view.setImageResource(R.drawable.profile_fav_icon)
                else
                    view.setImageResource(R.drawable.fav_icon)


                myFavoriteVehicle()

            }

            override fun onError(error: String?) {

            }


        })
    }
    lateinit var  model: FavVehicleFragmentBinding
    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // binding=   DataBindingUtil.inflate<ActivityProfileBinding>(inflater,pick.com.app.R.layout.activity_profile,container,false)
        model = DataBindingUtil.inflate(inflater, R.layout.fav_vehicle_fragment, container, false)
        return model.root

    }
    val adpater=UniverSalBindAdapter(R.layout.row_layout_favvehcil)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycleview.layoutManager= GridLayoutManager(activity, 3)

        recycleview.adapter=adpater

        myFavoriteVehicle()

    }



    fun myFavoriteVehicle() {
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(BaseActivity.activity).data.user_id


        ApiServices<BookingModel>().callApi(
            Urls.MY_FAVOURITE_VEHICLE, this, hashmap, BookingModel::class.java, true, activity!!
        )
    }
}