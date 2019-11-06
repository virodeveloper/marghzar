package pick.com.app.varient.user.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom

class HomeActivity : BaseActivity(){

    private lateinit var activityBookingBinding: pick.com.app.databinding.ActivityBookingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBookingBinding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

        var toolbar = ToolbarCustom(R.drawable.nav_icon, resources.getString(R.string.booking), R.drawable.noti_icon,ToolbarCustom.NoIcon)

        activityBookingBinding.toolbar = toolbar

        activityBookingBinding.toolbar!!.title = resources.getString(R.string.my_profile)



    }
}