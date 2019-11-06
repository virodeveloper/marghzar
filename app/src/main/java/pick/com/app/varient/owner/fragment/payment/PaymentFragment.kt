package pick.com.app.varient.owner.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.adapter.UniverSalBindAdapter
import kotlinx.android.synthetic.main.fav_vehicle_fragment.*
import pick.com.app.R

class PaymentFragment : Fragment(){


    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): PaymentFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PaymentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.payment_list_layout, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycleview.layoutManager= GridLayoutManager(activity, 1)
        val adpater= UniverSalBindAdapter(R.layout.custom_payment_row)
        recycleview.adapter=adpater

        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
        adpater.add(Any())
    }
}
