package pick.com.app.varient.owner.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aigestudio.wheelpicker.WheelPicker
import com.google.gson.Gson
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.toast
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.OwnerAvailabilityModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls


class ManageAvailabilityActivity : BaseActivity(), onResponse, WheelPicker.OnItemSelectedListener {

    var hrtext = ""
    var mintext = ""

    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {

        Log.e("", "")

        when (picker!!.getId()) {
            pick.com.app.R.id.main_wheel_left -> {
                hrtext="$data"
            }
            pick.com.app.R.id.main_wheel_center -> {

                mintext="$data"
            }

        }
    }

    lateinit var startTimeList: ArrayList<Int>
    lateinit var EndTimeList: ArrayList<Int>

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {

        if (methodtype == Urls.GET_USER_AVAILABALITY) {

            model = result as OwnerAvailabilityModel
            binding.user = model
            binding.invalidateAll()

        } else if (methodtype == Urls.OWNER_AVAILABALITY) {
          //  Toast.makeText(applicationContext,"Updated",Toast.LENGTH_SHORT).show()
            model = result as OwnerAvailabilityModel
            binding.user = this.model
            binding.invalidateAll()
            if (model.status==1)
               showMessageWithError(message=model.description!!,isfinish = false)


        }else if(methodtype == Urls.SAVE_MANAGE_AVAILABILITY){

            model = result as OwnerAvailabilityModel
            binding.user = this.model
            binding.invalidateAll()

        }
    }

    override fun onError(error: String?) {

    }

    private lateinit var binding: pick.com.app.databinding.OwnerManageAvailabilityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, pick.com.app.R.layout.owner_manage_availability)
        binding.activity = this
        startTimeList = ArrayList()
        EndTimeList = ArrayList()


        val toolbarCustom =
            ToolbarCustom(
                ToolbarCustom.lefticon,
                resources.getString(pick.com.app.R.string.manage_availability),
                ToolbarCustom.NoIcon,
                ToolbarCustom.NoIcon
            )
        left_Icon.onClick { onBackPressed() }

        binding.toolbar = toolbarCustom
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this@ManageAvailabilityActivity).data.user_id
        ApiServices<OwnerAvailabilityModel>().callApi(
            Urls.GET_USER_AVAILABALITY,
            this,
            hashmap,
            OwnerAvailabilityModel::class.java,
            true,
            this
        )
        //startTimeList()
        //endTimeList()
    }

    lateinit var model: OwnerAvailabilityModel
    fun ownerAvailable(model: OwnerAvailabilityModel) {
        this.model = model


//        if (this.model.waqas!!.toInt() == 0) {
//            this.model.data!![0].is_user_available = 1
//        } else {
//            this.model.data!![0].is_user_available = 0
//        }

        if (this.model.data!![0].is_user_available == 0) {
            this.model.data!![0].is_user_available = 1
        } else {
            this.model.data!![0].is_user_available = 0
        }
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this@ManageAvailabilityActivity).data.user_id
        hashmap["available_status"] = this.model.data!![0].is_user_available!!
        ApiServices<OwnerAvailabilityModel>().callApi(
            Urls.OWNER_AVAILABALITY,
            this,
            hashmap,
            OwnerAvailabilityModel::class.java,
            true,
            this
        )
    }

       /* if (this.model.data!![0].equals(null)){
            Toast.makeText(applicationContext,"Data is not null",Toast.LENGTH_SHORT).show()
            if (this.model.data!![0].is_user_available == 0) {
                this.model.data!![0].is_user_available = 1
            } else {
                this.model.data!![0].is_user_available = 0
            }
            val hashmap = HashMap<String, Any>()
            hashmap["user_id"] = SessionManager.getLoginModel(this@ManageAvailabilityActivity).data.user_id
            hashmap["available_status"] = this.model.data!![0].is_user_available!!
            ApiServices<OwnerAvailabilityModel>().callApi(
                Urls.OWNER_AVAILABALITY,
                this,
                hashmap,
                OwnerAvailabilityModel::class.java,
                true,
                this
            )
        }
        else
            Toast.makeText(applicationContext,"Data is null",Toast.LENGTH_SHORT).show()*/




    fun mondayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![0].is_available == 0) {
            this.model.data!![0].is_available = 1
        } else {
            this.model.data!![0].is_available = 0
        }
    }

    fun tuesdayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![1].is_available == 0) {
            this.model.data!![1].is_available = 1
        } else {
            this.model.data!![1].is_available = 0
        }
    }

    fun wednesdayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![2].is_available == 0) {
            this.model.data!![2].is_available = 1
        } else {
            this.model.data!![2].is_available = 0
        }
    }

    fun thursdayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![3].is_available == 0) {
            this.model.data!![3].is_available = 1
        } else {
            this.model.data!![3].is_available = 0
        }
    }

    fun fridayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![4].is_available == 0) {
            this.model.data!![4].is_available = 1
        } else {
            this.model.data!![4].is_available = 0
        }
    }

    fun saturdayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![5].is_available == 0) {
            this.model.data!![5].is_available = 1
        } else {
            this.model.data!![5].is_available = 0
        }
    }

    fun sundayAvailable(model: OwnerAvailabilityModel) {
        this.model = model
        if (this.model.data!![6].is_available == 0) {
            this.model.data!![6].is_available = 1
        } else {
            this.model.data!![6].is_available = 0
        }
    }

    fun startTimeList(): ArrayList<Int> {
        startTimeList.clear()
        val startTime: Int = 0
        val endTime: Int = 23
        for (i in startTime until endTime) {
            startTimeList.add(i + 1)
        }

        return startTimeList
    }

    fun endTimeList(endTime : String?): ArrayList<Int> {
        EndTimeList.clear()
        val startTime: Int = endTime!!.split(":")[0].toInt()
        val endTime: Int = 23
        for (i in startTime until endTime) {
            EndTimeList.add(i + 1)
        }

        return EndTimeList
    }


    fun whelldialog(arrayList: ArrayList<Int>, setTextView : String, model: OwnerAvailabilityModel) {
        this.model = model
        val dialog = Dialog(this, pick.com.app.R.style.mtrate)
        dialog.setContentView(pick.com.app.R.layout.whell_dialog)
        dialog.getWindow().setBackgroundDrawableResource(pick.com.app.R.color.transparent)


        val wheelLeft: WheelPicker = dialog.findViewById(pick.com.app.R.id.main_wheel_left)
        val teviewDone: TextView = dialog.findViewById(pick.com.app.R.id.teviewDone)
        val wheelCenter: WheelPicker = dialog.findViewById(pick.com.app.R.id.main_wheel_center)
        wheelLeft.setOnItemSelectedListener(this);
        wheelCenter.setOnItemSelectedListener(this);

        teviewDone.onClick {
            dialog.dismiss()



            if(setTextView == "mondayStartTime") {
                model.data!![0].start_date = hrtext+":"+mintext+":"+"00"
                model.data!![0].start_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "mondayEndTime") {
                model.data!![0].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "tuesdayStartTime") {
                model.data!![1].start_time = hrtext+":"+mintext+":"+"00"
                model.data!![1].start_date = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "tuesdayEndTime") {
                model.data!![1].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "wedStartTime") {
                model.data!![2].start_date = hrtext+":"+mintext+":"+"00"
                model.data!![2].start_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "wedEndTime") {
                model.data!![2].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "thurStartTime") {
                model.data!![3].start_date = hrtext+":"+mintext+":"+"00"
                model.data!![3].start_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "thurEndTime") {
                model.data!![3].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "fridaystartTime") {
                model.data!![4].start_time = hrtext+":"+mintext+":"+"00"
                model.data!![4].start_date = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "fridayEndTime") {
                model.data!![4].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "satStartTime") {
                model.data!![5].start_time = hrtext+":"+mintext+":"+"00"
                model.data!![5].start_date = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "satEndTime") {
                model.data!![5].end_time = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "sundayStartTime") {
                model.data!![6].start_time = hrtext+":"+mintext+":"+"00"
                model.data!![6].start_date = hrtext+":"+mintext+":"+"00"
            }else if(setTextView == "sundayEndTime") {
                model.data!![6].end_time = hrtext+":"+mintext+":"+"00"
            }

            binding.user = model
            binding.invalidateAll()
        }
        var minutes = ArrayList<String>()

        for (i in 0..59) {

            if (i < 10)
                minutes.add("0$i")
            else
                minutes.add("$i")

        }

        wheelLeft.data = arrayList
        wheelCenter.data = minutes


       /* hrtext =wheelLeft.data[0].toString()
        mintext =wheelCenter.data[0].toString()*/
            dialog.show()


    }

    fun mondayStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "mondayStartTime", model)
    }

    fun mondayEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![0].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![0].start_time), "mondayEndTime", model)
        }
    }

    fun tuesdayStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "tuesdayStartTime", model)
    }

    fun tuesdayEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![1].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![1].start_time), "tuesdayEndTime", model)
        }

    }

    fun wedStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "wedStartTime", model)
    }

    fun wedEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![2].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![2].start_time), "wedEndTime", model)
        }

    }

    fun thurStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "thurStartTime", model)
    }

    fun thurEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![3].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![3].start_time), "thurEndTime", model)
        }

    }

    fun fridaystartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "fridaystartTime", model)
    }

    fun fridayEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![4].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![4].start_time), "fridayEndTime", model)
        }

    }

    fun satStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "satStartTime", model)
    }

    fun satEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![5].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![5].start_time), "satEndTime", model)
        }

    }

    fun sundayStartTime(model: OwnerAvailabilityModel) {
        this.model = model
        whelldialog(startTimeList(), "sundayStartTime", model)
    }

    fun sundayEndTime(model: OwnerAvailabilityModel) {
        this.model = model
        if(model.data!![6].start_date == null){
            toast(resources.getString(R.string.empty_start_time_message))
        }else{
            whelldialog(endTimeList(model.data!![6].start_time), "sundayEndTime", model)
        }

    }

    fun saveAvailability(model: OwnerAvailabilityModel){
        this.model = model
        val hahsmap = HashMap<String, Any>()

        hahsmap["user_id"] = SessionManager.getLoginModel(BaseActivity.activity).data.user_id
        for (item in 0.. model.data!!.size-1) {

            var model = model.data!![item] as OwnerAvailabilityModel.Data
            hahsmap.put("availablity_data[${item}][dayofweek]", model.dayofweek.toString())
            hahsmap.put("availablity_data[${item}][start_time]", model.start_time.toString())
            hahsmap.put("availablity_data[${item}][end_time]", model.end_time.toString())
            hahsmap.put("availablity_data[${item}][is_available]", model.is_available.toString())

        }

        ApiServices<OwnerAvailabilityModel>().callApi(
            Urls.SAVE_MANAGE_AVAILABILITY,
            this,
            hahsmap,
            OwnerAvailabilityModel::class.java,
            true,
            this)
    }


}