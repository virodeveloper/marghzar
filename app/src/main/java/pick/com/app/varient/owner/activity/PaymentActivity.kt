package pick.com.app.varient.owner.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.payment_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.PaymentLayoutBinding
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.fragment.payment.PaymentFragment
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class PaymentActivity : BaseActivity(){


    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)

        when(methodtype) {

            Urls.DELETE_ACCOUNT -> {

                var result=result as RegistrationModel
                if (result.status==1){
                    showMessage(message=result.description)
SessionManager.setLoginModel(result,this)
                    activityBookingBinding.user = SessionManager.getLoginModel(this).data
                    activityBookingBinding.invalidateAll()
                }else{

                    onError(result.description)
                }

            }
        }
    }





    lateinit var  activityBookingBinding: PaymentLayoutBinding

    override fun onResume() {
        super.onResume()
        activityBookingBinding.user = SessionManager.getLoginModel(this).data

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBookingBinding = DataBindingUtil.setContentView<pick.com.app.databinding.PaymentLayoutBinding>(this, pick.com.app.R.layout.payment_layout)

        var toolbar = ToolbarCustom(R.drawable.back_arrow, resources.getString(R.string.payments), ToolbarCustom.NoIcon, R.drawable.noti_icon)
        activityBookingBinding.toolbar = toolbar

        left_Icon.onClick {
            onBackPressed()
        }


        var  mFragmentmanager =  supportFragmentManager;

        viewPager.adapter = SampleFragmentPagerAdapter(mFragmentmanager)

        tabs.setViewPager(viewPager)
    }



    inner class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        internal val PAGE_COUNT = 3
        private val tabTitles = arrayOf(resources.getString(R.string.daily) ,resources.getString(R.string.weekly),
            resources.getString(R.string.yearly))


        override fun getCount(): Int {
            return PAGE_COUNT
        }


        override fun getItem(position: Int): Fragment {



return PaymentFragment.newInstance(position + 1)

        }

        override fun getPageTitle(position: Int): CharSequence? {
            // Generate title based on item position
            return tabTitles[position]
        }
    }


    fun deleteBandDetails(view: View){

        var hash=HashMap<String,Any>()
        hash.put("user_id",activityBookingBinding.user!!.user_id)

        ApiServices<RegistrationModel>().callApi(Urls.DELETE_ACCOUNT,this,hash,RegistrationModel::class.java,true,this)
    }

    fun editBandDetails(view: View){

Redirection().goToAddBankAccount(activity = this)
    }
}
