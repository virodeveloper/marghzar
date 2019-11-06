package pick.com.app.base.model

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.R
import pick.com.app.Settlement
import pick.com.app.base.StaticWebUrlActivity
import pick.com.app.base.WebviewFragment
import pick.com.app.varient.owner.activity.ActivityBooking
import pick.com.app.varient.owner.activity.ManageAvailabilityActivity
import pick.com.app.varient.owner.activity.ManageVehicleActivity
import pick.com.app.varient.owner.activity.PaymentActivity
import pick.com.app.varient.owner.fragment.MyProfileFragment
import pick.com.app.varient.owner.fragment.SettlementFragmnet
import pick.com.app.varient.owner.fragment.UnderDovelopmentProcessFragment
import pick.com.app.varient.user.ui.activity.ContactUsActivity
import pick.com.app.varient.user.ui.fragment.booking.HomeFragment
import pick.com.app.varient.user.ui.fragment.booking.MyBookingFragmant


class DrawerModel {

    var pressed = false
    var activity = Activity()
    var position = 0
    var rightIcon = ToolbarCustom.NoIcon

    var title: String = ""
    var type_url : String = ""

    lateinit var class_: Any

    constructor(title: String) {
        this.title = title
    }

  lateinit  var drawerFragment:ActivityBooking
    constructor() {

    }

    constructor(title: String, drawerFragment: ActivityBooking, clazz: Any, activity: Activity) {
        this.title = title
        this.drawerFragment = drawerFragment
        this.class_ = clazz
        this.activity = activity
    }

    var string=""

    constructor(title: String, drawerFragment: ActivityBooking, clazz: Any, activity: Activity,string:String) {
        this.title = title
        this.string = string
        this.drawerFragment = drawerFragment
        this.class_ = clazz
        this.activity = activity
    }


    constructor(title: String, drawerFragment: ActivityBooking, clazz: Fragment, activity: Activity,rightIcon:Int) {
        this.title = title
        this.drawerFragment = drawerFragment
        this.class_ = clazz
        this.activity = activity
        this.rightIcon = rightIcon
    }

    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(
        pressed: Boolean,
        activity: Activity,

        title: String,
        class_: Fragment,
        drawerFragment: ActivityBooking
    ) {
        this.pressed = pressed
        this.activity = activity
        this.position = position
        this.title = title
        this.class_ = class_
        this.drawerFragment = drawerFragment
    }


    fun getOwerArrayList(context: Context,drawerFragment:ActivityBooking,currentLocation: Location): ArrayList<DrawerModel> {


        val ower_drawer_list = ArrayList<DrawerModel>()

        var selcteditem = DrawerModel(context.getString(pick.com.app.R.string.my_booking),drawerFragment,
            pick.com.app.varient.owner.fragment.booking.MyBookingFragmant(),activity,R.drawable.select_language_icon)
        selcteditem.pressed = true
        ower_drawer_list.add(selcteditem)
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.my_profile),drawerFragment,
            MyProfileFragment(),activity,R.drawable.edit_icon))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.manage_vehicle),drawerFragment,
            ManageVehicleActivity(),activity))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.manage_availability),drawerFragment,ManageAvailabilityActivity(),activity))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.payment),drawerFragment,
            PaymentActivity(),activity))
        ower_drawer_list.add(DrawerModel("Settlement",drawerFragment,Settlement(),activity))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.about_us),drawerFragment,StaticWebUrlActivity(),activity,"about_us_owner,${activity.getString(R.string.about_us)}"))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.contact_us),drawerFragment,ContactUsActivity(),activity))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.plolicies),drawerFragment,StaticWebUrlActivity(),activity,"policies_owner,${activity.getString(R.string.policies)}"))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.privacy_policy),drawerFragment,StaticWebUrlActivity(),activity,"privacy_policy_owner,${activity.getString(R.string.privacy_policy)}"))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.terms_and_condition),drawerFragment,StaticWebUrlActivity(),activity,"terms_and_conditions_owner,${activity.getString(R.string.terms_and_condition)}"))
        ower_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.logout),drawerFragment,UnderDovelopmentProcessFragment(),activity))

        return ower_drawer_list


    }

    fun getUserArrayList(context:Activity, drawerFragment: ActivityBooking,currentLocation: Location): ArrayList<DrawerModel> {


        val user_drawer_list = ArrayList<DrawerModel>()
        val selcteditem = DrawerModel(context.getString(pick.com.app.R.string.home),drawerFragment,
            HomeFragment.newInstance(currentLocation,""),activity)
        selcteditem.pressed=true
        user_drawer_list.add(selcteditem)
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.my_booking),drawerFragment,
            MyBookingFragmant(),activity))
       user_drawer_list.add(DrawerModel("Settlement",drawerFragment,Settlement(),activity))
        //user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.wallet),drawerFragment,UnderDovelopmentProcessFragment(),activity))

        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.my_profile),drawerFragment,MyProfileFragment(),activity,R.drawable.edit_icon))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.about_us),drawerFragment,WebviewFragment.getInstance("http://pick.com.sa/api/about_us_user?lang=en"),activity))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.contact_us),drawerFragment,ContactUsActivity(),activity))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.plolicies),drawerFragment,WebviewFragment.getInstance("http://pick.com.sa/api/policies_user?lang=en"),activity))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.privacy_policy),drawerFragment,WebviewFragment.getInstance("http://pick.com.sa/api/privacy_policy_user?lang=en"),activity))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.terms_and_condition),drawerFragment,WebviewFragment.getInstance("http://pick.com.sa/api/terms_and_conditions_user?lang=en"),activity))
        user_drawer_list.add(DrawerModel(context.getString(pick.com.app.R.string.logout),drawerFragment,UnderDovelopmentProcessFragment(),activity))

        return user_drawer_list


    }


    companion object {

        var oldposition = 0;

        @JvmStatic
        @BindingAdapter("showSelectImage")
        fun showSelectImage(imageview: ImageView, drawerModel: DrawerModel) {
            if (drawerModel.pressed) {
                imageview.setImageResource(pick.com.app.R.drawable.slide_nav_icon_h)

            } else {
                imageview.setImageResource(pick.com.app.R.drawable.slide_nav_icon)

            }

            imageview.onClick {
                drawerModel.drawerFragment.closeDrawer()
                if(drawerModel.title == resources.getString(R.string.logout)){
                    drawerModel.drawerFragment.twoTabButtonDialog(1)
                }else {
                    drawerModel.drawerFragment.gotoFragmetn(drawerModel.class_,drawerModel.string)

                    if (drawerModel.class_ is Fragment)
                    if (oldposition != drawerModel.position) {

                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.title=drawerModel.title
                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.righticon=drawerModel.rightIcon
                        drawerModel.drawerFragment.activityBookingBinding!!.notifyChange()
                        drawerModel.drawerFragment.onResume()
                        drawerModel.pressed = !drawerModel.pressed

                        drawerModel.class_
                        val bundle = null


                        (drawerModel.drawerFragment.adpter.arrraylist[oldposition] as DrawerModel).pressed = false
                        drawerModel.drawerFragment.adpter.notifyDataSetChanged()
                        oldposition = drawerModel.position
                    }
                }

            }
        }


        @JvmStatic
        @BindingAdapter("showlayout")
        fun showlayout(linearLayout: LinearLayout, drawerModel: DrawerModel) {


            if (drawerModel.pressed) {

                linearLayout.setBackgroundColor(Color.parseColor("#1DA1F2"))

            } else {

                linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))

            }
            linearLayout.onClick {
                drawerModel.drawerFragment.closeDrawer()
                if(drawerModel.title == resources.getString(R.string.logout)){
                    drawerModel.drawerFragment.twoTabButtonDialog(1)
                }else{

                    drawerModel.drawerFragment.gotoFragmetn(drawerModel.class_,drawerModel.string)
                    if (drawerModel.class_ is Fragment){
                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.title=drawerModel.title
                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.righticon=drawerModel.rightIcon

                        drawerModel.drawerFragment.activityBookingBinding!!.notifyChange()
                        drawerModel.drawerFragment.onResume()

                        if (oldposition != drawerModel.position) {
                            val bundle = null
                            // drawerModel.drawerFragment.gotoFragmetn(drawerModel.class_)

                            drawerModel.pressed = !drawerModel.pressed
                            (  drawerModel.drawerFragment.adpter.arrraylist[oldposition] as DrawerModel).pressed = false
                            drawerModel.drawerFragment.adpter.notifyDataSetChanged()
                            oldposition = drawerModel.position
                    }




            }}}
        }





        @JvmStatic
        @BindingAdapter("showTextview")
        fun showTextview(linearLayout: TextView, drawerModel: DrawerModel) {

            if (drawerModel.pressed) {

                linearLayout.setBackgroundColor(Color.parseColor("#1DA1F2"))
                linearLayout.setTextColor(Color.WHITE)
            } else {
                linearLayout.setTextColor(Color.BLACK)
                linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))

            }
            linearLayout.onClick {
                drawerModel.drawerFragment.closeDrawer()
                if(drawerModel.title == resources.getString(R.string.logout)){
                    drawerModel.drawerFragment.twoTabButtonDialog(1)
                }else{
                    drawerModel.drawerFragment.gotoFragmetn(drawerModel.class_,drawerModel.string)
                    if (drawerModel.class_ is Fragment)
                    if (oldposition != drawerModel.position) {
                        val bundle = null
                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.title=drawerModel.title
                        drawerModel.drawerFragment.activityBookingBinding.toolbar!!.righticon=drawerModel.rightIcon

                        drawerModel.drawerFragment.activityBookingBinding!!.notifyChange()
                        drawerModel.drawerFragment.onResume()
                        // drawerModel.drawerFragment.gotoFragmetn(drawerModel.class_)
                        drawerModel.pressed = !drawerModel.pressed
                        (drawerModel.drawerFragment.adpter.arrraylist[oldposition] as DrawerModel).pressed = false
                        drawerModel.drawerFragment.adpter.notifyDataSetChanged()
                        oldposition = drawerModel.position
                    }
            }

            }
        }
    }

}
