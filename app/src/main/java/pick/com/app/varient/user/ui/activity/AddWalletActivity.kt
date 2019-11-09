package pick.com.app.varient.user.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.onClick
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import kotlinx.android.synthetic.main.toolbar.*
import pick.com.app.R
import pick.com.app.base.BaseActivity
import pick.com.app.base.PaymentActi
import pick.com.app.base.PaymentActivity
import pick.com.app.base.StaticWebUrlActivity
import pick.com.app.base.model.StaticWebPagesModel
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.databinding.AddUserPaymentScreenBinding
import pick.com.app.databinding.UserPaymentScreenBinding
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.location.AppLocationActivity
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.FilterModel.Companion.hashmap
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.varient.user.pojo.WalletModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class AddWalletActivity : BaseActivity() {

    private lateinit var binding : AddUserPaymentScreenBinding
    private var bookingId : String= ""
    private var payable_amount : String= ""
    private var add_amount : String= ""
    private lateinit var toolbarCustom: ToolbarCustom

    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)
        if (methodtype == Urls.MAKE_PAYMENT_WALLET) {
            var model = result as WalletModel
            if(model.status == 1){
                /*startActivity(Intent(this, StaticWebUrlActivity::class.java).apply {
                    StaticWebPagesModel.type="wallet"

                })*/
                startActivity(Intent(this, StaticWebUrlActivity::class.java)
                    .putExtra("url", model.payment_url+","+"Payment"))
                finish()
            }
        } else if(methodtype == Urls.USER_WALLET) {
            var model = result as WalletModel
        }
        else if(methodtype == Urls.ADDWALLETAMOUNT) {
            var model = result as RegistrationModel

            Toast.makeText(applicationContext,"Your wallet updated",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_user_payment_screen)
        toolbarCustom = ToolbarCustom(ToolbarCustom.lefticon, resources.getString(R.string.payment), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        binding.toolbar = toolbarCustom
        binding.model = WalletModel()
        binding.redirection = this@AddWalletActivity
        left_Icon.onClick { onBackPressed() }
        bookingId = intent.getStringExtra("booking_id")
        payable_amount = intent.getStringExtra("amount")
        add_amount = intent.getStringExtra("amount")
        binding.payableAmount.setText(payable_amount + " SAR")
        userWallet()
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


    var COUNTRYCODE_REWUEST=10002
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppLocationActivity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
            hashmap["amount"] = add_amount.toInt()
            ApiServices<WalletModel>().callApi(Urls.ADDWALLETAMOUNT, this, hashmap, WalletModel::class.java,true, this)

            Log.e("Tag", data!!.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));
            Toast.makeText(this, data.getStringExtra(PaymentParams.RESPONSE_CODE), Toast.LENGTH_LONG).show();
            Toast.makeText(this, data.getStringExtra(PaymentParams.TRANSACTION_ID), Toast.LENGTH_LONG).show();
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }
        else
            Toast.makeText(this, "Something Gone waqas Wrong", Toast.LENGTH_LONG).show();




        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == COUNTRYCODE_REWUEST && resultCode == Activity.RESULT_OK) {

                val requiredValue = data!!.getStringExtra("countrycode")
                binding.model!!.country_code=requiredValue
                binding.invalidateAll()
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@AddWalletActivity, ex.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

   /* fun payNow(view : View){
        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] = SessionManager.getLoginModel(this).data.user_id
        hashmap["booking_id"] = bookingId
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
        ApiServices<WalletModel>().callApi(Urls.MAKE_PAYMENT_WALLET, this, hashmap, WalletModel::class.java,
            true, this)
    }

    fun paypayment(view: View) {
        val intent=Intent(this, PaymentActi()::class.java)
        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }*/
   fun payNow(view: View) {
       if(validate()){
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
           intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, binding.model!!.country_code+binding.model!!.contact_number)
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
        if(binding.model!!.country_code.isEmpty()){
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
}