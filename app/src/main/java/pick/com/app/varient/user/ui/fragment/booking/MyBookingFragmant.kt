package pick.com.app.varient.user.ui.fragment.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.databinding.MyBookingLayoutBinding
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel

class MyBookingFragmant : Fragment(), onResponse {
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        val result = result as RegistrationModel

        (result.is_active == 1 && result.message.toLowerCase() == "success")
        SessionManager.setLoginModel(result, activity!!)


        // (activity as CommonActivity). showMessage(result.message, result.description)

    }

    override fun onError(error: String?) {
        (activity as CommonActivity).showMessage(
            "Unsuccess",
            if (error.toString() == "connectionError") resources.getString(R.string.internet_is_not_working_properly) else error.toString()
        )

    }


    lateinit var binding: MyBookingLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /* view = DataBindingUtil.inflate(
             inflater, R.layout.activity_profile, container, false
         )
 */

        binding = DataBindingUtil.inflate(inflater, pick.com.app.R.layout.my_booking_layout, container, false)


        /*  val binding = DataBindingUtil.inflate(
            inflater, pick.com.app.R.layout.activity_profile, container, false
        )*/

        //here data must be an instance of the class MarsDataProvider


        // view=inflater.inflate(pick.com.app.R.layout.activity_profile, container, false)

        return binding.getRoot()
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




    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var mFragmentmanager = getChildFragmentManager();

        view!!.viewPager.adapter = SampleFragmentPagerAdapter(mFragmentmanager)

        view!!.tabs.setViewPager(viewPager)
        //binding.user= SessionManager.getLoginModel(activity)
        binding.invalidateAll()


        // Attach the page change listener inside the activity
        view!!.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                view!!.viewPager.adapter!!.notifyDataSetChanged()
            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {
                // Code goes here
            }
        })



    }





    inner class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        internal val PAGE_COUNT = 2
        private val tabTitles = arrayOf(resources.getString(R.string.ongoing), resources.getString(R.string.past))


        override fun getCount(): Int {
            return PAGE_COUNT
        }

     /*   override fun getItemPosition(`object`: Any): Int {

            if (`object` is NewBookingFragment) {
                val f = `object` as NewBookingFragment
                f!!.onResume()
            }
            return super.getItemPosition(`object`)
        }*/
     public override fun getItemPosition(`object`: Any): Int {


         return  PagerAdapter.POSITION_NONE
     }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)

        }

        override fun getItem(position: Int): Fragment {

            when (position) {

                0 ->
                    return NewBookingFragment.newInstance(position + 1)
                1 ->
                    return NewBookingFragment.newInstance(position + 1)
                2 ->
                    return NewBookingFragment.newInstance(position + 1)

            }

return Fragment()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            // Generate title based on item position
            return tabTitles[position]
        }

    }



    fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)

     }

}

