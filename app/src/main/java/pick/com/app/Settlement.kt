package pick.com.app

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import okhttp3.ResponseBody
import pick.com.app.base.BaseActivity
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class Settlement : AppCompatActivity(), onResponse {
   lateinit var bank:String
  lateinit  var categories:Array<String?>
    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        if (methodtype == Urls.BANK) {

            Toast.makeText(applicationContext,"you made it",Toast.LENGTH_SHORT).show()
            var model = result as Model
            var i = 0
            categories=model.bank_name
            val dataAdapter = ArrayAdapter(this@Settlement, android.R.layout.simple_spinner_item,categories )
            spinner.setAdapter(dataAdapter)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            while (i <= model.bank_name.size) {
//                categories=String.
//                i = i + 1
//            }

            var t=2


        }
        if (methodtype == Urls.SETTLEMENT) {
            Toast.makeText(applicationContext,"Request Sended",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onError(error: String?) {

        Toast.makeText(applicationContext,"you dont",Toast.LENGTH_SHORT).show()
    }

    lateinit var  editText: EditText
    lateinit var  editText2: EditText
    lateinit var  editText3: EditText
    lateinit var  editText4: EditText
    lateinit var  spinner: Spinner
    lateinit var  btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement)
        editText=findViewById(R.id.amount)
        editText2=findViewById(R.id.baccount)
        editText3=findViewById(R.id.iban)
        editText4=findViewById(R.id.accountname)
        spinner=findViewById(R.id.bankname)

        val hashmap = HashMap<String, Any>()
        hashmap["social_id"] ="1"
        ApiServices<Model>().callApi(
            Urls.BANK,
            this,
            hashmap,
            Model::class.java,
            true,
            this
        )

       // var datamodel:ArrayAdapter<String>

        spinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0:AdapterView<*>, arg1: View, arg2:Int,
                                        arg3:Long) {
              bank = spinner.getItemAtPosition(arg2).toString()

            }
            override fun onNothingSelected(arg0:AdapterView<*>) {
                //optionally do something here
            }
        })
    }

    fun dosettlement(view: View) {
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(BaseActivity.activity).data.user_id
        hashmap["amount"] =editText.text.toString()
        hashmap["iban"] =editText3.text.toString()
        hashmap["bank_account_number"] =editText2.text.toString()
        hashmap["bank_name"] =bank
        hashmap["account_name"] =editText4.text.toString()
//        val sharedPreferences: SharedPreferences = this.getSharedPreferences("Mupre", Context.MODE_PRIVATE)
//
//        var wall=sharedPreferences.getString("wallet","100")
//        if (wall.toInt()>editText.text.toString().toInt()){
//
//        }
//        else
//            Toast.makeText(applicationContext,"you cannot send request",Toast.LENGTH_SHORT).show()

        ApiServices<Model>().callApi(
            Urls.SETTLEMENT,
            this,
            hashmap,
            Model::class.java,
            true,
            this
        )


    }
}
