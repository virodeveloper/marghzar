package pick.com.app

import android.content.Context
import android.os.Bundle
import pick.com.app.base.BaseActivity
import pick.com.app.uitility.helpper.Redirection


open class MainActivity : BaseActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)








        Redirection().goToHome(true, this, null)


    }
}
