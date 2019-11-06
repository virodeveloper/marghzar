package pick.com.app.base.model

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.view.View
import androidx.databinding.BindingAdapter
import com.livinglifetechway.k4kotlin.core.onClick



class CountryCodeModel {


    var status: String? = null
    var message: String? = null
    var data = ArrayList<Data>()

    class Data {
        override fun toString(): String {
            return "+"+phonecode+" -"+name
        }

        var id: String? = null
        var sortname: String? = null
        var name: String? = null
        var phonecode = ""

        var countrycode = "+$phonecode -"

        companion object {

            var activity:Activity?=null

@BindingAdapter("itemSelect")
@JvmStatic
fun itemSelect(view: View,data: Data){
    view.onClick {
        val intent = activity!!.intent
        intent.putExtra("countrycode", data.phonecode)
        activity!!.setResult(RESULT_OK, intent)
        activity!!.finish()
    }


}




        }
    }
}
