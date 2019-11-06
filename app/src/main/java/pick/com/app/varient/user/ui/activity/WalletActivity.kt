package pick.com.app.varient.user.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hbb20.CountryCodePicker
import com.livinglifetechway.k4kotlin.core.onClick
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import kotlinx.android.synthetic.main.dialog_add_wallet.*
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.PaymentActivity
import pick.com.app.base.StaticWebUrlActivity
import pick.com.app.base.model.StaticWebPagesModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.UserPaymentScreenBinding
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.location.AppLocationActivity
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.FilterModel.Companion.hashmap
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.WalletModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class WalletActivity : BaseActivity() {

    private lateinit var binding : UserPaymentScreenBinding
    private var bookingId : String= ""
    private var payable_amount : String= ""
    private lateinit var toolbarCustom: ToolbarCustom
    lateinit var btn:Button
    lateinit var checkBox: CheckBox
    lateinit var textView: TextView
    lateinit var modll:WalletModel
    lateinit var wall:String
    var new:Int=0
    var newWallet=0
    lateinit var co:CountryCodePicker

    var am:String="Wallet Amount"
    var u=0

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        if (methodtype == Urls.MAKE_PAYMENT) {
            var up:String="https://pick.com.sa/index.php"
            var model = result as WalletModel
            if(model.status == 1){

                startActivity(Intent(this, StaticWebUrlActivity::class.java)
                    .putExtra("url", up+","+"Payment"))
                finish()
                //model.payment_url
            }
        } else if(methodtype == Urls.USER_WALLET) {
            var model = result as WalletModel
            am=model.wallet_amount
        }
        else if(methodtype == Urls.BOOKING_PAYMENT) {

            var model = result as WalletModel
            if(model.status == 1){
                finish()
                Toast.makeText(applicationContext,"Payment Done",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.user_payment_screen)
        toolbarCustom = ToolbarCustom(ToolbarCustom.lefticon, resources.getString(R.string.payment), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        binding.toolbar = toolbarCustom

        textView=findViewById(R.id.wallett)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("Mupre", Context.MODE_PRIVATE)
        co=findViewById(R.id.cou)

        wall=sharedPreferences.getString("wallet","100")


        textView.text=wall.toString()

//


        checkBox=findViewById(R.id.cb_wallet)

        checkBox.setOnClickListener(View.OnClickListener {
            if (checkBox.isChecked){
                if(wall.toInt()>0 && payable_amount.toInt()>wall.toInt())
                {u=2}
                else{u=1}



            }
            else
                u=0


        })



        binding.model = WalletModel()
        binding.redirection = this@WalletActivity
        left_Icon.onClick { onBackPressed() }
        bookingId = intent.getStringExtra("booking_id")
        payable_amount = intent.getStringExtra("amount")

        binding.payableAmount.setText(payable_amount + " SAR")

//        if(u==1){
//            if(payable_amount.toInt()>wall.toInt()){
//                new=payable_amount.toInt()-wall.toInt()
//                newWallet=payable_amount.toInt()-new
//                payable_amount=new.toString()
//            }
//        }


    }



    fun userWallet(){
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
        ApiServices<WalletModel>().callApi(Urls.USER_WALLET, this, hashmap, WalletModel::class.java,
            true, this)
    }

    fun goToCountryCode(){

        Redirection().goToCountryCode(false, this, COUNTRYCODE_REWUEST)

    }
    fun payNow(view: View) {
        if(validate()){


            if(u==0){
                val intent = Intent(this, PayTabActivity::class.java)
                intent.putExtra(PaymentParams.MERCHANT_EMAIL, "turkiothman@hotmail.com")
                intent.putExtra(
                    PaymentParams.SECRET_KEY,
                    "8pJ1qNyOZvvxFkaYXpwZJC6gDBmOEeGNEpO5dnTcSoJyIUxFj3F3FLkjnG81VOPzeEFr1yEUXnsFiZlB31FJNZ3aFbcB01afFQHG"
                )//Add your Secret Key Here

                intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
                intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Vehicle Booking")
                intent.putExtra(PaymentParams.AMOUNT, payable_amount.toDouble())

                intent.putExtra(PaymentParams.CURRENCY_CODE, "SAR")
                intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, co.selectedCountryCode+binding.model!!.contact_number)
                intent.putExtra(PaymentParams.CUSTOMER_EMAIL, binding.model!!.email)
                intent.putExtra(PaymentParams.ORDER_ID, bookingId)
                intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

                //Billing Address
                intent.putExtra(PaymentParams.ADDRESS_BILLING, binding.model!!.billing_address)
                intent.putExtra(PaymentParams.CITY_BILLING, binding.model!!.city)
                intent.putExtra(PaymentParams.STATE_BILLING, binding.model!!.state)

                intent.putExtra(PaymentParams.COUNTRY_BILLING, "SAU")
                intent.putExtra(
                    PaymentParams.POSTAL_CODE_BILLING,
                    "00973"
                ) //Put Country Phone code if Postal code not available '00973'

                //Shipping Address
                intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
                intent.putExtra(PaymentParams.CITY_SHIPPING, binding.model!!.city)
                intent.putExtra(PaymentParams.STATE_SHIPPING, binding.model!!.state)
                intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "SAR")
                intent.putExtra(
                    PaymentParams.POSTAL_CODE_SHIPPING,
                    "00973"
                ) //Put Country Phone code if Postal code not available '00973'

                //Payment Page Style
                intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

                //Tokenization
                intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
                startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)


            }
            else
            {

                if(u==2){
                    if(payable_amount=="0"){
                        payable_amount = intent.getStringExtra("amount")
                    }
                    new=payable_amount.toInt()-wall.toInt()
                    newWallet=payable_amount.toInt()-new
                    payable_amount=new.toString()
                    val intent = Intent(this, PayTabActivity::class.java)
                    intent.putExtra(PaymentParams.MERCHANT_EMAIL, "turkiothman@hotmail.com")
                    intent.putExtra(
                        PaymentParams.SECRET_KEY,
                        "8pJ1qNyOZvvxFkaYXpwZJC6gDBmOEeGNEpO5dnTcSoJyIUxFj3F3FLkjnG81VOPzeEFr1yEUXnsFiZlB31FJNZ3aFbcB01afFQHG"
                    )//Add your Secret Key Here

                    intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
                    intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Vehicle Booking")
                    intent.putExtra(PaymentParams.AMOUNT, payable_amount.toDouble())

                    intent.putExtra(PaymentParams.CURRENCY_CODE, "SAR")
                    intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, co.selectedCountryCode+binding.model!!.contact_number)
                    intent.putExtra(PaymentParams.CUSTOMER_EMAIL, binding.model!!.email)
                    intent.putExtra(PaymentParams.ORDER_ID, bookingId)
                    intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

                    //Billing Address
                    intent.putExtra(PaymentParams.ADDRESS_BILLING, binding.model!!.billing_address)
                    intent.putExtra(PaymentParams.CITY_BILLING, binding.model!!.city)
                    intent.putExtra(PaymentParams.STATE_BILLING, binding.model!!.state)

                    intent.putExtra(PaymentParams.COUNTRY_BILLING, "SAU")
                    intent.putExtra(
                        PaymentParams.POSTAL_CODE_BILLING,
                        "00973"
                    ) //Put Country Phone code if Postal code not available '00973'

                    //Shipping Address
                    intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
                    intent.putExtra(PaymentParams.CITY_SHIPPING, binding.model!!.city)
                    intent.putExtra(PaymentParams.STATE_SHIPPING, binding.model!!.state)
                    intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "SAR")
                    intent.putExtra(
                        PaymentParams.POSTAL_CODE_SHIPPING,
                        "00973"
                    ) //Put Country Phone code if Postal code not available '00973'

                    //Payment Page Style
                    intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

                    //Tokenization
                    intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
                    startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)



                }

                else if(u==1){
                    hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
                    hashmap["booking_id"] = bookingId
                    hashmap["amount"] = payable_amount
                    hashmap["payment_type"] = u
                    ApiServices<WalletModel>().callApi(Urls.BOOKING_PAYMENT, this, hashmap, WalletModel::class.java,
                        true, this)
                }
                else
                    Toast.makeText(activity,"Something terrible",Toast.LENGTH_SHORT).show();



//            hashmap["booking_id"] = data.getStringExtra(PaymentParams.ORDER_ID)

//                hashmap["payment_type"] = u




            }
        }


    }


    var COUNTRYCODE_REWUEST=10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppLocationActivity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data!!.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));
            hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
            hashmap["booking_id"] = bookingId
            hashmap["amount"] = payable_amount
            hashmap["transaction_id"] = data.getStringExtra(PaymentParams.TRANSACTION_ID)
            hashmap["payment_type"] = u
            if(u==2){
                hashmap["wall"] = newWallet
            }

//            hashmap["booking_id"] = data.getStringExtra(PaymentParams.ORDER_ID)




            ApiServices<WalletModel>().callApi(Urls.BOOKING_PAYMENT, this, hashmap, WalletModel::class.java,
                true, this)


//            Toast.makeText(this, data.getStringExtra(PaymentParams.RESPONSE_CODE), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, data.getStringExtra(PaymentParams.TRANSACTION_ID), Toast.LENGTH_LONG).show();
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }
        else
            Toast.makeText(this, "Something Gone Wrong", Toast.LENGTH_LONG).show();


        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                binding.model!!.country_code=requiredValue
                binding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@WalletActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun goPayment(activity: Activity) {
        val intent = Intent(activity, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, "turkiothman@hotmail.com")
        intent.putExtra(
            PaymentParams.SECRET_KEY,
            "8pJ1qNyOZvvxFkaYXpwZJC6gDBmOEeGNEpO5dnTcSoJyIUxFj3F3FLkjnG81VOPzeEFr1yEUXnsFiZlB31FJNZ3aFbcB01afFQHG"
        )//Add your Secret Key Here

        intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library")
        intent.putExtra(PaymentParams.AMOUNT, 5.0)

        intent.putExtra(PaymentParams.CURRENCY_CODE, "BHD")
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        intent.putExtra(PaymentParams.ORDER_ID, bookingId)
        intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

        //Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_BILLING, "Manama")
        intent.putExtra(PaymentParams.STATE_BILLING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Shipping Address
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Payment Page Style
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
        activity.startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }






    fun validate(): Boolean {

        if(binding.model!!.first_name.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.last_name.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(co.selectedCountryCode.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.country_name.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.city.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.state.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.contact_number.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.postal_code.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.email.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.model!!.billing_address.isEmpty()){
            Toast.makeText(applicationContext,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        return  true
    }


    /*fun payNow(view : View){
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
        hashmap["booking_id"] = bookingId
        hashmap["wallet_amount"] = "0"
        hashmap["amount"] = payable_amount
        hashmap["currency"] = "SAR"
        hashmap["first_name"] = binding.model!!.first_name
        hashmap["last_name"] = binding.model!!.last_name
        hashmap["country_code"] = binding.model!!.country_code
        hashmap["contact_number"] = binding.model!!.contact_number
        hashmap["email"] = binding.model!!.email
        hashmap["billing_address"] = binding.model!!.billing_address
        hashmap["city"] = binding.model!!.city
        hashmap["state"] = binding.model!!.state
        hashmap["postal_code"] = binding.model!!.postal_code
        hashmap["country"] = binding.model!!.country_name
        ApiServices<WalletModel>().callApi(Urls.MAKE_PAYMENT, this, hashmap, WalletModel::class.java,
            true, this)
    }*/

}






