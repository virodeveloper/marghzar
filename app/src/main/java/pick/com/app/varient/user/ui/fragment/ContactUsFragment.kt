package pick.com.app.varient.user.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pick.com.app.R

class ContactUsFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_contact_us, container, false)
    }


    companion object {
        val ARG_PAGE = "ARG_PAGE"


        fun newInstance(page: Int): ContactUsFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = ContactUsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}