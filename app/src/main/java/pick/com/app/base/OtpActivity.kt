package pick.com.app.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.livinglifetechway.k4kotlin.core.toast
import pick.com.app.R
import pick.com.app.uitility.helpper.Redirection

class OtpActivity : BaseActivity() {
    lateinit var text12: TextView
    lateinit var text11: TextView
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_layout)

        text12=findViewById(R.id.tx1)
        text11=findViewById(R.id.tx2)

        activity.toast("camed")

        message("Sended")




    }

    fun message(msg:String){
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text12.visibility = View.VISIBLE
                text12.text = msg
            }
            override fun onFinish() {
                text12.visibility = View.INVISIBLE
                text12.text = ""
            }
        }.start()
    }



    fun confirmOTPClick(view: View) {

        Redirection().goToForgot(true,this,null)

        /* if (binding.user!!.checkvalidation(view, binding.user!!))
         {

             toast("Sucessfully")

         }*/


    }

}
