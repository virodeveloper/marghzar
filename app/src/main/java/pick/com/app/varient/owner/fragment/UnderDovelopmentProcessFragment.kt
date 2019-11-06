package pick.com.app.varient.owner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pick.com.app.R
import pick.com.app.varient.user.pojo.RegistrationModel

class UnderDovelopmentProcessFragment : Fragment() {


    private var mPage: Int = 0
    private lateinit var underDovelopmentProcessFragment: UnderDovelopmentProcessFragment
    var registrationModel = RegistrationModel("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.under_development_process_fragment, container, false)
    }


    companion object {
        val ARG_PAGE = "ARG_PAGE"


        fun newInstance(page: Int): UnderDovelopmentProcessFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = UnderDovelopmentProcessFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
