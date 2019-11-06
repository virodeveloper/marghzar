package pick.com.app.varient.owner.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pick.com.app.R
import pick.com.app.varient.owner.activity.ManageVehicleActivity
import pick.com.app.varient.user.ui.fragment.FavoriteVehicleFragment

class ManageVehicleFragment : Fragment(){


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

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fav_vehicle_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        startActivity(Intent(activity,ManageVehicleActivity::class.java))

    }
}
