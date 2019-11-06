package pick.com.app.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import pick.com.app.R

class PaymentActi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        gopay()
    }

    private fun gopay() {
        val intent = Intent(this, PayTabActivity::class.java)
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
        intent.putExtra(PaymentParams.ORDER_ID, "123456")
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
        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }

}
