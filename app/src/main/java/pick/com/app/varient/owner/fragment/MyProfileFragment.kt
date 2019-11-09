package pick.com.app.varient.owner.fragment


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.profile_top_layout.*
import kotlinx.android.synthetic.main.profile_top_layout.view.*
import kotlinx.android.synthetic.main.profile_top_layout.view.addToWallet
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.Constants
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.base.StaticWebUrlActivity
import pick.com.app.databinding.ActivityProfileBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.WalletModel
import pick.com.app.varient.user.ui.activity.AddWalletActivity
import pick.com.app.varient.user.ui.activity.WalletActivity
import pick.com.app.varient.user.ui.fragment.FavoriteVehicleFragment
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls




class MyProfileFragment : Fragment(), onResponse {
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        if (methodtype == Urls.MAKE_PAYMENT_WALLET) {
            var model = result as WalletModel
            if(model.status == 1){
                startActivity(Intent(activity, StaticWebUrlActivity::class.java)
                        .putExtra("url", model.payment_url+","+"Payment"))
            }
        }
        if (methodtype == Urls.USER_WALLET) {
            val result = result as RegistrationModel
        }
        else {
            val result = result as RegistrationModel

            (result.is_active == 1 && result.message.toLowerCase() == "success")
            SessionManager.setLoginModel(result, activity!!)
            binding.user=result
            binding.invalidateAll()
        }




       // (activity as CommonActivity). showMessage(result.message, result.description)

    }

    override fun onError(error: String?) {
        (activity as CommonActivity) .showMessage("Unsuccess", if (error.toString()=="connectionError") context!!.resources.getString(
            R.string.internet_is_not_working_properly) else error.toString())

    }


    lateinit var  binding:ActivityProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       /* view = DataBindingUtil.inflate(
            inflater, R.layout.activity_profile, container, false
        )
*/

         binding=   DataBindingUtil.inflate<ActivityProfileBinding>(inflater,pick.com.app.R.layout.activity_profile,container,false)


        /*  val binding = DataBindingUtil.inflate(
            inflater, pick.com.app.R.layout.activity_profile, container, false
        )*/

        //here data must be an instance of the class MarsDataProvider



       // view=inflater.inflate(pick.com.app.R.layout.activity_profile, container, false)



        return  binding.getRoot()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (view != null) {
            val parentViewGroup = binding.getRoot().parent as ViewGroup?
            parentViewGroup?.removeAllViews();
        }
    }


    override fun onResume() {
        super.onResume()

        var  mFragmentmanager =  getChildFragmentManager();

        view!!.viewPager.adapter = SampleFragmentPagerAdapter(mFragmentmanager)

        view!!.tabs.setViewPager(viewPager)
        binding.user=SessionManager.getLoginModel(activity)
        binding.invalidateAll()



        if (binding!!.user!!.data.user_type=="oi"){


            view!!.textViewCompany.setText(getString(pick.com.app.R.string.individual))
        }else{
            view!!.textViewCompany.setText(getString(pick.com.app.R.string.company))
        }
        ApiServices<RegistrationModel>().callApi(
            Urls.GET_PROFILE,
            this,binding.user!!.getProfile(binding.user!!)
            , RegistrationModel::class.java,true,activity!!)

    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    //   var fragment= activity!!.getSupportFragmentManager().beginTransaction().replace(android.R.id.tabcontent, CDMBasicMovieFragment.newInstance()).commit();


        activity!!.righticon.onClick {
            Redirection().goToEdit(false, activity!!, null)
        }

        activity!!.addToWallet.onClick {
           showAddWalletDialog()
        }
    }

    fun showAddWalletDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.add_to_wallet))
        val view = layoutInflater.inflate(R.layout.dialog_add_wallet, null)
        val categoryEditText = view.findViewById(R.id.amount) as EditText
        categoryEditText.inputType = InputType.TYPE_CLASS_NUMBER

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val value = categoryEditText.text
            startActivity(Intent(activity, AddWalletActivity::class.java).putExtra("booking_id", "0").
                putExtra("amount", ""+value))
            dialog.dismiss()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }

    inner class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        internal val PAGE_COUNT = 2
        private val tabTitles = arrayOf(if(Constants.apptype == Constants.AppType.USER)context!!.getString(pick.com.app.R.string.favorite_vechicle) else context!!.getString(
            pick.com.app.R.string.info), context!!.getString(pick.com.app.R.string.change_password))


        override fun getCount(): Int {
            return PAGE_COUNT
        }


        override fun getItem(position: Int): Fragment {

            when {
                Constants.apptype == Constants.AppType.USER -> if (position == 0)
                    return FavoriteVehicleFragment.newInstance(position + 1)
                else
                    return ChangePasswordFragment.newInstance(position + 1)
                else -> if (position == 0)
                    return InfoFragment.newInstance(position + 1)
                else
                    return ChangePasswordFragment.newInstance(position + 1)
            }



        }

        override fun getPageTitle(position: Int): CharSequence? {
            // Generate title based on item position
            return tabTitles[position]
        }
    }
}
