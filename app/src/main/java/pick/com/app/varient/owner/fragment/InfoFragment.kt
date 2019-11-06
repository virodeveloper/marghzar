package pick.com.app.varient.owner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show
import pick.com.app.databinding.OwnerProfileInfoFragmentBinding
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel

interface Updateable {
    fun update()
}

class InfoFragment : Fragment() ,Updateable  {
    override fun update() {
     onResume()
    }

    private var mPage: Int = 0

    internal var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }

    lateinit var model: RegistrationModel

    lateinit var binding: OwnerProfileInfoFragmentBinding
    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            pick.com.app.R.layout.owner_profile_info_fragment,
            container,
            false
        )
        model = SessionManager.getLoginModel(activity)
        binding.user = SessionManager.getLoginModel(activity)
        binding.user!!.data.isdifferentcity=(binding.user!!.data.is_different_city == 1)

        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        model = SessionManager.getLoginModel(activity)
        var adapter = UniverSalBindAdapter(pick.com.app.R.layout.custom_info_drop_locations)
        binding.recyclerView.isNestedScrollingEnabled=false
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.recyclerView.adapter = adapter
        if (model.data.locations!=null)
            adapter.addAll(model.data.locations as ArrayList<Any>)


        if (model.data.deliver_user_door==0){
            binding.layoutperkm.hide()
        }else{
            binding.layoutperkm.show()
        }


        binding.invalidateAll()
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): InfoFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = InfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}