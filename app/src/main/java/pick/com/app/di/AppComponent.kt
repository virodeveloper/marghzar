package pick.com.app.di

import dagger.Component
import pick.com.app.MyApplication
import pick.com.app.base.CommonActivity
import pick.com.app.base.LoginActivity
import pick.com.app.base.OtpActivity
import pick.com.app.varient.user.ui.activity.RegistrationActivity
import pick.com.app.webservices.ApiServices
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent  {


    fun inject(myApplication: MyApplication)
    fun inject(myApplication: CommonActivity)
    fun inject(myApplication: RegistrationActivity)
    fun inject(myApplication: LoginActivity)
    fun inject(myApplication: OtpActivity)
    fun inject(myApplication: ApiServices<Any>)


}
