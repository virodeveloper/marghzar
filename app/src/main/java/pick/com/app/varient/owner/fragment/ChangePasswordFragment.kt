package pick.com.app.varient.owner.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.livinglifetechway.k4kotlin.core.makeToast
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.databinding.ChangePasswordFragmentBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class ChangePasswordFragment : Fragment(), onResponse {
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        if (result is RegistrationModel) {
            Log.e("", "");

            val result = result as RegistrationModel

            if (result.message.toLowerCase()=="success") {
                makeToast(activity, result.description, 1000)
                onResume()
            }

            (activity as CommonActivity).showMessage(result.message, result.description)
        }
    }

    override fun onError(error: String?) {

        (activity as CommonActivity) .showMessage("Unsuccess", if (error.toString()=="connectionError") context!!.resources.getString(R.string.internet_is_not_working_properly) else error.toString())

    }

    private var mPage: Int = 0
    private  var changePasswordFragmentBinding: ChangePasswordFragmentBinding?=null
    var registrationModel = RegistrationModel("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        changePasswordFragmentBinding=   DataBindingUtil.inflate(inflater,R.layout.change_password_fragment,container,false)

        changePasswordFragmentBinding!!.activity=this


        return changePasswordFragmentBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        if (changePasswordFragmentBinding!=null)
        changePasswordFragmentBinding!!.user = SessionManager.getLoginModel(activity)
        changePasswordFragmentBinding!!.user!!.data.password=""
        changePasswordFragmentBinding!!.invalidateAll()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (view != null) {
            val parentViewGroup = changePasswordFragmentBinding!!.getRoot().parent as ViewGroup?
            parentViewGroup?.removeAllViews();
        }
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"


        fun newInstance(page: Int): ChangePasswordFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = ChangePasswordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changePasswordFragmentBinding!!.user = SessionManager.getLoginModel(activity)
        changePasswordFragmentBinding!!.user!!.data.password=""
        changePasswordFragmentBinding!!.invalidateAll()

    }

   fun submitOnClick(view: View,model : RegistrationModel) {

       if (changePasswordFragmentBinding!!.user!!.checkSetPassWordvalidation(view , changePasswordFragmentBinding!!.user!!, "changepassword")) {

           ApiServices<RegistrationModel>().callApi(
               Urls.CHANGE_PASSWORD,
               this,
               registrationModel.changePassword(changePasswordFragmentBinding!!.user!!),
               RegistrationModel::class.java,
               true, activity!!
           )
       }
    }
}