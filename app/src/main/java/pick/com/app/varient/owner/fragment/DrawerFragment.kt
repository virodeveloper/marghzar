package pick.com.app.varient.owner.fragment

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adapter.UniverSalBindAdapter
import kotlinx.android.synthetic.main.left_side_menu.view.*
import pick.com.app.Constants


class DrawerFragment : Fragment() {
    private lateinit var viewFragment: View



    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private var selectedPos = -1
    lateinit var drawerlayout: DrawerLayout
    private lateinit var listLayoutBinding : pick.com.app.databinding.LeftSideMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflater != null) {
          listLayoutBinding = DataBindingUtil.inflate(inflater, pick.com.app.R.layout.left_side_menu, container, false)
            viewFragment = listLayoutBinding.root
        }
        initialize()

        val adpter=UniverSalBindAdapter(pick.com.app.R.layout.custom_row_drawer)
        viewFragment.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.activity!!,
                LinearLayout.VERTICAL
            )
        )

        viewFragment.recyclerView.adapter=adpter

        if(Constants.apptype == Constants.AppType.USER){
           // adpter.addAll(DrawerModel(activity!!).getUserArrayList(activity!!,this) as ArrayList<Any>)
        }else{
           // adpter.addAll(DrawerModel(activity!!).getOwerArrayList(activity!!,this) as ArrayList<Any>)
        }



        return viewFragment
    }

    private fun initialize() {
       setListener()
    }

    private fun setListener() {

    }

    fun closeDrawer() {
        drawerlayout.closeDrawer(Gravity.LEFT)
    }

    @SuppressLint("NewApi")
    fun setUp(drawerlayout: DrawerLayout) {

        this.drawerlayout = drawerlayout
        mDrawerToggle = object : ActionBarDrawerToggle(activity, drawerlayout, pick.com.app.R.string.open_drawer, pick.com.app.R.string.close_drawer) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                if (Constants.apptype == Constants.AppType.USER) {
                    /*viewFragment.side_menu_student_name.text = (activity as BaseActivity).getFromPrefsString(HealthCareConstant.AGENT_NAME)
                    if (activity is AgentHomeActivity) {
                        setStyle(0)
                    }*/

                } else {
                    /*viewFragment.side_menu_student_name.text = (activity as BaseActivity).getFromPrefsString(HealthCareConstant.CLINICIAN_AGENT_NAME)
                    if (activity is ClinicianHomeActivity) {
                        setStyle(0)
                    }*/
                }

                /*if (activity is ProfileActivity) {
                    setStyle(1)
                } else if (activity is ChatIntitaiteActivity) {
                    setStyle(2)
                }*/


            }

            /*@SuppressLint("NewApi")
            override fun onDrawerClosed(drawerView: View) {
                val am = activity!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val cn = am.getRunningTasks(1)[0].topActivity
                super.onDrawerClosed(drawerView)
                if (selectedPos != -1) {
                    when (selectedPos) {
                        0 -> if (getClassShortName() != ".activity.HomeActivity") {
                            navigateToHome()
                        } else {
                            drawerlayout.closeDrawers()
                        }
                        1 -> if (getClassShortName() != ".activity.ProfileActivity") {
                            navigateToProfile()
                        } else {
                            drawerlayout.closeDrawers()
                        }
                        2 -> if (getClassShortName() != ".activity.ChatIntitaiteActivity") {


                        } else {
                            drawerlayout.closeDrawers()
                        }

                        3 -> navigateTpLogout()

                        else -> {
                        }
                    }
                    selectedPos = -1
                }
            }*/
        }
        drawerlayout.addDrawerListener(mDrawerToggle)
    }

    private fun navigateToProfile() {
       // Redirection().goToProfile(false, activity!!,null)
    }



}