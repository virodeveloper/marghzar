package pick.com.app.varient.owner.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import okhttp3.ResponseBody
import pick.com.app.R
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls


/**
 * A simple [Fragment] subclass.
 */
class SettlementFragmnet : Fragment() {

    lateinit var  editText:EditText
    lateinit var  editText2:EditText
    lateinit var  editText3: EditText
    lateinit var  Spinner:EditText
    lateinit var  btn:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View= inflater.inflate(R.layout.fragment_settlement_fragmnet, container, false)
        val hashmap = HashMap<String, Any>()
//        hashmap["social_id"] ="1"
//        ApiServices<ResponseBody>().callApi(
//            Urls.BANK,
//            this,
//            hashmap,
//            SettlementFragmnet::class.java,
//            true,
//            this
//        )


        editText=view.findViewById(R.id.amount)
        editText2=view.findViewById(R.id.baccount)
        editText3=view.findViewById(R.id.iban)
        return  view
    }


}
