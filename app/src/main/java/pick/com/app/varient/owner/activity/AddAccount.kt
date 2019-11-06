package pick.com.app.varient.owner.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.livinglifetechway.k4kotlin.core.onClick
import kotlinx.android.synthetic.main.add_bank_account_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import nl.garvelink.iban.CountryCodes
import nl.garvelink.iban.IBAN

import pick.com.app.base.BaseActivity
import pick.com.app.base.model.ToolbarCustom
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.user.pojo.RegistrationModel
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls
import nl.garvelink.iban.Modulo97






class AddAccount : BaseActivity() {

    lateinit var addBankAccountLayoutBinding:pick.com.app.databinding.AddBankAccountLayoutBinding



    override fun <T> onSucess(result: T, methodtype: String?) {
        super.onSucess(result, methodtype)

        when(methodtype) {

            Urls.ADD_ACCOUNT,Urls.UPDATE_ACCOUNT -> {

                val result=result as RegistrationModel
                if (result.status==1){
                    SessionManager.setLoginModel(result,this)
                    showMessageWithError(message=result.description,isfinish = true)


                }else{

                    onError(result.description)
                }

            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBankAccountLayoutBinding=DataBindingUtil. setContentView(this, pick.com.app.R.layout.add_bank_account_layout)

        var model=SessionManager.getLoginModel(this).data
        addBankAccountLayoutBinding.model=model
        val toolbarCustom =
            ToolbarCustom(ToolbarCustom.lefticon, if (model.beneficiary_name!="")resources.getString(
                pick.com.app.R.string.update_account_details) else resources.getString(pick.com.app.R.string.add_account_details), ToolbarCustom.NoIcon, ToolbarCustom.NoIcon)
        left_Icon.onClick { onBackPressed() }
        addBankAccountLayoutBinding.toolbar=toolbarCustom
        submitbutton.text=if (model.beneficiary_name!="")resources.getString(pick.com.app.R.string.update_account) else resources.getString(
            pick.com.app.R.string.add_account)
    }

    fun submitDetails(view:View){
        /*["bank_name": hdfc bank, "user_id": 46, "bank_Account_number": 4542332423, "iban_number": 43242wfsdfs, "beneficiary_name": sourabh singhal]*/

        var urltype=Urls.UPDATE_ACCOUNT
        if (addBankAccountLayoutBinding.model!!.iban_number.isEmpty())
            urltype=Urls.ADD_ACCOUNT
       /* val iban = IBAN.valueOf(addBankAccountLayoutBinding.model!!.iban_number)
        val length = CountryCodes.getLengthForCountryCode("SA")

        val builder = StringBuilder("LU000019400644750000")
        val checkDigits = Modulo97.calculateCheckDigits(builder) // 28
        Toast.makeText(applicationContext,checkDigits  ,Toast.LENGTH_SHORT).show()*/


        val iba=addBankAccountLayoutBinding.model!!.iban_number.toString()
       // val iban:String = IBAN.valueOf(ibb)
        val p = iba
        val first = p[0]
        val two = p[1]
        val sb = StringBuilder()
        sb.append(first)
        sb.append(two)

        val l = sb.toString().toUpperCase()
        val w = p.length
        val valid = Modulo97.verifyCheckDigits(p)

        val length = CountryCodes.getLengthForCountryCode(l)


        val k = sb.toString()

        if (l == k && w == length) {
            Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show()
            val hahsmpa=HashMap<String,Any>()
            hahsmpa.put("user_id",addBankAccountLayoutBinding.model!!.user_id)
            hahsmpa.put("bank_name",addBankAccountLayoutBinding.model!!.bank_name)
            hahsmpa.put("bank_account_number",addBankAccountLayoutBinding.model!!.account_number)
            hahsmpa.put("iban_number",addBankAccountLayoutBinding.model!!.iban_number)
            hahsmpa.put("beneficiary_name",addBankAccountLayoutBinding.model!!.beneficiary_name)

            if (RegistrationModel("User").addcountValidation(view,addBankAccountLayoutBinding.model!!))
                ApiServices<RegistrationModel>().callApi(urltype,this,hahsmpa,RegistrationModel::class.java,true,this)

        }
        else{
            Toast.makeText(applicationContext, "Please Enter Correct IBAN NUMBER", Toast.LENGTH_SHORT).show()
        }

    }
}
